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
    //运费
    @SerializedName("freightPrice")
    private String freightPrice;
    //支付订单编号
    @SerializedName("tradeNo")
    private String tradeNo;
    //订单跟踪
    @SerializedName("shipOrderList")
    private List<ShipOrderInfo> shipOrderInfos;
    //顺丰H5页面url
    @SerializedName("sfH5Url")
    private String sfH5Url;
    //是否显示查看地图按钮:0-不显示，1-显示
    @SerializedName("showMap")
    private String showMap;
    //退款到账时间
    @SerializedName("refundConfirmTime")
    private String refundConfirmTime;
    //退款发起时间
    @SerializedName("refundTime")
    private String refundTime;
    //是否存在子订单
    @SerializedName("hasChild")
    private Boolean hasChild;
    //配送方式
    @SerializedName("peiSongType")
    private String peiSongType;
    //是否是普通商品
    @SerializedName("general")
    private Boolean general;
    //支付记录
    @SerializedName("payList")
    private List<PayRecordInfo> payRecordInfos;
    //支付描述
    @SerializedName("payDescription")
    private String payDescription;
    //是否存在退款记录
    @SerializedName("hasRefund")
    private Boolean hasRefund;
    //物流单号
    @SerializedName("waybillNo")
    private String waybillNo;

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


    public String getFreightPrice() {
        return freightPrice;
    }

    public void setFreightPrice(String freightPrice) {
        this.freightPrice = freightPrice;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public List<ShipOrderInfo> getShipOrderInfos() {
        return shipOrderInfos;
    }

    public void setShipOrderInfos(List<ShipOrderInfo> shipOrderInfos) {
        this.shipOrderInfos = shipOrderInfos;
    }

    public String getSfH5Url() {
        return sfH5Url;
    }

    public void setSfH5Url(String sfH5Url) {
        this.sfH5Url = sfH5Url;
    }

    public String getShowMap() {
        return showMap;
    }

    public void setShowMap(String showMap) {
        this.showMap = showMap;
    }

    public String getRefundConfirmTime() {
        return refundConfirmTime;
    }

    public void setRefundConfirmTime(String refundConfirmTime) {
        this.refundConfirmTime = refundConfirmTime;
    }

    public String getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(String refundTime) {
        this.refundTime = refundTime;
    }

    public Boolean getHasChild() {
        return hasChild;
    }

    public void setHasChild(Boolean hasChild) {
        this.hasChild = hasChild;
    }

    public String getPeiSongType() {
        return peiSongType;
    }

    public void setPeiSongType(String peiSongType) {
        this.peiSongType = peiSongType;
    }

    public Boolean getGeneral() {
        return general;
    }

    public void setGeneral(Boolean general) {
        this.general = general;
    }

    public List<PayRecordInfo> getPayRecordInfos() {
        return payRecordInfos;
    }

    public void setPayRecordInfos(List<PayRecordInfo> payRecordInfos) {
        this.payRecordInfos = payRecordInfos;
    }

    public String getPayDescription() {
        return payDescription;
    }

    public void setPayDescription(String payDescription) {
        this.payDescription = payDescription;
    }

    public Boolean getHasRefund() {
        return hasRefund;
    }

    public void setHasRefund(Boolean hasRefund) {
        this.hasRefund = hasRefund;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }
}


