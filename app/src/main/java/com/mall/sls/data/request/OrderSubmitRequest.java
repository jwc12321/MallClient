package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/5/15.
 * 描述：
 */
public class OrderSubmitRequest {
    //地址id
    @SerializedName("addressId")
    private String addressId;
    //购物车ID
    @SerializedName("cartId")
    private String cartId;
    //优惠券规则ID
    @SerializedName("couponId")
    private String couponId;
    //用户优惠券ID
    @SerializedName("userCouponId")
    private String userCouponId;
    //备注
    @SerializedName("message")
    private String message;
    //来源 android
    @SerializedName("orderFrom")
    private String orderFrom;
    //配送方式
    @SerializedName("shipChannel")
    private String shipChannel;

    public OrderSubmitRequest(String addressId, String cartId, String couponId, String userCouponId, String message, String orderFrom,String shipChannel) {
        this.addressId = addressId;
        this.cartId = cartId;
        this.couponId = couponId;
        this.userCouponId = userCouponId;
        this.message = message;
        this.orderFrom = orderFrom;
        this.shipChannel=shipChannel;
    }
}
