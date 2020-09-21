package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author jwc on 2020/5/13.
 * 描述：
 */
public class CouponInfo implements Serializable {
    //用户优惠券ID
    @SerializedName("id")
    private String id;
    //优惠券规则ID
    @SerializedName("cid")
    private String cid;
    //优惠金额
    @SerializedName("discount")
    private String discount;
    //满减限制金额
    @SerializedName("min")
    private String min;
    //名字
    @SerializedName("name")
    private String name;
    //限制使用文本
    @SerializedName("limitCondition")
    private String limitCondition;
    //有效期开始时间
    @SerializedName("startTime")
    private String startTime;
    //有效期截止时间
    @SerializedName("endTime")
    private String endTime;
    //是否可用
    @SerializedName("available")
    private String available;
    //规则
    @SerializedName("description")
    private String description;
    //
    private boolean isUp=false;

    public boolean isUp() {
        return isUp;
    }

    public void setUp(boolean up) {
        isUp = up;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLimitCondition() {
        return limitCondition;
    }

    public void setLimitCondition(String limitCondition) {
        this.limitCondition = limitCondition;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }


}
