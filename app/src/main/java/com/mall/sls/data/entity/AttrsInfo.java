package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AttrsInfo implements Serializable{
    //规格编号
    @SerializedName("id")
    private String id;
    //规格名称
    @SerializedName("name")
    private String name;
    //规格值列表
    @SerializedName("productAttrValues")
    private List<ProductAttrValuesInfo> productAttrValuesInfos;

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

    public List<ProductAttrValuesInfo> getProductAttrValuesInfos() {
        return productAttrValuesInfos;
    }

    public void setProductAttrValuesInfos(List<ProductAttrValuesInfo> productAttrValuesInfos) {
        this.productAttrValuesInfos = productAttrValuesInfos;
    }
}
