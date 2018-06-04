package com.cn.webApp.model;
/**
 * 实体类
 * 角色组管理
 * @author rg
 * 上午10:10:57
 */
public class RoleGroup extends BaseObject{
    private String id;

    private String groupname;

    private String groupstate;

    private String customerunitcode;

    private String roleids;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname == null ? null : groupname.trim();
    }

    public String getGroupstate() {
        return groupstate;
    }

    public void setGroupstate(String groupstate) {
        this.groupstate = groupstate == null ? null : groupstate.trim();
    }

    public String getCustomerunitcode() {
        return customerunitcode;
    }

    public void setCustomerunitcode(String customerunitcode) {
        this.customerunitcode = customerunitcode == null ? null : customerunitcode.trim();
    }

    public String getRoleids() {
        return roleids;
    }

    public void setRoleids(String roleids) {
        this.roleids = roleids == null ? null : roleids.trim();
    }
}