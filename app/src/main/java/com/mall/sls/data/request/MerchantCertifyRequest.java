package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/10/23.
 * 描述：
 */
public class MerchantCertifyRequest {
    //营业执照
    @SerializedName("businessLicense")
    private String businessLicense;
    //门头
    @SerializedName("doorHeader")
    private String doorHeader;
    //省市区
    @SerializedName("address")
    private String address;
    //详细地址
    @SerializedName("detail")
    private String detail;

    public MerchantCertifyRequest(String businessLicense, String doorHeader, String address, String detail) {
        this.businessLicense = businessLicense;
        this.doorHeader = doorHeader;
        this.address = address;
        this.detail = detail;
    }
}
