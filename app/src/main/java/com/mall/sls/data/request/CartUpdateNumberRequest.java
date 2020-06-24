package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/6/24.
 * 描述：
 */
public class CartUpdateNumberRequest {
    //购物车商品id
    @SerializedName("id")
    private String id;
    //数量
    @SerializedName("number")
    private String number;

    public CartUpdateNumberRequest(String id, String number) {
        this.id = id;
        this.number = number;
    }
}
