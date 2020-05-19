package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/5/19.
 * 描述：
 */
public class MsgReadRequest {
    //消息id
    @SerializedName("msgId")
    private String msgId;
    @SerializedName("status")
    private String status;

    public MsgReadRequest(String msgId, String status) {
        this.msgId = msgId;
        this.status = status;
    }
}
