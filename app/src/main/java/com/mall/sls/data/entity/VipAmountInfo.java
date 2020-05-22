package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/5/22.
 * 描述：
 */
public class VipAmountInfo {
    @SerializedName("certify")
    private String certify;
    @SerializedName("spvip")
    private String spvip;

    public String getCertify() {
        return certify;
    }

    public void setCertify(String certify) {
        this.certify = certify;
    }

    public String getSpvip() {
        return spvip;
    }

    public void setSpvip(String spvip) {
        this.spvip = spvip;
    }
}
