package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author jwc on 2020/5/18.
 * 描述：
 */
public class LocalTeam {
    @SerializedName("list")
    private List<GoodsItemInfo> goodsItemInfos;

    public List<GoodsItemInfo> getGoodsItemInfos() {
        return goodsItemInfos;
    }

    public void setGoodsItemInfos(List<GoodsItemInfo> goodsItemInfos) {
        this.goodsItemInfos = goodsItemInfos;
    }
}
