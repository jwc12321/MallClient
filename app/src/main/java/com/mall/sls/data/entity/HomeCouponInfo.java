package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/6/16.
 * 描述：
 */
public class HomeCouponInfo {
    @SerializedName("discount")
    private String discount;
    @SerializedName("min")
    private String min;

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }
}
