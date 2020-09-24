package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author jwc on 2020/9/11.
 * 描述：
 */
public class BaoFuPay implements Serializable {
    @SerializedName("payParam")
    private BaoFuPayInfo baoFuPayInfo;

    public BaoFuPayInfo getBaoFuPayInfo() {
        return baoFuPayInfo;
    }

    public void setBaoFuPayInfo(BaoFuPayInfo baoFuPayInfo) {
        this.baoFuPayInfo = baoFuPayInfo;
    }
}
