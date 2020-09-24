package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/9/17.
 * 描述：
 */
public class BindResultInfo {
    //绑卡状态
    @SerializedName("bindStatus")
    private String bindStatus;
    //失败理由
    @SerializedName("message")
    private String message;

    public String getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(String bindStatus) {
        this.bindStatus = bindStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
