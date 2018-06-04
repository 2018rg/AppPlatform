package com.cn.webApp.model;

import java.util.Date;
/**
 * 实体类
 * 法人授权
 * @author rg
 * 上午9:45:57
 */
public class LegalPersonAuthority extends BaseObject{
    private String id;

    private Date endTime;

    private String customerunitcode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCustomerunitcode() {
        return customerunitcode;
    }

    public void setCustomerunitcode(String customerunitcode) {
        this.customerunitcode = customerunitcode == null ? null : customerunitcode.trim();
    }
}