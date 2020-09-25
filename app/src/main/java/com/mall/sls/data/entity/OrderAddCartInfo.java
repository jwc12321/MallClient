package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author jwc on 2020/9/25.
 * 描述：
 */
public class OrderAddCartInfo {
    //是否显示弹框
    @SerializedName("showData")
    private Boolean showData;
    //总数
    @SerializedName("totalCount")
    private String totalCount;
    //失效商品
    @SerializedName("goods")
    private List<HiddenItemCartInfo> hiddenItemCartInfos;

    public Boolean getShowData() {
        return showData;
    }

    public void setShowData(Boolean showData) {
        this.showData = showData;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public List<HiddenItemCartInfo> getHiddenItemCartInfos() {
        return hiddenItemCartInfos;
    }

    public void setHiddenItemCartInfos(List<HiddenItemCartInfo> hiddenItemCartInfos) {
        this.hiddenItemCartInfos = hiddenItemCartInfos;
    }
}
