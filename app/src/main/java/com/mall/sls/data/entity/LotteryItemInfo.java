package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author jwc on 2020/6/9.
 * 描述：
 */
public class LotteryItemInfo {
    //锦鲤列表
    @SerializedName("brocadeCarps")
    private List<String> brocadeCarps;
    //历史抽奖记录数数量
    @SerializedName("historyPrizeCount")
    private String historyPrizeCount;
    //系统时间
    @SerializedName("now")
    private String now;
    //抽奖机会次数
    @SerializedName("prizeNumber")
    private String prizeNumber;
    //抽奖列表
    @SerializedName("prizeVos")
    private List<PrizeVo> prizeVos ;

    public List<String> getBrocadeCarps() {
        return brocadeCarps;
    }

    public void setBrocadeCarps(List<String> brocadeCarps) {
        this.brocadeCarps = brocadeCarps;
    }

    public String getHistoryPrizeCount() {
        return historyPrizeCount;
    }

    public void setHistoryPrizeCount(String historyPrizeCount) {
        this.historyPrizeCount = historyPrizeCount;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public String getPrizeNumber() {
        return prizeNumber;
    }

    public void setPrizeNumber(String prizeNumber) {
        this.prizeNumber = prizeNumber;
    }

    public List<PrizeVo> getPrizeVos() {
        return prizeVos;
    }

    public void setPrizeVos(List<PrizeVo> prizeVos) {
        this.prizeVos = prizeVos;
    }
}
