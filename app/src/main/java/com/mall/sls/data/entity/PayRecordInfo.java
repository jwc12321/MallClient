package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author jwc on 2020/10/10.
 * 描述：
 */
public class PayRecordInfo implements Serializable {
    //交易编号
    @SerializedName("payNo")
    private String payNo;
    //0-微信支付 1-支付宝支付 2-小程序支付 3-银行卡支付
    @SerializedName("payType")
    private String payType;
    //1-支付成功 2-支付处理中 3-支付失败
    @SerializedName("orderStatus")
    private String orderStatus;
    //支付金额
    @SerializedName("actualPrice")
    private String actualPrice;
    //支付时间
    @SerializedName("payTime")
    private String payTime;
    //三方支付单号
    @SerializedName("paySn")
    private String paySn;

    public String getPaySn() {
        return paySn;
    }

    public void setPaySn(String paySn) {
        this.paySn = paySn;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(String actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }
}
