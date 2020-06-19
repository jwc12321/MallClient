package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/6/19.
 * 描述：
 */
public class LinkUrlInfo {
    //链接url
    @SerializedName("link")
    private String link;
    //链接类型 0-外部链接 1-内部链接
    @SerializedName("linkType")
    private String   linkType;
    //本地跳转类型
    @SerializedName("nativeType")
    private String nativeType;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getNativeType() {
        return nativeType;
    }

    public void setNativeType(String nativeType) {
        this.nativeType = nativeType;
    }
}
