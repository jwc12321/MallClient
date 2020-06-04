package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/5/13.
 * 描述：
 */
public class BannerInfo {
    //bannerid
    @SerializedName("bannerId")
    private String bannerId;
    //banner链接url
    @SerializedName("link")
    private String link;
    //图片url
    @SerializedName("url")
    private String url;
    //是否跳转链接
    @SerializedName("linkOpen")
    private boolean linkOpen;
    //链接类型 0-外部链接 1-内部链接
    @SerializedName("linkType")
    private String   linkType;
    //本地跳转类型
    @SerializedName("nativeType")
    private String nativeType;
    //名字
    @SerializedName("name")
    private String name;

    public String getBannerId() {
        return bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isLinkOpen() {
        return linkOpen;
    }

    public void setLinkOpen(boolean linkOpen) {
        this.linkOpen = linkOpen;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
