package com.zoro.dto;

import java.util.Date;

public class TbusPolicy {
    /** 保单主键*/
    private String policyid;

    /** 保单号*/
    private String policycode;

    /** 投保单号*/
    private String prepolicycode;

    /** 保单类型*/
    private String policytype;

    /** 交单交单*/
    private Date receivedate;

    /** 投保人姓名*/
    private String hname;

    /** 投保人证件类型*/
    private String hcertitype;

    /** 投保人证件类型*/
    private String hcertitypename;

    /** 投保人证件号码*/
    private String hcerticode;

    /** 投保人性别*/
    private String hsex;

    /** 投保人性别*/
    private String hsexname;

    /** 投保人国籍*/
    private String hnational;

    /** 投保人国籍*/
    private String hnationalname;

    /** 投保人婚姻状况*/
    private String hmarrystate;

    /** 投保人婚姻状况*/
    private String hmarrystatename;

    /** 被保人姓名*/
    private String ipname;

    /** 与投保人关系*/
    private String iprelationcode;

    /** 与投保人关系*/
    private String iprelationname;

    /** 被保人证件类型*/
    private String ipcertitype;

    /** 被保人证件类型*/
    private String ipcertitypename;

    /** 被保人证件号码*/
    private String ipcerticode;

    /** 被保人性别*/
    private String ipsex;

    /** 被保人性别*/
    private String ipsexname;

    /** 被保人国籍*/
    private String ipnational;

    /** 被保人国籍*/
    private String ipnationalname;

    /** 被保人婚姻状况*/
    private String ipmarrystate;

    /** 被保人婚姻状况*/
    private String ipmarrystatename;

    /** 受益人姓名*/
    private String bpname;

    /** 与被保人关系*/
    private String bprelationcode;

    /** 与被保人关系*/
    private String bprelationname;

    /** 受益人证件类型*/
    private String bpcertitype;

    /** 受益人证件类型*/
    private String bpcertitypename;

    /** 受益人证件号码*/
    private String bpcerticode;

    /** 受益人性别*/
    private String bpsex;

    /** 受益人性别*/
    private String bpsexname;

    public String getPolicyid() {
        return policyid;
    }

    public void setPolicyid(String policyid) {
        this.policyid = policyid == null ? null : policyid.trim();
    }

    public String getPolicycode() {
        return policycode;
    }

    public void setPolicycode(String policycode) {
        this.policycode = policycode == null ? null : policycode.trim();
    }

    public String getPrepolicycode() {
        return prepolicycode;
    }

    public void setPrepolicycode(String prepolicycode) {
        this.prepolicycode = prepolicycode == null ? null : prepolicycode.trim();
    }

    public String getPolicytype() {
        return policytype;
    }

    public void setPolicytype(String policytype) {
        this.policytype = policytype == null ? null : policytype.trim();
    }

    public Date getReceivedate() {
        return receivedate;
    }

