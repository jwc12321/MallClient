package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JWC on 2018/7/6.
 */

public class GoodsSpec implements Serializable {
    @SerializedName("name")
    private String name;
    @SerializedName("valueList")
    private List<ProductSkuInfo> specs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductSkuInfo> getSpecs() {
        return specs;
    }

    public void setSpecs(List<ProductSkuInfo> specs) {
        this.specs = specs;
    }
}
