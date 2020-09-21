package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author jwc on 2020/9/21.
 * 描述：
 */
public class SecondCategoryInfo {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name ;
    @SerializedName("goodsVo")
    private List<GoodsItemInfo> goodsItemInfos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GoodsItemInfo> getGoodsItemInfos() {
        return goodsItemInfos;
    }

    public void setGoodsItemInfos(List<GoodsItemInfo> goodsItemInfos) {
        this.goodsItemInfos = goodsItemInfos;
    }
}
