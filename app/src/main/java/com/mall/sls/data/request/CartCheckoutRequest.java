package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/5/15.
 * 描述：
 */
public class CartCheckoutRequest {
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

    public CartCheckoutRequest(String addressId, String cartId, String couponId, String userCouponId) {
        this.addressId = addressId;
        this.cartId = cartId;
        this.couponId = couponId;
        this.userCouponId = userCouponId;
    }
}
