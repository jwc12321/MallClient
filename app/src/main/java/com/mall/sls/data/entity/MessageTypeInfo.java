package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/5/19.
 * 描述：
 */
public class MessageTypeInfo {
    @SerializedName("iconImg")
    private String iconImg;
    @SerializedName("type")
    private String type;
    @SerializedName("firstMsgContent")
    private String firstMsgContent;
    @SerializedName("id")
    private String id ;

    public String getIconImg() {
        return iconImg;
    }

    public void setIconImg(String iconImg) {
        this.iconImg = iconImg;
    }

    public String getFirstMsgContent() {
        return firstMsgContent;
    }

    public void setFirstMsgContent(String firstMsgContent) {
        this.firstMsgContent = firstMsgContent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
