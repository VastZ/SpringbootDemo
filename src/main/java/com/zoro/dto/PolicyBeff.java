package com.zoro.dto;

public class PolicyBeff {
    /** */
    private String beffkey;

    /** */
    private String policykey;

    /** */
    private String name;

    /** */
    private String sex;

    /** */
    private String idtype;

    /** */
    private String idcode;

    /** */
    private String relation;

    /** */
    private String marriage;

    /** */
    private Integer validflag;

    public String getBeffkey() {
        return beffkey;
    }

    public void setBeffkey(String beffkey) {
        this.beffkey = beffkey == null ? null : beffkey.trim();
    }

    public String getPolicykey() {
        return policykey;
    }

    public void setPolicykey(String policykey) {
        this.policykey = policykey == null ? null : policykey.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getIdtype() {
        return idtype;
    }

    public void setIdtype(String idtype) {
        this.idtype = idtype == null ? null : idtype.trim();
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode == null ? null : idcode.trim();
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation == null ? null : relation.trim();
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage == null ? null : marriage.trim();
    }

    public Integer getValidflag() {
        return validflag;
    }

    public void setValidflag(Integer validflag) {
        this.validflag = validflag;
    }
}