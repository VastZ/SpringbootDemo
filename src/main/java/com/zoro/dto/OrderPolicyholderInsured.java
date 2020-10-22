package com.zoro.dto;

public class OrderPolicyholderInsured {
    /** */
    private String uuid;

    /** */
    private String delflag;

    /** */
    private String ordermainuuid;

    /** */
    private String name;

    /** */
    private String ename;

    /** */
    private String certificatestype;

    /** */
    private String certificatesnumber;

    /** */
    private String sex;

    /** */
    private String maritalstatus;

    /** */
    private String relationship;

    /** */
    private String type;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getDelflag() {
        return delflag;
    }

    public void setDelflag(String delflag) {
        this.delflag = delflag == null ? null : delflag.trim();
    }

    public String getOrdermainuuid() {
        return ordermainuuid;
    }

    public void setOrdermainuuid(String ordermainuuid) {
        this.ordermainuuid = ordermainuuid == null ? null : ordermainuuid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename == null ? null : ename.trim();
    }

    public String getCertificatestype() {
        return certificatestype;
    }

    public void setCertificatestype(String certificatestype) {
        this.certificatestype = certificatestype == null ? null : certificatestype.trim();
    }

    public String getCertificatesnumber() {
        return certificatesnumber;
    }

    public void setCertificatesnumber(String certificatesnumber) {
        this.certificatesnumber = certificatesnumber == null ? null : certificatesnumber.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getMaritalstatus() {
        return maritalstatus;
    }

    public void setMaritalstatus(String maritalstatus) {
        this.maritalstatus = maritalstatus == null ? null : maritalstatus.trim();
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship == null ? null : relationship.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}