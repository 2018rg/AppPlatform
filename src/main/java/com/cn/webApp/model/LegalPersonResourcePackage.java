package com.cn.webApp.model;
/**
 * 租户-授权资源包对应表
 * 实体类
 * @author rg
 * 下午2:51:13
 */
public class LegalPersonResourcePackage extends BaseObject{
    private String id;

    private String resourcepackageid;

    private String customerunitcode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getResourcepackageid() {
        return resourcepackageid;
    }

    public void setResourcepackageid(String resourcepackageid) {
        this.resourcepackageid = resourcepackageid == null ? null : resourcepackageid.trim();
    }

    public String getCustomerunitcode() {
        return customerunitcode;
    }

    public void setCustomerunitcode(String customerunitcode) {
        this.customerunitcode = customerunitcode == null ? null : customerunitcode.trim();
    }
}