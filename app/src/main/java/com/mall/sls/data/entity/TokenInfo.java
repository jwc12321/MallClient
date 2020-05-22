package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/5/6.
 * 描述：
 */
public class TokenInfo {
    //登录token
    @SerializedName("token")
    private String token;
    @SerializedName("userInfo")
    private UserInfo userInfo;
    @SerializedName("unionId")
    private String unionId;



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
}
