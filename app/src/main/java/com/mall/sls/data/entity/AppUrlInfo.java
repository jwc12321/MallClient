package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

public class AppUrlInfo {
    //是否是最新版本
    @SerializedName("ifLatest")
    private boolean ifLatest;
    //是否需要强制更新
    @SerializedName("forceUpdate")
    private boolean forceUpdate;
    //内容
    @SerializedName("message")
    private String message;
    //跟新url
    @SerializedName("url")
    private String url;
    //当前最新版本
    @SerializedName("currentVersion")
    private String currentVersion;

    public boolean isIfLatest() {
        return ifLatest;
    }

    public void setIfLatest(boolean ifLatest) {
        this.ifLatest = ifLatest;
    }

    public boolean isForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
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

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }
}
