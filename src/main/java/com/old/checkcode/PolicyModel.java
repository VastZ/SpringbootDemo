package com.old.checkcode;

/**
 * @author zhang.wenhan
 * @description PolicyConstant
 * @date 2019/8/12 15:00
 */
public class PolicyModel {


    public final static String COMPKEY_TBANLIAN = "CO160304000000000001";//太保安联
    public final static String COMPKEY_SLS = "CO080529000000000001";  //苏黎世
    public final static String COMPKEY_PICC = "CO070703000000000001";
    public final static String COMPKEY_SUNSHINE = "CO080218000000000001";
    public final static String COMPKEY_OLDMUTUAL_GUODIAN = "CO060913000000000008";//瑞泰
    public final static String COMPKEY_ANLLIAN = "CO071120000000000001";//中德安联
    public final static String COMPKEY_RSA = "CO111104000000000001";//中德安联
    public final static String COMPKEY_CLP = "CO070723000000000011";//国寿产险
    public final static String COMPKEY_DUBON = "CO070619000000000002";//都邦产险
    public final static String COMPKEY_DAZHONG = "CO120329000000000001";//史带财险（原大众保险）
    public final static String COMPKEY_GUOHUA = "CO100624000000000001";//国华人寿保险公司
    public final static String COMPKEY_MEIYA = "CO090122000000000001";//美亚财产保险
    public final static String COMPKEY_HUAAN = "CO070619000000000003";//华安财产保险
    public final static String COMPKEY_DADI = "CO070723000000000005";//大地财产保险
    public final static String COMPKEY_ZHONGYI = "CO081008000000000001";//中意财产保险
    public final static String COMPKEY_XINHUA = "CO070124000000000002";//新华保险
    public final static String COMPKEY_TAIPING = "CO120306000000000001";//太平养老保险
    public final static String COMPKEY_TAIPINGCC = "CO070723000000000002";//太平财产保险 齿科
    //	public final static String COMPKEY_TAIPINGCC = "COCC0306000000000001";
    public final static String COMPKEY_HEZHONG = "CO090602000000000001";//合众人寿保险
    public final static String COMPKEY_ZHONGYIN = "CO090609000000000001";//中银保险
    public final static String COMPKEY_METLIFE = "CO081104000000000001";//中美联泰大都会人寿保险
    public final static String COMPKEY_PINGAN = "CO070723000000000001";//中国平安财险保险
    public final static String COMPKEY_ANLIAN_GENERAL = "CO090114000000000001";//安联财险保险
    public final static String COMPKEY_RMJK = "CO060913000000000004";//人民健康保险
    public final static String COMPKEY_YONGAN = "CO070621000000000001";//永安财险保险
    public final static String COMPKEY_LIAN = "CO120301000000000001";//利安人寿保险股份有限公司
    public final static String COMPKEY_TKYL = "CO140618000000000001";//泰康养老保险
    public final static String COMPKEY_ZGRS = "CO060913000000000001";//中国人寿保险
    public final static String COMPKEY_TaiPingYang = "CO070720000000000002";//太平洋财产保险
    public final static String COMPKEY_BaiNian = "CO101018000000000001";//百年人寿
    public final static String COMPKEY_TONGFANGQQ = "CO060913000000000010";//同方全球
    public final static String COMPKEY_TaiPingYangRS = "CO060913000000000002";//太平洋人寿保险
    public final static String COMPKEY_700du = "SC700000000000000001";//太平洋人寿保险
    public final static String COMPKEY_HuaXia = "CO080411000000000001";//华夏保险
    public final static String COMPKEY_ChangSheng = "CO071108000000000001";//长生人寿保险
    public final static String COMPKEY_ZHONGAN = "CO151111000000000001";//众安在线财产保险股份有限公司
    public final static String COMPKEY_PingAnHealth = "CO090730000000000001"; //平安健康保险 add by caicl
    public final static String COMPKEY_TaiKangRenShou = "CO061205000000000003";//泰康人寿
    public final static String COMPKEY_TaiKangZAIXIAN = "CO160815000000000001";//泰康在线
    public final static String COMPKEY_FuXingRenShou = "CO140619000000000007";//复星人寿
    public final static String COMPKEY_CHANGAN = "CO090914000000000001";//长安责任保险
    public final static String COMPKEY_ANSHENGTIANPING = "CO140318000000000001";//安盛天平财产保险
    public final static String COMPKEY_ANXIN="CO170320000000000001";//安心财产保险
    public final static String COMPKEY_YANGGUANG="CO070723000000000007";//阳光财产保险
    public final static String COMPKEY_YANGGUANG_YJX="CO070723000000000007_YJX";//阳光意外险
    public final static String COMPKEY_HUATAI="CO070621000000000003";//华泰财产保险


    public final static String PRODKEY_METLIFE001 = "metlife001";//大都会人寿短期公共交通工具意外伤害保险

    public final static String PRODKEY = "JXYX";

    public final static String NEW_POLICY = "S";//保存投保单
    public final static String PRE_POLICY = "P";//核保失败
    public final static String WAITPRE_POLICY = "W";//等待客户签字投保单
    public final static String WAITPAYFEE_POLICY = "F";//等待客户付费
    public final static String VALIDPRE_POLICY = "Y";//正式投保单
    public final static String EFFECT_POLICY = "E";//承保保单
    public final static String DECLINE_POLICY = "D";//拒保保单
    public final static String REMOVE_POLICY = "R";//撤单
    public final static String ACK_POLICY = "A";//签收回执
    public final static String CREMOVE_POLICY = "C";//犹豫期撤单

    public final static String PAYFINISH_POLICY = "H";//客户支付完成
    public final static String PAYERROR_POLICY = "O";//客户支付失败

    public final static String VALID = "Y";
    public final static String NVALID = "N";

    public final static String USER_CUSTOMER = "C";//客户主动注册
    public final static String USER_SALER = "I"; //销售人员
    public final static String USER_MINGYA = "S"; //明亚用户
    public final static String USER_COMP = "J"; //加盟商

    static String custInfo = "<a style=\"text-decoration:underline;color:red;font-size:14px\"  href=\"http://vip.tq.cn/vip/pageinfo.do?version=vip&admiuin=9133820&ltype=0&iscallback=0&agentid=1040992&action=acd&acd=1&type_code=1001\" target=\"_blank\">在线客服</a>";

    final static String USEPASSWORD = "mingyasunpdf";
    final static String OWNERPASSWORD = "sunmingyapdf";

    public final static String ONLINE_PAY_ALIPAY = "ALIPAY";//使用支付宝支付
    public final static String ONLINE_PAY_WEIXIN = "WEIXIN";//使用微信支付
    public final static String ONLINE_PAY_EASY = "EASY";//使用易生支付
    public final static String ONLINE_PAY_GDYL = "GDYL";//使用广东银联支付
    public final static String ONLINE_PAY_CCB = "CCB";//使用建行支付
    public final static String ONLINE_PAY_GOPAY = "GOPAY";//使用国付宝支付
    public final static String ONLINE_PAY_TENPAY = "TENPAY";//使用财付通支付
    public final static String ONLINE_PAY_WEIXINPAY = "WEIXINPAY";//使用财付通支付
    public final static String ONLINE_PAY_XINHUAPAY = "XINHUAPAY";//使用新华支付
    public static final String PERSON_STATE_ACTIVE = "已激活";
    public static final String PERSON_STATE_NOT_ACTIVE = "未激活";



}
