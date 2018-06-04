package com.cn.webApp.model;
/**
 * 实体类
 * 法人客户-应用授权业务功能信息表
 * @author rg
 * 下午2:45:24
 */
public class LegalPersonModule extends BaseObject{
    private String id;

    private String appid;

    private String appmodelid;

    private Integer sortid;

    private String customerunitcode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getAppmodelid() {
        return appmodelid;
    }

    public void setAppmodelid(String appmodelid) {
        this.appmodelid = appmodelid == null ? null : appmodelid.trim();
    }

    public Integer getSortid() {
        return sortid;
    }

    public void setSortid(Integer sortid) {
        this.sortid = sortid;
    }

    public String getCustomerunitcode() {
        return customerunitcode;
    }

    public void setCustomerunitcode(String customerunitcode) {
        this.customerunitcode = customerunitcode == null ? null : customerunitcode.trim();
    }
}