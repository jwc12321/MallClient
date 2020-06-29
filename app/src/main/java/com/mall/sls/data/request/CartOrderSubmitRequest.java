package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author jwc on 2020/5/15.
 * 描述：
 */
public class CartOrderSubmitRequest {
    //地址id
    @SerializedName("addressId")
    private String addressId;
    //购物车ID
    @SerializedName("ids")
    private List<String> ids;
    //用户优惠券ID
    @SerializedName("userCouponId")
    private String userCouponId;
    //备注
    @SerializedName("message")
    private String message;
    //来源 android
    @SerializedName("orderFrom")
    private String orderFrom;

    public CartOrderSubmitRequest(String addressId, List<String> ids, String userCouponId, String message, String orderFrom) {
        this.addressId = addressId;
        this.ids = ids;
        this.userCouponId = userCouponId;
        this.message = message;
        this.orderFrom = orderFrom;
    }
}
