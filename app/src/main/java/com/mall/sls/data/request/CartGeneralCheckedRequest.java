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
    //用户优惠券id 不传参数或0使用默认 传-1不使用优惠券
    @SerializedName("userCouponId")
    private String userCouponId;
    //配送方式
    @SerializedName("shipChannel")
    private String shipChannel;

    public CartGeneralCheckedRequest(String addressId, List<String> ids, String userCouponId,String shipChannel) {
        this.addressId = addressId;
        this.ids = ids;
        this.userCouponId = userCouponId;
        this.shipChannel=shipChannel;
    }
}
