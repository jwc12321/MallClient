package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.PropertyResourceBundle;

/**
 * @author jwc on 2020/5/11.
 * 描述：
 */
public class GoodsOrderDetails {
    //订单id
    @SerializedName("id")
    private String id;
    //订单创建时间
    @SerializedName("addTime")
    private String addTime;
    //订单状态
    @SerializedName("orderStatus")
    private String orderStatus;
    //商品列表
    @SerializedName("orderGoodsVoList")
    private List<OrderGoodsVo> orderGoodsVos;
    //实付款
    @SerializedName("actualPrice")
    private String actualPrice;
    //优惠券优惠金额
    @SerializedName("couponPrice")
    private String couponPrice;
    //商品金额
    @SerializedName("goodsPrice")
    private String goodsPrice;
    //收货人
    @SerializedName("consignee")
    private String consignee;
    //地址
    @SerializedName("address")
    private String address;
    //手机号
    @SerializedName("mobile")
    private String mobile;
    //支付方式
    @SerializedName("payModeText")
    private String payModeText;
    //订单编号
    @SerializedName("orderSn")
    private String orderSn;
    //订单支付时间
    @SerializedName("payTime")
    private String payTime;
    //订单备注
    @SerializedName("message")
    private String message;
    //支付截止时间
    @SerializedName("payLimitTime")
    private String payLimitTime;
    //服务器当前时间
    @SerializedName("systemTime")
    private String systemTime;
    //订单支付超时时间（分钟）
    @SerializedName("payTimeoutMinute")
    private String payTimeoutMinute;
    //拼团结束时间
    @SerializedName("endGrouponTime")
    private String endGrouponTime;
    //是否是活动团
    @SerializedName("activity")
    private Boolean isActivity;
    //活动id
    @SerializedName("grouponLinkId")
    private String grouponLinkId;

    public String getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(String couponPrice) {
        this.couponPrice = couponPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderGoodsVo> getOrderGoodsVos() {
        return orderGoodsVos;
    }

    public void setOrderGoodsVos(List<OrderGoodsVo> orderGoodsVos) {
        this.orderGoodsVos = orderGoodsVos;
    }

    public String getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(String actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPayModeText() {
        return payModeText;
    }

    public void setPayModeText(String payModeText) {
        this.payModeText = payModeText;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPayLimitTime() {
        return payLimitTime;
    }

    public void setPayLimitTime(String payLimitTime) {
        this.payLimitTime = payLimitTime;
    }

    public String getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(String systemTime) {
        this.systemTime = systemTime;
    }

    public String getPayTimeoutMinute() {
        return payTimeoutMinute;
    }

    public void setPayTimeoutMinute(String payTimeoutMinute) {
        this.payTimeoutMinute = payTimeoutMinute;
    }

    public String getEndGrouponTime() {
        return endGrouponTime;
    }

    public void setEndGrouponTime(String endGrouponTime) {
        this.endGrouponTime = endGrouponTime;
    }

    public Boolean getActivity() {
        return isActivity;
    }

    public void setActivity(Boolean activity) {
        isActivity = activity;
    }

    public String getGrouponLinkId() {
        return grouponLinkId;
    }

    public void setGrouponLinkId(String grouponLinkId) {
        this.grouponLinkId = grouponLinkId;
    }

}
