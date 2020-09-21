package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author jwc on 2020/9/21.
 * 描述：
 */
public class FirstCategory {
    @SerializedName("list")
    private List<FirstCategoryInfo> firstCategoryInfos;

    public List<FirstCategoryInfo> getFirstCategoryInfos() {
        return firstCategoryInfos;
    }

    public void setFirstCategoryInfos(List<FirstCategoryInfo> firstCategoryInfos) {
        this.firstCategoryInfos = firstCategoryInfos;
    }
}
