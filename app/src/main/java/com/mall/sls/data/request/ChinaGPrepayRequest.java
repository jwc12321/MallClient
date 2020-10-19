package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/10/19.
 * 描述：
 */
public class ChinaGPrepayRequest {
    //卡号
    @SerializedName("accNo")
    private String accNo;
    //有效期
    @SerializedName("expired")
    private String expired;
    //安全码
    @SerializedName("cvv2")
    private String cvv2;
    //名字
    @SerializedName("name")
    private String name;
    //身份证号
    @SerializedName("idCard")
    private String idCard;
    //手机号
    @SerializedName("mobile")
    private String mobile;
    //支付id
    @SerializedName("payId")
    private String payId;

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }
}
