package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author jwc on 2020/6/24.
 * 描述：
 */
public class ConfirmCartOrderDetail {
    //地址
    @SerializedName("address")
    private AddressInfo addressInfo;
    //下单商品
    @SerializedName("carts")
    private List<CartItemInfo> cartItemInfos;
    //失效商品
    @SerializedName("hiddenCartList")
    private List<HiddenItemCartInfo> hiddenItemCartInfos;
    //可用优惠券数量
    @SerializedName("couponCount")
    private String couponCount ;
    //用户优惠券id
    @SerializedName("couponUserId")
    private String couponUserId;
    //优惠券优惠金额
    @SerializedName("couponPrice")
    private String couponPrice;
    //运费
    @SerializedName("freightPrice")
    private String freightPrice;
    //商品总金额
    @SerializedName("goodsTotalPrice")
    private String goodsTotalPrice;
    //订单总金额
    @SerializedName("orderTotalPrice")
    private String orderTotalPrice;
    //配送方式
    @SerializedName("peiSongType")
    private String peiSongType;
    //可以下单的购物车id
    @SerializedName("cartIds")
    private String cartIds;
    //免运费描述
    @SerializedName("freeShipDes")
    private String freeShipDes;
    //是否在配送范围  true 超出配送范围
    @SerializedName("outShip")
    private Boolean outShip;


    public AddressInfo getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(AddressInfo addressInfo) {
        this.addressInfo = addressInfo;
    }

    public List<CartItemInfo> getCartItemInfos() {
        return cartItemInfos;
    }

    public void setCartItemInfos(List<CartItemInfo> cartItemInfos) {
        this.cartItemInfos = cartItemInfos;
    }

    public List<HiddenItemCartInfo> getHiddenItemCartInfos() {
        return hiddenItemCartInfos;
    }

    public void setHiddenItemCartInfos(List<HiddenItemCartInfo> hiddenItemCartInfos) {
        this.hiddenItemCartInfos = hiddenItemCartInfos;
    }

    public String getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(String couponCount) {
        this.couponCount = couponCount;
    }

    public String getCouponUserId() {
        return couponUserId;
    }

    public void setCouponUserId(String couponUserId) {
        this.couponUserId = couponUserId;
    }

    public String getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(String couponPrice) {
        this.couponPrice = couponPrice;
    }

    public String getFreightPrice() {
        return freightPrice;
    }

    public void setFreightPrice(String freightPrice) {
        this.freightPrice = freightPrice;
    }

    public String getGoodsTotalPrice() {
        return goodsTotalPrice;
    }

    public void setGoodsTotalPrice(String goodsTotalPrice) {
        this.goodsTotalPrice = goodsTotalPrice;
    }

    public String getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(String orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public String getPeiSongType() {
        return peiSongType;
    }

    public void setPeiSongType(String peiSongType) {
        this.peiSongType = peiSongType;
    }

    public String getCartIds() {
        return cartIds;
    }

    public void setCartIds(String cartIds) {
        this.cartIds = cartIds;
    }

    public String getFreeShipDes() {
        return freeShipDes;
    }

    public void setFreeShipDes(String freeShipDes) {
        this.freeShipDes = freeShipDes;
    }

    public Boolean getOutShip() {
        return outShip;
    }

    public void setOutShip(Boolean outShip) {
        this.outShip = outShip;
    }
}
