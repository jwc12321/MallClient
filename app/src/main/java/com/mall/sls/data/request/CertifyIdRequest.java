package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

public class CertifyIdRequest {
    //姓名
    @SerializedName("certName")
    private String certName;
    //身份证号
    @SerializedName("certNo")
    private String certNo;
    //阿里云获取
    @SerializedName("metaInfo")
    private String metaInfo;

    public CertifyIdRequest(String certName, String certNo, String metaInfo) {
        this.certName = certName;
        this.certNo = certNo;
        this.metaInfo = metaInfo;
    }
}
