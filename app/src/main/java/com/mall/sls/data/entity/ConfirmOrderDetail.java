package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author jwc on 2020/5/15.
 * 描述：
 */
public class ConfirmOrderDetail implements Serializable {
    //地址
    @SerializedName("checkedAddress")
    private AddressInfo addressInfo;
    //商品
    @SerializedName("checkedGoodsList")
    private List<CheckedGoods> checkedGoods;
    //可用优惠券数量
    @SerializedName("availableCouponLength")
    private String availableCouponLength;
    //购物车ID
    @SerializedName("cartId")
    private String cartId;
    //商品总金额
    @SerializedName("goodsTotalPrice")
    private String goodsTotalPrice;
    //订单总金额
    @SerializedName("orderTotalPrice")
    private String orderTotalPrice;
    //优惠券优惠金额
    @SerializedName("couponPrice")
    private String couponPrice;
    //优惠券ID 值为-1或0则用户不使用优惠券，值为0则自动选择优惠券
    @SerializedName("couponId")
    private String couponId;
    //用户优惠券ID
    @SerializedName("userCouponId")
    private String userCouponId;
    //产品描述
    @SerializedName("brief")
    private String brief;
    //配送方式
    @SerializedName("peiSongType")
    private String peiSongType;
    //运费
    @SerializedName("freightPrice")
    private String freightPrice;
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

    public List<CheckedGoods> getCheckedGoods() {
        return checkedGoods;
    }

    public void setCheckedGoods(List<CheckedGoods> checkedGoods) {
        this.checkedGoods = checkedGoods;
    }

    public String getAvailableCouponLength() {
        return availableCouponLength;
    }

    public void setAvailableCouponLength(String availableCouponLength) {
        this.availableCouponLength = availableCouponLength;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
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

    public String getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(String couponPrice) {
        this.couponPrice = couponPrice;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getUserCouponId() {
        return userCouponId;
    }

    public void setUserCouponId(String userCouponId) {
        this.userCouponId = userCouponId;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getPeiSongType() {
        return peiSongType;
    }

    public void setPeiSongType(String peiSongType) {
        this.peiSongType = peiSongType;
    }

    public String getFreightPrice() {
        return freightPrice;
    }

    public void setFreightPrice(String freightPrice) {
        this.freightPrice = freightPrice;
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
