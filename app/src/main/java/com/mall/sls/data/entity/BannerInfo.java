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
}
