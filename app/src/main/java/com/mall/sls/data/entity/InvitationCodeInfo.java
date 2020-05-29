package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/5/29.
 * 描述：
 */
public class InvitationCodeInfo {
    //h5域名地址
    @SerializedName("baseUrl")
    private String baseUrl;
    //邀请码
    @SerializedName("invitationCode")
    private String invitationCode;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }
}
