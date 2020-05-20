package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/5/20.
 * 描述：
 */
public class UserPayDtoRequest {
    @SerializedName("orderType")
    private String orderType;
    @SerializedName("payType")
    private String payType;

    public UserPayDtoRequest(String orderType, String payType) {
        this.orderType = orderType;
        this.payType = payType;
    }
}
