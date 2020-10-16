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
    @SerializedName("startTime")
    private String startTime;
    //到账时间
    @SerializedName("endTime")
    private String endTime;
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

    public Boolean getRefundSuccess() {
        return refundSuccess;
    }

    public void setRefundSuccess(Boolean refundSuccess) {
        this.refundSuccess = refundSuccess;
    }
}
