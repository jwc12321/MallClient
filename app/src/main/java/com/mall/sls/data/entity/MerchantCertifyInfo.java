package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/10/23.
 * 描述：
 */
public class MerchantCertifyInfo {
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

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getDoorHeader() {
        return doorHeader;
    }

    public void setDoorHeader(String doorHeader) {
        this.doorHeader = doorHeader;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
