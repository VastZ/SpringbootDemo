package com.zoro.dto;

import java.util.Date;

public class Policy {

    private Integer id;
    /** */
    private String policykey;

    /** */
    private String compkey;

    /** */
    private String policyno;

    /** */
    private String prepolicycode;

    /** */
    private String policycode;

    /** */
    private Date createdate;

    /** */
    private Date confirmdate;

    /** */
    private Integer status;

    /** */
    private Integer validate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPolicykey() {
        return policykey;
    }

    public void setPolicykey(String policykey) {
        this.policykey = policykey == null ? null : policykey.trim();
    }

    public String getCompkey() {
        return compkey;
    }

    public void setCompkey(String compkey) {
        this.compkey = compkey == null ? null : compkey.trim();
    }

    public String getPolicyno() {
        return policyno;
    }

    public void setPolicyno(String policyno) {
        this.policyno = policyno == null ? null : policyno.trim();
    }

    public String getPrepolicycode() {
        return prepolicycode;
    }

    public void setPrepolicycode(String prepolicycode) {
        this.prepolicycode = prepolicycode == null ? null : prepolicycode.trim();
    }

    public String getPolicycode() {
        return policycode;
    }

    public void setPolicycode(String policycode) {
        this.policycode = policycode == null ? null : policycode.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getConfirmdate() {
        return confirmdate;
    }

    public void setConfirmdate(Date confirmdate) {
        this.confirmdate = confirmdate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getValidate() {
        return validate;
    }

    public void setValidate(Integer validate) {
        this.validate = validate;
    }
}