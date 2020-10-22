package com.zoro.service.impl;

import com.old.checkcode.PolicyModel;
import com.zoro.config.CodeConvert;
import com.zoro.dto.*;
import com.zoro.mapper.CheckCodeMapper;
import com.zoro.mapper.CodeCheckMapper;
import com.zoro.service.CodeCheckService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author zhang.wenhan
 * @description CodeCheckServiceImpl
 * @date 2019/8/12 15:47
 */
@Service
public class CodeCheckServiceImpl implements CodeCheckService {

    private final static Logger logger = LoggerFactory.getLogger(CodeCheckServiceImpl.class);

    @Autowired
    CodeCheckMapper codeCheckMapper;

    @Autowired
    CheckCodeMapper checkCodeMapper;

    @Override
    public void excute() {
        long start = System.currentTimeMillis();
        List<Policy> list = codeCheckMapper.getPolicyList();
        if (CollectionUtils.isEmpty(list)) {
            logger.info("待处理列表为空， 处理完毕！");
        }
        logger.info("开始处理，size:" + list.size());
        for (Policy policy : list) {
            checkCode(policy);
        }
        long end = System.currentTimeMillis();
        logger.info("处理完毕！耗时：" + (end - start) + " 毫秒");
    }

    @Override
    public void checkCode(Policy policy, CountDownLatch countDownLatch) {
        try {
            checkCode(policy);
        } finally {
            countDownLatch.countDown();
        }
    }


    @Override
    public void checkCode2018(OrderMain orderMain, CountDownLatch countDownLatch) {
        try {
            checkCode2018(orderMain);
        } finally {
            countDownLatch.countDown();
        }
    }

