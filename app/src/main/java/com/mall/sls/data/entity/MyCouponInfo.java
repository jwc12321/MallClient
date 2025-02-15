package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author jwc on 2020/5/15.
 * 描述：
 */
public class MyCouponInfo {
    @SerializedName("list")
    private List<CouponInfo> couponInfos;
    @SerializedName("total")
    private String total;

    public List<CouponInfo> getCouponInfos() {
        return couponInfos;
    }

    public void setCouponInfos(List<CouponInfo> couponInfos) {
        this.couponInfos = couponInfos;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
