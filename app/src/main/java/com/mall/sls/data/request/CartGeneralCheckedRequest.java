package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author jwc on 2020/6/24.
 * 描述：
 */
public class CartGeneralCheckedRequest {
    //地址id
    @SerializedName("addressId")
    private String addressId;
    //购物车中的商品id
    @SerializedName("ids")
    private List<String> ids ;
    //优惠卷id
    @SerializedName("userCouponId")
    private String userCouponId;

    public CartGeneralCheckedRequest(String addressId, List<String> ids, String userCouponId) {
        this.addressId = addressId;
        this.ids = ids;
        this.userCouponId = userCouponId;
    }
}
