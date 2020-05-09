package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductAttrValuesInfo implements Serializable {
    //规格编号
    @SerializedName("attrId")
    private String attrId;
    //规格值编号
    @SerializedName("id")
    private String id;
    //规格值
    @SerializedName("name")
    private String name;

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

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
}
