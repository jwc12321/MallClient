package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/5/19.
 * 描述：
 */
public class MsgIdRequest {
    //消息id
    @SerializedName("msgId")
    private String msgId;

    public MsgIdRequest(String msgId) {
        this.msgId = msgId;
    }
}
