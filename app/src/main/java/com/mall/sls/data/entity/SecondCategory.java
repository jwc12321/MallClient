package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author jwc on 2020/9/21.
 * 描述：
 */
public class SecondCategory {
    @SerializedName("list")
    private List<SecondCategoryInfo> secondCategoryInfos;

    public List<SecondCategoryInfo> getSecondCategoryInfos() {
        return secondCategoryInfos;
    }

    public void setSecondCategoryInfos(List<SecondCategoryInfo> secondCategoryInfos) {
        this.secondCategoryInfos = secondCategoryInfos;
    }
}
