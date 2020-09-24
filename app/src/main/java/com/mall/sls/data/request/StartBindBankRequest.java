package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/9/10.
 * 描述：
 */
public class StartBindBankRequest {
    //银行码
    @SerializedName("bankCode")
    private String bankCode;
    //银行名称
    @SerializedName("bankName")
    private String bankName;
    //卡号
    @SerializedName("cardNo")
    private String cardNo;
    //是否是信用卡
    @SerializedName("creditCard")
    private Boolean creditCard;
    //有效期
    @SerializedName("expireDate")
    private String expireDate;
    //安全码
    @SerializedName("safeCode")
    private String safeCode;
    //名字
    @SerializedName("name")
    private String name;
    //身份证号
    @SerializedName("idCard")
    private String idCard;
    //手机号
    @SerializedName("mobile")
    private String mobile;

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Boolean getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(Boolean creditCard) {
        this.creditCard = creditCard;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getSafeCode() {
        return safeCode;
    }

    public void setSafeCode(String safeCode) {
        this.safeCode = safeCode;
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
}
