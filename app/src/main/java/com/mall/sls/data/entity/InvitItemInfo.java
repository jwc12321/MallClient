package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/5/19.
 * 描述：
 */
public class InvitItemInfo {
    //头像
    @SerializedName("avatar")
    private String avatar;
    //电话
    @SerializedName("mobile")
    private String mobile;
    //等级 0-普通 1-认证会员 2-超级会员
    @SerializedName("userLevel")
    private String userLevel;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }
}
