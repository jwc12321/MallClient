package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/10/19.
 * 描述：
 */
public class ChinaGSendCodeRequest {
    //爱农tn
    @SerializedName("tn")
    private String tn;

    public ChinaGSendCodeRequest(String tn) {
        this.tn = tn;
    }
}
