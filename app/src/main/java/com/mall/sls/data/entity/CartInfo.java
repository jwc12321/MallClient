package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author jwc on 2020/6/24.
 * 描述：
 */
public class CartInfo {
    //有效
    @SerializedName("normalList")
    private List<CartItemInfo> normalList;
    //失效商品
    @SerializedName("cancelList")
    private List<HiddenItemCartInfo> cancelList;

    public List<CartItemInfo> getNormalList() {
        return normalList;
    }

    public void setNormalList(List<CartItemInfo> normalList) {
        this.normalList = normalList;
    }

    public List<HiddenItemCartInfo> getCancelList() {
        return cancelList;
    }

    public void setCancelList(List<HiddenItemCartInfo> cancelList) {
        this.cancelList = cancelList;
    }
}
