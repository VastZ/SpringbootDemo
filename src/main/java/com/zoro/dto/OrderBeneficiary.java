package com.zoro.dto;

public class OrderBeneficiary {
    /** */
    private Long id;

    /** */
    private String uuid;

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
    private String benefitorder;

    /** */
    private String relationship;

    /** */
    private String beneficiarytype;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
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

    public String getBenefitorder() {
        return benefitorder;
    }

    public void setBenefitorder(String benefitorder) {
        this.benefitorder = benefitorder == null ? null : benefitorder.trim();
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship == null ? null : relationship.trim();
    }

    public String getBeneficiarytype() {
        return beneficiarytype;
    }

    public void setBeneficiarytype(String beneficiarytype) {
        this.beneficiarytype = beneficiarytype == null ? null : beneficiarytype.trim();
    }
}