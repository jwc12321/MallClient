package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author jwc on 2020/9/10.
 * 描述：
 */
public class BankCardInfo implements Serializable {
    @SerializedName("id")
    private String id;
    //银行编码 ICBC
    @SerializedName("bankCode")
    private String bankCode;
    //银行卡名字
    @SerializedName("bankName")
    private String bankName;
    //银行卡的类型
    @SerializedName("cardType")
    private String cardType;
    //银行卡号
    @SerializedName("cardNo")
    private String cardNo;
    //姓名
    @SerializedName("name")
    private String name;
    //身份证号
    @SerializedName("idCard")
    private String idCard;
    //手机号
    @SerializedName("mobile")
    private String mobile;
    //银行卡logo
    @SerializedName("icon")
    private String icon;
    //单次支付限额
    @SerializedName("onceLimit")
    private String onceLimit;
    //当日支付限额
    @SerializedName("dayLimit")
    private String dayLimit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getOnceLimit() {
        return onceLimit;
    }

    public void setOnceLimit(String onceLimit) {
        this.onceLimit = onceLimit;
    }

    public String getDayLimit() {
        return dayLimit;
    }

    public void setDayLimit(String dayLimit) {
        this.dayLimit = dayLimit;
    }
}
