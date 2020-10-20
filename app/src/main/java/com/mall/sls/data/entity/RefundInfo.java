package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/7/17.
 * 描述：
 */
public class RefundInfo {
    //退款编号
    @SerializedName("refundNo")
    private String refundNo;
    //退款金额
    @SerializedName("refundAmount")
    private String refundAmount;
    //发起时间
    @SerializedName("addTime")
    private String addTime;
    //到账时间
    @SerializedName("updateTime")
    private String updateTime;
    //是否到账成功
    @SerializedName("refundSuccess")
    private Boolean refundSuccess;

    public String getRefundNo() {
        return refundNo;
    }

    public void setRefundNo(String refundNo) {
        this.refundNo = refundNo;
    }

    public String getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getRefundSuccess() {
        return refundSuccess;
    }

    public void setRefundSuccess(Boolean refundSuccess) {
        this.refundSuccess = refundSuccess;
    }
}
