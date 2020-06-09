package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author jwc on 2020/6/9.
 * 描述：
 */
public class LotteryRecord {
    @SerializedName("list")
    private List<PrizeVo> prizeVos;

    public List<PrizeVo> getPrizeVos() {
        return prizeVos;
    }

    public void setPrizeVos(List<PrizeVo> prizeVos) {
        this.prizeVos = prizeVos;
    }
}
