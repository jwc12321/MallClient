package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/6/23.
 * 描述：
 */
public class CartAddRequest {
    //SKUid
    @SerializedName("productId")
    private String productId ;
    //数量
    @SerializedName("number")
    private String number;

    public CartAddRequest(String productId, String number) {
        this.productId = productId;
        this.number = number;
    }
}
