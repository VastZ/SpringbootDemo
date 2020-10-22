package com.zoro.dto;

public class CheckCode {
    /** */
    private Integer id;

    /** */
    private String policycode;

    /** */
    private String code;

    /** */
    private String erpCode;

    /** 700度转换后的code*/
    private String turnCode;

    /** 证件号*/
    private String certcode;

    /** 1 投保人性别
2 投保人婚姻
3 被保人性别
4 被保人婚姻
5 被保人关系
6 受益人性别
7 受益人关系*/
    private Integer state;

    public CheckCode() {
    }

    public CheckCode(String policycode, String code, String erpCode, String turnCode, String certcode, Integer state) {
        this.policycode = policycode;
        this.code = code;
        this.erpCode = erpCode;
        this.turnCode = turnCode;
        this.certcode = certcode;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPolicycode() {
        return policycode;
    }

    public void setPolicycode(String policycode) {
        this.policycode = policycode == null ? null : policycode.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getErpCode() {
        return erpCode;
    }

    public void setErpCode(String erpCode) {
        this.erpCode = erpCode == null ? null : erpCode.trim();
    }

    public String getTurnCode() {
        return turnCode;
    }

    public void setTurnCode(String turnCode) {
        this.turnCode = turnCode == null ? null : turnCode.trim();
    }

    public String getCertcode() {
        return certcode;
    }

    public void setCertcode(String certcode) {
        this.certcode = certcode == null ? null : certcode.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}