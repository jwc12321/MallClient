package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

public class AppUrlInfo {
    //是否是最新版本
    @SerializedName("ifLatest")
    private Boolean ifLatest;
    //是否需要强制更新
    @SerializedName("forceUpdate")
    private Boolean forceUpdate;
    //内容
    @SerializedName("message")
    private String message;
    //跟新url
    @SerializedName("url")
    private String url;

    public Boolean getIfLatest() {
        return ifLatest;
    }

    public void setIfLatest(Boolean ifLatest) {
        this.ifLatest = ifLatest;
    }

    public Boolean getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(Boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
