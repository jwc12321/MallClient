package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/5/19.
 * 描述：
 */
public class MessageItemInfo {
    //图片
    @SerializedName("imageUrl")
    private String imageUrl;
    //标题
    @SerializedName("title")
    private String title;
    //内容
    @SerializedName("content")
    private String content;
    //时间
    @SerializedName("addTime")
    private String addTime;
    //消息类型Id
    @SerializedName("typeId")
    private String typeId;
    //相关Id 根据type来定 1订单id
    @SerializedName("associatedId")
    private String associatedId;
    //id
    @SerializedName("id")
    private String id;
    //0未读 1已读
    @SerializedName("status")
    private String status;
    //1:订单 2：其他
    @SerializedName("linkUrl")
    private String linkUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getAssociatedId() {
        return associatedId;
    }

    public void setAssociatedId(String associatedId) {
        this.associatedId = associatedId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
}
