package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/10/26.
 * 描述：
 */
public class PointsRecordInfo {
    //内容
    @SerializedName("content")
    private String content;
    //数量
    @SerializedName("count")
    private String count;
    //时间
    @SerializedName("addTime")
    private String addTime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
}
