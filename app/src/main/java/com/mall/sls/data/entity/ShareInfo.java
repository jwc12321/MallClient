package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/5/26.
 * 描述：
 */
public class ShareInfo {
    //推荐吗
    @SerializedName("invitationCode")
    private String invitationCode;
    //推荐链接
    @SerializedName("shareUrl")
    private String shareUrl;
    //海报图
    @SerializedName("photoUrl")
    private String photoUrl;

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
