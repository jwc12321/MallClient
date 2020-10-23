package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/10/23.
 * 描述：
 */
public class IntegralPointsInfo {
    //总积分
    @SerializedName("totalPoints")
    private String totalPoints;
    //剩余积分
    @SerializedName("lastPoints")
    private String lastPoints;

    public String getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(String totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getLastPoints() {
        return lastPoints;
    }

    public void setLastPoints(String lastPoints) {
        this.lastPoints = lastPoints;
    }
}
