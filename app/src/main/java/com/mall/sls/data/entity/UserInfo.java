package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/5/6.
 * 描述：
 */
public class UserInfo {
    //手机号
    @SerializedName("mobile")
    private String mobile;
    //头像
    @SerializedName("avatarUrl")
    private String avatarUrl;
    //用户级别 0-普通 1-认证 2-超级
    @SerializedName("userLevel")
    private String userLevel;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }
}
