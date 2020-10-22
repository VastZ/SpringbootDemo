package com.zoro.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zoro.mapper.QibaiduMapper;
import com.zoro.service.QibaiduService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhang.wenhan
 * @description QibaiduServiceImpl
 * @date 2019/12/2 13:14
 */
@Service
public class QibaiduServiceImpl implements QibaiduService {

    @Autowired
    QibaiduMapper qibaiduMapper;

    @Override
    public void dealWithdrawApply(String name, String applyMoney, String note) {

        List<JSONObject> list = qibaiduMapper.getApplyInfo(name, applyMoney);
        if (CollectionUtils.isNotEmpty(list) && list.size() != 1) {
            System.err.println("匹配到多条数据，请手动处理");
            return;
        }
        JSONObject json = list.get(0);
        System.out.println("update withdraw_apply set state = '2', note = '"
                + note
                + "' where uuid = '" +
                json.getString("uuid") + "';");
        BigDecimal money = json.getBigDecimal("applyMoney");
        BigDecimal accountBanlance = json.getBigDecimal("accountBalance");
        double banlance = money.add(accountBanlance).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println("update customer_account set accountBalance = '"
                + banlance
                + "' where uuid = '" +
                json.getString("acUuid") + "';");
        System.out.println("update virtual_account_customer_log set operType = '0', description = '"
                + note
                + "', nowbalance = '" + banlance
                + "' where uuid = '" +
                json.getString("logUuid") + "';");
    }
}
