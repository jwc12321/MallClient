package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author jwc on 2020/5/19.
 * 描述：
 */
public class MessageInfo {
    @SerializedName("list")
    private List<MessageItemInfo> messageItemInfos;

    public List<MessageItemInfo> getMessageItemInfos() {
        return messageItemInfos;
    }

    public void setMessageItemInfos(List<MessageItemInfo> messageItemInfos) {
        this.messageItemInfos = messageItemInfos;
    }
}
