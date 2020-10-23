package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/7/13.
 * 描述：
 */
public class UploadUrlInfo {
    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
