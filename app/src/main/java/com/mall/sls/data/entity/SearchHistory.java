package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author jwc on 2020/9/21.
 * 描述：
 */
public class SearchHistory {
    @SerializedName("list")
    private List<String> historyRecords;

    public List<String> getHistoryRecords() {
        return historyRecords;
    }

    public void setHistoryRecords(List<String> historyRecords) {
        this.historyRecords = historyRecords;
    }
}
