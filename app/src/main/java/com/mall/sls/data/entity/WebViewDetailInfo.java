package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by JWC on 2018/4/27.
 */

public class WebViewDetailInfo implements Serializable{

    /**
     * 标题
     */
    @SerializedName("Title")
    private String title;

    /**
     * Webview内容所指向的Url
     */
    @SerializedName("Url")
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