    public void setReceivedate(Date receivedate) {
        this.receivedate = receivedate;
    }

    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname == null ? null : hname.trim();
    }

    public String getHcertitype() {
        return hcertitype;
    }

    public void setHcertitype(String hcertitype) {
        this.hcertitype = hcertitype == null ? null : hcertitype.trim();
    }

    public String getHcertitypename() {
        return hcertitypename;
    }

    public void setHcertitypename(String hcertitypename) {
        this.hcertitypename = hcertitypename == null ? null : hcertitypename.trim();
    }

    public String getHcerticode() {
        return hcerticode;
    }

    public void setHcerticode(String hcerticode) {
        this.hcerticode = hcerticode == null ? null : hcerticode.trim();
    }

    public String getHsex() {
        return hsex;
    }

    public void setHsex(String hsex) {
        this.hsex = hsex == null ? null : hsex.trim();
    }

    public String getHsexname() {
        return hsexname;
    }

    public void setHsexname(String hsexname) {
        this.hsexname = hsexname == null ? null : hsexname.trim();
    }

    public String getHnational() {
        return hnational;
    }

    public void setHnational(String hnational) {
        this.hnational = hnational == null ? null : hnational.trim();
    }

    public String getHnationalname() {
        return hnationalname;
    }

    public void setHnationalname(String hnationalname) {
        this.hnationalname = hnationalname == null ? null : hnationalname.trim();
    }

    public String getHmarrystate() {
        return hmarrystate;
    }

    public void setHmarrystate(String hmarrystate) {
        this.hmarrystate = hmarrystate == null ? null : hmarrystate.trim();
    }

    public String getHmarrystatename() {
        return hmarrystatename;
    }

    public void setHmarrystatename(String hmarrystatename) {
        this.hmarrystatename = hmarrystatename == null ? null : hmarrystatename.trim();
    }

    public String getIpname() {
        return ipname;
    }

    public void setIpname(String ipname) {
        this.ipname = ipname == null ? null : ipname.trim();
    }

    public String getIprelationcode() {
        return iprelationcode;
    }

    public void setIprelationcode(String iprelationcode) {
        this.iprelationcode = iprelationcode == null ? null : iprelationcode.trim();
    }

    public String getIprelationname() {
        return iprelationname;
    }

    public void setIprelationname(String iprelationname) {
        this.iprelationname = iprelationname == null ? null : iprelationname.trim();
    }

    public String getIpcertitype() {
        return ipcertitype;
    }

    public void setIpcertitype(String ipcertitype) {
        this.ipcertitype = ipcertitype == null ? null : ipcertitype.trim();
    }

    public String getIpcertitypename() {
        return ipcertitypename;
    }

    public void setIpcertitypename(String ipcertitypename) {
        this.ipcertitypename = ipcertitypename == null ? null : ipcertitypename.trim();
    }

    public String getIpcerticode() {
        return ipcerticode;
    }

    public void setIpcerticode(String ipcerticode) {
        this.ipcerticode = ipcerticode == null ? null : ipcerticode.trim();
    }

    public String getIpsex() {
        return ipsex;
    }

    public void setIpsex(String ipsex) {
        this.ipsex = ipsex == null ? null : ipsex.trim();
    }

    public String getIpsexname() {
        return ipsexname;
    }

    public void setIpsexname(String ipsexname) {
        this.ipsexname = ipsexname == null ? null : ipsexname.trim();
    }

    public String getIpnational() {
        return ipnational;
    }

    public void setIpnational(String ipnational) {
        this.ipnational = ipnational == null ? null : ipnational.trim();
    }

    public String getIpnationalname() {
        return ipnationalname;
    }

    public void setIpnationalname(String ipnationalname) {
        this.ipnationalname = ipnationalname == null ? null : ipnationalname.trim();
    }

    public String getIpmarrystate() {
        return ipmarrystate;
    }

    public void setIpmarrystate(String ipmarrystate) {
        this.ipmarrystate = ipmarrystate == null ? null : ipmarrystate.trim();
    }

    public String getIpmarrystatename() {
        return ipmarrystatename;
    }

    public void setIpmarrystatename(String ipmarrystatename) {
        this.ipmarrystatename = ipmarrystatename == null ? null : ipmarrystatename.trim();
    }

    public String getBpname() {
        return bpname;
    }

    public void setBpname(String bpname) {
        this.bpname = bpname == null ? null : bpname.trim();
    }

    public String getBprelationcode() {
        return bprelationcode;
    }

    public void setBprelationcode(String bprelationcode) {
        this.bprelationcode = bprelationcode == null ? null : bprelationcode.trim();
    }

    public String getBprelationname() {
        return bprelationname;
    }

    public void setBprelationname(String bprelationname) {
        this.bprelationname = bprelationname == null ? null : bprelationname.trim();
    }

    public String getBpcertitype() {
        return bpcertitype;
    }

    public void setBpcertitype(String bpcertitype) {
        this.bpcertitype = bpcertitype == null ? null : bpcertitype.trim();
    }

    public String getBpcertitypename() {
        return bpcertitypename;
    }

    public void setBpcertitypename(String bpcertitypename) {
        this.bpcertitypename = bpcertitypename == null ? null : bpcertitypename.trim();
    }

    public String getBpcerticode() {
        return bpcerticode;
    }

    public void setBpcerticode(String bpcerticode) {
        this.bpcerticode = bpcerticode == null ? null : bpcerticode.trim();
    }

    public String getBpsex() {
        return bpsex;
    }

    public void setBpsex(String bpsex) {
        this.bpsex = bpsex == null ? null : bpsex.trim();
    }

    public String getBpsexname() {
        return bpsexname;
    }

    public void setBpsexname(String bpsexname) {
        this.bpsexname = bpsexname == null ? null : bpsexname.trim();
    }
}