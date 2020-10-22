package com.zoro.mapper;

import com.zoro.dto.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CodeCheckMapper {

    List<String> getCodeName(String codeType, String codeValue, String compKey);

    List<Policy> getPolicyList();

    List<Policy> getList( @Param("start") int start);

    List<TbusPolicy>  getByPolicyCode(@Param("policyCode") String policyCode);

    List<TbusPolicy>  getByPrePolicyCode(@Param("prePolicyCode") String prePolicyCode);

    PolicyCust getHolder(@Param("policyKey") String policyKey);

    List<PolicyCust> getInsurance(@Param("policyKey") String policyKey);

    List<PolicyBeff> getBenifate(@Param("policyKey") String policyKey);

    List<OrderPolicyholderInsured> getHolderAndInsurance(@Param("orderMainUuid") String orderMainUuid);

    List<OrderBeneficiary> getBeneficiary(@Param("orderMainUuid") String orderMainUuid);

    List<OrderMain> getOrderList( @Param("start") int start);

    String getPorductNo(@Param("orderMainUuid") String orderMainUuid);

    List<OrderMain>  getMarryList();

}
