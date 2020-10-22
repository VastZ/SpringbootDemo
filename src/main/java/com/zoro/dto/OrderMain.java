package com.zoro.dto;

public class OrderMain {
    private Long id;

    /** */
    private String uuid;

    /** */
    private String delflag;

    /** */
    private String orderid;

    /** */
    private String ordertime;

    /** 保单号*/
    private String insuranceid;

    /** ERP主键*/
    private String erpuuid;


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

    public String getDelflag() {
        return delflag;
    }

    public void setDelflag(String delflag) {
        this.delflag = delflag == null ? null : delflag.trim();
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime == null ? null : ordertime.trim();
    }

    public String getInsuranceid() {
        return insuranceid;
    }

    public void setInsuranceid(String insuranceid) {
        this.insuranceid = insuranceid == null ? null : insuranceid.trim();
    }

    public String getErpuuid() {
        return erpuuid;
    }

    public void setErpuuid(String erpuuid) {
        this.erpuuid = erpuuid == null ? null : erpuuid.trim();
    }
}