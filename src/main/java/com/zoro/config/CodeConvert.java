package com.zoro.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author zhang.wenhan
 * @description CodeConvert
 * @date 2019/8/21 13:20
 */
public class CodeConvert {
    //700度code 0：女；1：男
    //erp code 2:女；1：男
    public static final String  sexCode1 = "{\"0\":\"2\", \"1\":\"1\"}";
    //700度 code 1:身份证; 2:军官证;3:护照;4:港澳台通行证;5:其他，6户口本
    //erp code  1:身份证、2：护照、3：军官证、4其他
    public static final String certificatesType1 = "{\"1\":\"1\", \"3\":\"2\",\"2\":\"3\",\"4\":\"4\",\"5\":\"4\",\"6\":\"5\",\"01\":\"4\",\"02\":\"4\",\"04\":\"4\",\"7\":\"4\",\"8\":\"4\"}";
    //700度code a:本人;b:妻子;c:丈夫;d:父亲;e:母亲;f:儿子;g:女儿;h:其他具有抚养或赡养关系的家庭成员或近亲属;i:劳动关系
    //erp code 1-本人;2-父母;3-子女;4-配偶;5-监护人;31-其他;
    public static final String holderInsuredRelationCode1 = "{\"a\":\"1\", \"b\":\"4\",\"c\":\"4\", \"d\":\"3\",\"e\":\"3\", \"f\":\"2\",\"g\":\"2\",\"h\":\"31\",\"i\":\"31\",\"j\":\"31\",\"z\":\"31\"}";
    //加受益人关系转换
    // 本人-1.父母-2，子女-3. 配偶-4.监护人-5. 其它-6
    //a:本人;b:妻子;c:丈夫;d:父亲;e:母亲;f:儿子;g:女儿;
    public static final String benefitInsuredRelationCode1 = "{\"a\":\"1\",\"b\":\"4\",\"c\":\"4\",\"d\":\"2\",\"e\":\"2\",\"f\":\"3\",\"g\":\"3\",\"h\":\"31\",\"i\":\"31\",\"j\":\"31\",\"z\":\"31\"}";//受益人被保人关系（受益人X是被保人的Y）

    public static final String holderInsuredRelationCode2 = "{\"a\":\"1\", \"b\":\"4\",\"c\":\"4\", \"d\":\"2\",\"e\":\"2\", \"f\":\"3\",\"g\":\"3\",\"h\":\"31\",\"i\":\"31\",\"j\":\"31\"}";


    public static final JSONObject sexCode = JSON.parseObject(sexCode1);
    public static final JSONObject certificatesTypeCode = JSON.parseObject(certificatesType1);
    public static final JSONObject holderInsuredRelationCode = JSON.parseObject(holderInsuredRelationCode1);
    public static final JSONObject benefitInsuredRelationCode = JSON.parseObject(benefitInsuredRelationCode1);

    public static final JSONObject homeHolderInsuredRelationCode = JSON.parseObject(holderInsuredRelationCode2);


}