    @Override
    public void checkMarry2018(OrderMain orderMain) {
        List<TbusPolicy> tbusPolicies = codeCheckMapper.getByPrePolicyCode(orderMain.getOrderid());
        if (CollectionUtils.isEmpty(tbusPolicies)) {
            return;
        }
        List<CheckCode> list = new ArrayList<>();
        List<OrderPolicyholderInsured> policyholderInsureds = codeCheckMapper.getHolderAndInsurance(orderMain.getUuid());
        if (CollectionUtils.isEmpty(policyholderInsureds)) {
            return;
        }
        for(OrderPolicyholderInsured policyholderInsured : policyholderInsureds){
            if("0".equals(policyholderInsured.getType())){
                if(policyholderInsured.getMaritalstatus() == null){
                    return;
                }
                String marryState = this.getErpMarry2018(policyholderInsured.getMaritalstatus());
                TbusPolicy tbusPolicy = tbusPolicies.get(0);
                if (!marryState.equals(tbusPolicy.getHmarrystate())) {
                    this.printSql(orderMain.getOrderid(), policyholderInsured.getMaritalstatus(), tbusPolicy.getHmarrystate(), marryState,
                            policyholderInsured.getCertificatesnumber(), 19, list);
                }
            } else {
                for (TbusPolicy tbusPolicy : tbusPolicies) {
                    if (policyholderInsured.getCertificatesnumber().equals(tbusPolicy.getIpcerticode())) {
                        if(policyholderInsured.getMaritalstatus() == null){
                            return;
                        }
                        String marryState = this.getErpMarry2018(policyholderInsured.getMaritalstatus());
                        if (!marryState.equals(tbusPolicy.getIpmarrystate())) {
                            this.printSql(orderMain.getOrderid(), policyholderInsured.getMaritalstatus(), tbusPolicy.getIpmarrystate(), marryState,
                                    policyholderInsured.getCertificatesnumber(), 20, list);
                        }
                        break;
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(list)) {
            checkCodeMapper.insertList(list);
        }
    }

    private String getErpMarry2018(String marryState){
        switch (marryState){
            case "1":
                return "01";
            case "2":
                return "02";
            case "3":
                return "03";
            case "4":
                return "04";
           default:
                return "05";
        }
    }


    @Override
    public void checkCode2018(OrderMain orderMain) {
        try {
            List<TbusPolicy> tbusPolicies = codeCheckMapper.getByPrePolicyCode(orderMain.getOrderid());
            if (CollectionUtils.isEmpty(tbusPolicies)) {
                return;
            }
            List<CheckCode> list = new ArrayList<>();
            List<OrderPolicyholderInsured> policyholderInsureds = codeCheckMapper.getHolderAndInsurance(orderMain.getUuid());
            this.checkPolicyholderInsured(policyholderInsureds, tbusPolicies, orderMain, list);
/*            List<OrderBeneficiary> beneficiaries = codeCheckMapper.getBeneficiary(orderMain.getUuid());
            this.checkBeneficiaries(beneficiaries, tbusPolicies, orderMain, list);*/
            if (CollectionUtils.isNotEmpty(list)) {
                checkCodeMapper.insertList(list);
            }
        } catch (Exception e) {
            logger.error("校验异常", e);
            CheckCode checkCode = new CheckCode(orderMain.getOrderid(), orderMain.getOrderid(), orderMain.getId().toString(),
                    "校验异常", e.getMessage(), 10);
            checkCodeMapper.insertSelective(checkCode);
            System.out.println("校验异常 id:" + orderMain.getId());
        }

    }

    private void checkBeneficiaries(List<OrderBeneficiary> beneficiaries, List<TbusPolicy> tbusPolicies, OrderMain orderMain, List<CheckCode> list) {
        if (CollectionUtils.isEmpty(beneficiaries)) {
            return;
        }
        for (OrderBeneficiary beneficiary : beneficiaries) {
            for (TbusPolicy tbusPolicy : tbusPolicies) {
                if (beneficiary.getCertificatesnumber() == null) {
                    continue;
                }
                if (beneficiary.getCertificatesnumber().equals(tbusPolicy.getBpcerticode())) {
                    String sex = this.getErpSex2018(beneficiary.getSex());
                    if (!sex.equals(tbusPolicy.getBpsex())) {
                        this.printSql(orderMain.getOrderid(), beneficiary.getSex(), tbusPolicy.getBpsex(), sex, tbusPolicy.getBpcerticode(), 16, list);
                    }
                    String certificatesType = this.getCertificatesType(beneficiary.getCertificatestype());
                    if (!certificatesType.equals(tbusPolicy.getBpcertitype())) {
                        this.printSql(orderMain.getOrderid(), beneficiary.getCertificatestype(), tbusPolicy.getBpcertitype(), certificatesType,
                                tbusPolicy.getBpcerticode(), 17, list);
                    }

                    String relation = this.getBenefitInsuredRelationCode(beneficiary.getRelationship());
                    if (!relation.equals(tbusPolicy.getBprelationcode())) {
                        this.printSql(orderMain.getOrderid(), beneficiary.getRelationship(), tbusPolicy.getBprelationcode(), relation, tbusPolicy.getBpcerticode(), 18, list);
                    }
                    break;
                }
            }
        }

    }

    private void checkPolicyholderInsured(List<OrderPolicyholderInsured> policyholderInsureds, List<TbusPolicy> tbusPolicies, OrderMain orderMain, List<CheckCode> list) {
        if (CollectionUtils.isEmpty(policyholderInsureds)) {
            return;
        }
        String insrelation = "";
        String relation = "";
        String productNo = codeCheckMapper.getPorductNo(orderMain.getUuid());
        for (OrderPolicyholderInsured policyholder : policyholderInsureds) {
            if ("0".equals(policyholder.getType())) { // 投保人
                /*String sex = this.getErpSex2018(policyholder.getSex());
                TbusPolicy tbusPolicy = tbusPolicies.get(0);
                if (!sex.equals(tbusPolicy.getHsex())) {
                    this.printSql(orderMain.getOrderid(), policyholder.getSex(), tbusPolicy.getHsex(), sex,
                            policyholder.getCertificatesnumber(), 11, list);
                }
                String certificatesType = this.getCertificatesType(policyholder.getCertificatestype());
                if (!certificatesType.equals(tbusPolicy.getHcertitype())) {
                    this.printSql(orderMain.getOrderid(), policyholder.getCertificatestype(), tbusPolicy.getHcertitype(), certificatesType,
                            policyholder.getCertificatesnumber(), 12, list);
                }*/
                insrelation = this.getHolderInsuredRelationCode(policyholder.getRelationship(), productNo);
                relation = policyholder.getRelationship();
            }
        }
        for (OrderPolicyholderInsured policyInsured : policyholderInsureds) {
            if ("1".equals(policyInsured.getType())) { // 被保人
                for (TbusPolicy tbusPolicy : tbusPolicies) {
                    if (policyInsured.getCertificatesnumber().equals(tbusPolicy.getIpcerticode())) {
                       /* String sex = this.getErpSex2018(policyInsured.getSex());
                        if (!sex.equals(tbusPolicy.getIpsex())) {
                            this.printSql(orderMain.getOrderid(), policyInsured.getSex(), tbusPolicy.getIpsex(), sex,
                                    tbusPolicy.getIpcerticode(), 13, list);
                        }
                        String certificatesType = this.getCertificatesType(policyInsured.getCertificatestype());
                        if (!certificatesType.equals(tbusPolicy.getIpcertitype())) {
                            this.printSql(orderMain.getOrderid(), policyInsured.getCertificatestype(), tbusPolicy.getIpcertitype(), certificatesType,
                                    tbusPolicy.getIpcerticode(), 14, list);
                        }*/
                        if(StringUtils.isEmpty(insrelation)){
                            insrelation = this.getHolderInsuredRelationCode(policyInsured.getRelationship(), productNo);
                        }
                        if(policyholderInsureds.size() > 2){
                            insrelation = this.getHolderInsuredRelationCode(policyInsured.getRelationship(), productNo);
                        }
                        if(StringUtils.isEmpty(relation)){
                            relation = policyInsured.getRelationship();
                        }
                        if (!insrelation.equals(tbusPolicy.getIprelationcode())) {
                            this.printSql(orderMain.getOrderid(), relation, tbusPolicy.getIprelationcode(), insrelation, tbusPolicy.getIpcerticode(), 15, list);
                        }
                        break;
                    }
                }
            }
        }
    }

    private String getBenefitInsuredRelationCode(String realtion) {
        if (StringUtils.isBlank(realtion)) {
            return "27";
        }
        String str = CodeConvert.benefitInsuredRelationCode.getString(realtion.trim().toLowerCase());
        switch (str) {
            case "1":
                return "01";
            case "2":
                return "33";
            case "3":
                return "34";
            case "4":
                return "35";
            default:
                return "27";
        }
    }

    private String getHolderInsuredRelationCode(String realtion, String productNo) {
        if (StringUtils.isBlank(realtion)) {
            return "";
        }
        String str = "";
        if("FXLH-YLX02".equals(productNo)){
            str = CodeConvert.homeHolderInsuredRelationCode.getString(realtion.trim().toLowerCase());
        } else {
           str = CodeConvert.holderInsuredRelationCode.getString(realtion.trim().toLowerCase());
        }
        switch (str) {
            case "1":
                return "01";
            case "2":
                return "33";
            case "3":
                return "34";
            case "4":
                return "35";
            case "31":
                return "27";
            default:
                return "32";
        }
    }

    private String getCertificatesType(String type) {
        if (StringUtils.isBlank(type)) {
            return "19";
        }
        String str = CodeConvert.certificatesTypeCode.getString(type.trim());
        switch (str) {
            case "1":
                return "01";
            case "2":
                return "02";
            case "3":
                return "03";
            case "5":
                return "04";
            default:
                return "19";
        }
    }

    private String getErpSex2018(String sex) {
        if (StringUtils.isBlank(sex)) {
            return "00";
        }
        String str = CodeConvert.sexCode.getString(sex.trim());
        switch (str) {
            case "1":
                return "01";
            case "2":
                return "02";
            default:
                return "00";
        }
    }


    @Override
    public void checkCode(Policy policy) {

        try {
            List<TbusPolicy> tbusPolicies = codeCheckMapper.getByPolicyCode(policy.getPolicycode());
            if (CollectionUtils.isEmpty(tbusPolicies)) {
                return;
            }
            PolicyCust holder = codeCheckMapper.getHolder(policy.getPolicykey());
            List<CheckCode> list = new ArrayList<>();
            this.checkHolder(holder, tbusPolicies.get(0), policy, list);
            List<PolicyCust> insurances = codeCheckMapper.getInsurance(policy.getPolicykey());
            this.checkInsurance(insurances, tbusPolicies, policy, holder, list);
            List<PolicyBeff> policyBeffs = codeCheckMapper.getBenifate(policy.getPolicykey());
            this.checkBenifate(policyBeffs, tbusPolicies, policy, list);
            if (CollectionUtils.isNotEmpty(list)) {
                checkCodeMapper.insertList(list);
            }
        } catch (Exception e) {
            logger.error("校验异常", e);
//            CheckCode checkCode = new CheckCode(policy.getPolicycode(), policy.getPolicykey(), policy.getId().toString(),
//                    "校验异常", null, 10);
//            checkCodeMapper.insertSelective(checkCode);
            System.out.println("校验异常 id:" + policy.getId());
        }
    }

    private void checkBenifate(List<PolicyBeff> policyBeffs, List<TbusPolicy> tbusPolicies, Policy policy, List<CheckCode> list) {
        if (CollectionUtils.isEmpty(policyBeffs)) {
            return;
        }
        for (PolicyBeff policyBeff : policyBeffs) {
            for (TbusPolicy tbusPolicy : tbusPolicies) {
                if (policyBeff.getIdcode() == null) {
                    continue;
                }
                if (policyBeff.getIdcode().equals(tbusPolicy.getBpcerticode())) {
                    String sex = this.getERPSex(policyBeff.getSex(), policy.getCompkey());
                    if (!sex.equals(tbusPolicy.getBpsex())) {
                        this.printSql(policy.getPolicycode(), policyBeff.getSex(), tbusPolicy.getBpsex(), sex, policyBeff.getIdcode(), 6, list);
                    }
                    String relation = this.getERPBenifateRelation(policyBeff.getRelation(), policy.getCompkey());
                    if (!relation.equals(tbusPolicy.getBprelationcode())) {
                        this.printSql(policy.getPolicycode(), policyBeff.getRelation(), tbusPolicy.getBprelationcode(), relation, policyBeff.getIdcode(), 7, list);
                    }
                    break;
                }
            }
        }
    }

    private void checkInsurance(List<PolicyCust> insurances, List<TbusPolicy> tbusPolicies, Policy policy, PolicyCust holder, List<CheckCode> list) {
        if (CollectionUtils.isEmpty(insurances)) {
            return;
        }
        for (PolicyCust insurance : insurances) {
            for (TbusPolicy tbusPolicy : tbusPolicies) {
                if (insurance.getIdcode().equals(tbusPolicy.getIpcerticode())) {
                    String sex = this.getERPSex(insurance.getSex(), policy.getCompkey());
                    if (!sex.equals(tbusPolicy.getIpsex())) {
                        this.printSql(policy.getPolicycode(), insurance.getSex(), tbusPolicy.getIpsex(), sex, insurance.getIdcode(), 3, list);
                    }
                    if (StringUtils.isNotEmpty(insurance.getMarriage())) {
                        String marriage = this.getERPMarriage(insurance.getMarriage(), policy.getCompkey());
                        if (!marriage.equals(tbusPolicy.getIpmarrystate())) {
                            this.printSql(policy.getPolicycode(), insurance.getMarriage(), tbusPolicy.getIpmarrystate(), marriage, insurance.getIdcode(), 4, list);
                        }
                    }
                    String relation = this.getInsuranceRelation(holder.getRelation(), holder.getSms(), insurance.getRelation(), policy.getCompkey());
                    if (!relation.equals(tbusPolicy.getIprelationcode())) {
                        this.printSql(policy.getPolicycode(), insurance.getRelation(), tbusPolicy.getIprelationcode(), relation, insurance.getIdcode(), 5, list);
                    }
                    break;
                }
            }
        }
    }

    private void checkHolder(PolicyCust holder, TbusPolicy tbusPolicy, Policy policy, List<CheckCode> list) {
        String sex = this.getERPSex(holder.getSex(), policy.getCompkey());
        if (!sex.equals(tbusPolicy.getHsex())) {
            this.printSql(policy.getPolicycode(), holder.getSex(), tbusPolicy.getHsex(), sex, null, 1, list);
        }
        if (StringUtils.isNotEmpty(holder.getMarriage())) {
            String marriage = this.getERPMarriage(holder.getMarriage(), policy.getCompkey());
            if (!marriage.equals(tbusPolicy.getHmarrystate())) {
                this.printSql(policy.getPolicycode(), holder.getMarriage(), tbusPolicy.getHmarrystate(), marriage, null, 2, list);
            }
        }
    }

    private void printSql(String policycode, String code, String erp_code, String turn_code, String certcode, int state, List<CheckCode> list) {
        list.add(new CheckCode(policycode, code, erp_code, turn_code, certcode, state));
//        StringBuffer sb = new StringBuffer("INSERT INTO `check_code`(`policycode`, `code`, `erp_code`, `turn_code`, `certcode`, `state`) VALUES ('");
//        sb.append(policycode).append("', '").append(code).append("', '").append(erp_code).append("', '")
//                .append(turn_code).append("', '").append(certcode).append("', ").append(state).append(");");
//        System.out.println(sb.toString());
    }

    private String getInsuranceRelation(String hRel, String sms, String iRel, String compKey) {
        String str = this.getInsuranceRelation1(hRel, sms, iRel, compKey);
        switch (str) {
            case "1":
                return "01";
            case "2":
                return "33";
            case "3":
                return "34";
            case "4":
                return "35";
            case "6":
                return "32";
            default:
                return "00";
        }
    }

    private String getInsuranceRelation1(String hRel, String sms, String iRel, String compKey) {
        if (StringUtils.isEmpty(hRel)) {
            hRel = iRel;
        }
        String relation = this.getErpCodeName("relation", hRel, compKey);
        if ("Y".equals(sms)) {
            relation = "1";
        }
        if (StringUtils.isEmpty(relation)) {
            relation = "6";
        }
        return relation;
    }

    private String getErpCodeName(String codeType, String codeValue, String compKey) {
        if (compKey == null || compKey.trim().length() <= 0)
            compKey = "CO070703000000000001";
        if ("CO120306000000000001".equals(compKey) && "sex".equals(codeType)) {
            if ("0".equals(codeValue)) {//男
                codeValue = "1";
            } else if ("1".equals(codeValue)) {//女
                codeValue = "2";
            }
        }
        if ("CO120306000000000001".equals(compKey) && "relation".equals(codeType)) {
            if ("00".equals(codeValue)) {//本人
                codeValue = "5";
            }
        }
        List<String> names = codeCheckMapper.getCodeName(codeType, codeValue, compKey);
        if (CollectionUtils.isEmpty(names)) {
            return "";
        }
        String name = names.get(0);
        String code = "";
        if (name.equals("丈夫")) {
            code = "4";
        } else if (name.equals("儿女")) {
            code = "3";
        } else if (name.equals("儿子")) {
            code = "3";
        } else if (name.equals("夫妻")) {
            code = "";
        } else if (name.equals("女儿")) {
            code = "3";
        } else if (name.equals("妻子")) {
            code = "4";
        } else if (name.equals("媳妇")) {
            code = "4";
        } else if (name.equals("子女")) {
            code = "3";
        } else if (name.equals("本人")) {
            code = "1";
        } else if (name.equals("母亲")) {
            code = "2";
        } else if (name.equals("母女")) {
            code = "3";
        } else if (name.equals("母子")) {
            code = "3";
        } else if (name.equals("父亲")) {
            code = "2";
        } else if (name.equals("父女")) {
            code = "3";
        } else if (name.equals("父子")) {
            code = "3";
        } else if (name.equals("父母")) {
            code = "2";
        } else if (name.equals("配偶")) {
            code = "4";
        }
        return code;

    }

    private String getERPMarriage(String marriage, String compKey) {
        String str = this.getERPMarriage1(marriage, compKey);
        switch (str) {
            case "1":
                return "02";
            case "2":
                return "01";
            case "3":
                return "03";
            case "4":
                return "04";
            default:
                return "0";
        }
    }

    private String getERPMarriage1(String marriage, String compKey) {
        //erp婚姻状况："1">未婚  "2">已婚   "3">离婚   "4">丧偶
        if (!PolicyModel.COMPKEY_PICC.equals(compKey)) {
            String type = "0";  //erp系统默认婚姻状况 空，徐确认
            if ("1".equals(marriage))//已婚
                type = "2";
            if ("0".equals(marriage))//未婚
                type = "1";
            if ("6".equals(marriage))//离异
                type = "3";
            if ("2".equals(marriage))//丧偶
                type = "4";
            //同方全球婚姻状况
            if (PolicyModel.COMPKEY_TONGFANGQQ.equals(compKey)) {
                if ("1".equals(marriage))//单身
                    type = "1";
                if ("2".equals(marriage))//已婚
                    type = "2";
                if ("3".equals(marriage))//鳏寡
                    type = "4";
                if ("4".equals(marriage))//离异
                    type = "3";
            }
            return type;
        } else {
            String type = "0";  //erp系统默认婚姻状况 空，徐确认
            if ("1".equals(marriage))//已婚
                type = "2";
            if ("2".equals(marriage))//未婚
                type = "1";
            if ("3".equals(marriage))//离异
                type = "3";
            if ("4".equals(marriage))//丧偶
                type = "4";
            return type;
        }
    }

    private String getERPSex(String sex, String compKey) {
        String str = this.getERPSex1(sex, compKey);
        switch (str) {
            case "1":
                return "01";
            case "2":
                return "02";
            default:
                return "00";
        }
    }

    private String getERPSex1(String sex, String compKey) {
        if (!PolicyModel.COMPKEY_PICC.equals(compKey)) {
            if (!PolicyModel.COMPKEY_TaiKangZAIXIAN.equals(compKey)) {
                if (PolicyModel.COMPKEY_YANGGUANG.equals(compKey)) {
                    return ("1".equals(sex) ? "1" : "2");
                }
                if (PolicyModel.COMPKEY_TAIPING.equals(compKey)) {
                    return ("1".equals(sex) ? "1" : "2");
                }
                if (PolicyModel.COMPKEY_TaiKangRenShou.equals(compKey)) {
                    return ("1".equals(sex) ? "1" : "2");
                }
                if (PolicyModel.COMPKEY_TBANLIAN.equals(compKey)) {
                    return ("1".equals(sex) ? "1" : "2");
                }
                if (PolicyModel.COMPKEY_METLIFE.equals(compKey)) {
                    return ("M".equals(sex) ? "1" : "2");
                }
                if (PolicyModel.COMPKEY_PingAnHealth.equals(compKey)) {
                    return ("M".equals(sex) ? "1" : "2");
                }

                String hsex = "1";
                if ("1".equals(sex))
                    hsex = "2";
                return hsex;
            } else {
                String hsex = "2";
                if ("1".equals(sex)) {
                    hsex = "1";
                }
                return hsex;
            }
        } else {
            String hsex = "1";
            if ("F".equals(sex))
                hsex = "2";
            return hsex;
        }
    }

    public String getERPCertitype(String idtype, String compKey) {
        //erp证件类型："1">身份证  "2">护照   "3">军官证   "4">其它
        if (!PolicyModel.COMPKEY_PICC.equals(compKey)) {
            String type = "4";
            if ("0".equals(idtype))//身份证
                type = "1";
            if ("2".equals(idtype))//军人证
                type = "3";
            if ("1".equals(idtype))//护照
                type = "2";
            ////永安证件类型
            if (PolicyModel.COMPKEY_YONGAN.equals(compKey)) {
                if ("1".equals(idtype))//身份证
                    type = "1";
                if ("3".equals(idtype))//护照
                    type = "2";
                if ("120001".equals(idtype))
                    type = "1";
                if ("120002".equals(idtype))
                    type = "2";
            }
            //苏黎世证件号
            if (PolicyModel.COMPKEY_SLS.equals(compKey)) {
                if ("I".equals(idtype))//身份证
                    type = "1";
                if ("P".equals(idtype))//护照
                    type = "2";
                if ("S".equals(idtype))//军人证
                    type = "3";
            }
            //国寿财证件号
            if (PolicyModel.COMPKEY_CLP.equals(compKey)) {
                if ("01".equals(idtype))//身份证
                    type = "1";
                if ("03".equals(idtype))//护照
                    type = "2";
                if ("04".equals(idtype))//军人证
                    type = "3";
            }
            //史带财险证件号
            if (PolicyModel.COMPKEY_DAZHONG.equals(compKey)) {
                if ("1".equals(idtype))//身份证
                    type = "1";
                if ("3".equals(idtype))//护照
                    type = "2";
            }
            //美亚证件号
            if (PolicyModel.COMPKEY_MEIYA.equals(compKey)) {
                if ("1".equals(idtype))//身份证
                    type = "1";
                if ("3".equals(idtype))//护照
                    type = "2";
            }
            //华安证件号
            if (PolicyModel.COMPKEY_HUAAN.equals(compKey)) {
                if ("1".equals(idtype))//身份证
                    type = "1";
                if ("2".equals(idtype))//军官证
                    type = "3";
                if ("3".equals(idtype))//护照
                    type = "2";
            }
            //大地证件号
            if (PolicyModel.COMPKEY_DADI.equals(compKey)) {
                if ("1".equals(idtype))//身份证
                    type = "1";
                if ("8".equals(idtype))//中国护照
                    type = "2";
                if ("2".equals(idtype))//军人证
                    type = "3";
            }
            //中意证件号
            if (PolicyModel.COMPKEY_ZHONGYI.equals(compKey)) {
                if ("01".equals(idtype))//身份证
                    type = "1";
                if ("03".equals(idtype))//护照
                    type = "2";
                if ("04".equals(idtype))//军官证
                    type = "3";
            }
            //新华证件号
            if (PolicyModel.COMPKEY_XINHUA.equals(compKey)) {
                if ("1".equals(idtype))//身份证
                    type = "1";
                if ("3".equals(idtype))//护照
                    type = "2";
                if ("2".equals(idtype))//军官证
                    type = "3";
            }
            //平安证件号
            if (PolicyModel.COMPKEY_PINGAN.equals(compKey)) {
                if ("01".equals(idtype))//身份证
                    type = "1";
                if ("02".equals(idtype))//护照
                    type = "2";
                if ("03".equals(idtype))//军官证
                    type = "3";
            }
            //平安健康证件号
            if (PolicyModel.COMPKEY_PingAnHealth.equals(compKey)) {
                if ("1".equals(idtype))//身份证
                    type = "1";
                if ("2".equals(idtype))//护照
                    type = "2";
                if ("A".equals(idtype))//其他
                    type = "4";
            }

            //安联财险证件号
            if (PolicyModel.COMPKEY_ANLIAN_GENERAL.equals(compKey)) {
                if ("1".equals(idtype))//身份证
                    type = "1";
                if ("2".equals(idtype))//护照
                    type = "2";
                if ("3".equals(idtype)) {//其他
                    type = "4";
                }
            }
            //人保健康证件号
            if (PolicyModel.COMPKEY_RMJK.equals(compKey)) {
                if ("1".equals(idtype))//身份证
                    type = "1";
                if ("2".equals(idtype))//军官证
                    type = "3";
                if ("3".equals(idtype))//护照
                    type = "2";
            }
            //百年证件号
            if (PolicyModel.COMPKEY_BaiNian.equals(compKey)) {
                if ("1".equals(idtype))//身份证
                    type = "1";
                if ("2".equals(idtype))//护照
                    type = "2";
            }
            //华夏证件号
            if (PolicyModel.COMPKEY_HuaXia.equals(compKey)) {
                if ("0".equals(idtype))//身份证
                    type = "1";
                if ("2".equals(idtype))//护照
                    type = "2";
                if ("3".equals(idtype))//军官证
                    type = "3";
            }
            //长生人寿证件号
            if (PolicyModel.COMPKEY_ChangSheng.equals(compKey)) {
                if ("0".equals(idtype))//身份证
                    type = "1";
                if ("1".equals(idtype))//护照
                    type = "2";
                if ("2".equals(idtype))//军官证
                    type = "3";
                if ("8".equals(idtype))//其他
                    type = "4";
            }
            //安盛天平
            if (PolicyModel.COMPKEY_ANSHENGTIANPING.equals(compKey)) {
                if ("01".equals(idtype))//身份证
                    type = "1";
                if ("02".equals(idtype))//护照
                    type = "2";
                if ("04".equals(idtype))//其他
                    type = "4";
            }
            //同方全球证件类型
            if (PolicyModel.COMPKEY_TONGFANGQQ.equals(compKey)) {
                if ("b".equals(idtype))//身份证
                    type = "1";
                if ("c".equals(idtype))//护照
                    type = "2";
                if ("o".equals(idtype))//军官证
                    type = "3";
            }
            //太平洋人寿证件类型
            else if (PolicyModel.COMPKEY_TaiPingYangRS.equals(compKey)) {
                if ("I".equals(idtype))//身份证
                    type = "1";
                if ("P".equals(idtype))//护照
                    type = "2";
                if ("S".equals(idtype))//军官证
                    type = "3";
            }
            //泰康人寿证件类型转换
            else if (PolicyModel.COMPKEY_TaiKangRenShou.equals(compKey)) {
                if ("1".equals(idtype)) {//身份证
                    type = "1";
                } else if ("3".equals(idtype)) {//护照
                    type = "2";
                } else if ("10".equals(idtype)) {//其他
                    type = "4";
                }
            }
            //复星保德信 证件类型转换
            else if (PolicyModel.COMPKEY_FuXingRenShou.equals(compKey)) {
                if ("1".equals(idtype)) {//身份证
                    type = "1";
                } else if ("3".equals(idtype)) {//护照
                    type = "2";
                } else if ("9".equals(idtype)) {//其他
                    type = "4";
                }
                //长安航意险证件类型转换
            } else if (PolicyModel.COMPKEY_CHANGAN.equals(compKey)) {
                if ("01".equals(idtype)) {//身份证
                    type = "1";
                } else if ("03".equals(idtype)) {//护照
                    type = "2";
                } else if ("99".equals(idtype)) {//其他
                    type = "4";
                }
                //太平养老
            } else if (PolicyModel.COMPKEY_TAIPING.equals(compKey)) {
                if ("1".equals(idtype)) {//身份证
                    type = "1";
                } else if ("3".equals(idtype)) {//护照
                    type = "2";
                } else if ("2".equals(idtype)) {//护照
                    type = "3";
                } else if ("9".equals(idtype)) {//其他
                    type = "4";
                }
            } else if (PolicyModel.COMPKEY_TaiKangZAIXIAN.equals(compKey)) {
                if ("01".equals(idtype)) {//身份证
                    type = "1";
                } else if ("02".equals(idtype)) {//护照
                    type = "2";
                } else if ("03".equals(idtype)) {//军官证
                    type = "3";
                } else if ("99".equals(idtype)) {//其他
                    type = "4";
                }
            }
            //中美大都会证件类型转码
            else if (PolicyModel.COMPKEY_METLIFE.equals(compKey)) {
                if ("A".equals(idtype)) {//身份证
                    type = "1";
                } else if ("B".equals(idtype)) {//护照
                    type = "2";
                } else if ("C".equals(idtype)) {//军官证
                    type = "3";
                } else if ("Q".equals(idtype)) {//其他
                    type = "4";
                }
            }
            //阳光财险证件类型转码(暂时只有身份证了)
            else if (PolicyModel.COMPKEY_YANGGUANG.equals(compKey)) {
                if ("01".equals(idtype)) {//身份证
                    type = "1";
                }
            }
            //国寿证件类型转码
            else if (PolicyModel.COMPKEY_ZGRS.equals(compKey)) {
                if ("I".equals(idtype)) {//身份证
                    type = "1";
                } else if ("P".equals(idtype)) {//护照
                    type = "2";
                } else if ("O".equals(idtype)) {//其他
                    type = "4";
                }
            }
            return type;
        } else {
            String type = "4";
            if ("1".equals(idtype))//身份证
                type = "1";
            if ("2".equals(idtype))//军人证
                type = "3";
            if ("3".equals(idtype))//护照
                type = "2";
            return type;
        }
    }

    private String getERPBenifateRelation(String relation, String compKey) {
        String ben = this.getERPBenifateRelation1(relation, compKey);
        switch (ben) {
            case "1":
                return "01";
            case "2":
                return "33";
            case "3":
                return "34";
            case "4":
                return "35";
            case "6":
                return "32";
            default:
                return "00";
        }
    }

    private String getERPBenifateRelation1(String relation, String compKey) {
        if (PolicyModel.COMPKEY_SUNSHINE.equals(compKey) || PolicyModel.COMPKEY_OLDMUTUAL_GUODIAN.equals(compKey)) {
            String type = "6";
            if ("00".equals(relation))//本人
                return "1";
            if ("07".equals(relation))//配偶
                return "4";
            //if("2".equals(relation))//子女
            //	return "2";
            //if("3".equals(relation))//父母
            //	return "3";
            return type;
        } else if (PolicyModel.COMPKEY_ANLLIAN.equals(compKey)) {
            String type = "6";
            if ("00".equals(relation))//本人
                return "1";
            if ("07".equals(relation) || "03".equals(relation))//配偶
                return "4";
            if ("4".equals(relation) || "5".equals(relation))//子女
                return "2";
            if ("01".equals(relation) || "02".equals(relation))//父母
                return "3";
            //if("2".equals(relation))//子女
            //	return "2";
            //if("3".equals(relation))//父母
            //	return "3";
            return type;
        } else if (PolicyModel.COMPKEY_YANGGUANG.equals(compKey)) {
            if ("1".equals(relation)) {//本人
                return "1";
            }

            return "1";
        } else {
            String type = "6";
            if ("5".equals(relation))//本人
                return "1";
            if ("1".equals(relation))//配偶
                return "4";
            if ("2".equals(relation))//子女
                return "2";
            if ("3".equals(relation))//父母
                return "3";
            return type;
        }
    }


}
