package com.tangl.wiki.po;

public class UserQueryPO extends PagePO {
    private String loginName;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Override
    public String toString() {
        return "UserQueryReq{" +
                "loginName='" + loginName + '\'' +
                "} " + super.toString();
    }
}