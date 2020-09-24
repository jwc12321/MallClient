package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/9/10.
 * 描述：
 */
public class CardInfo {
    //工商银行
    @SerializedName("bankDescription")
    private String bankDescription;
    //牡丹卡普卡
    @SerializedName("cardDescription")
    private String cardDescription;
    //是否是信用卡
    @SerializedName("creditCard")
    private Boolean creditCard;
    //卡类型
    @SerializedName("cardType")
    private String cardType;
    //银行码
    @SerializedName("bankId")
    private String bankId;
    //是否可用的银行卡
    @SerializedName("canUsed")
    private Boolean canUsed;

    public String getBankDescription() {
        return bankDescription;
    }

    public void setBankDescription(String bankDescription) {
        this.bankDescription = bankDescription;
    }

    public String getCardDescription() {
        return cardDescription;
    }

    public void setCardDescription(String cardDescription) {
        this.cardDescription = cardDescription;
    }

    public Boolean getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(Boolean creditCard) {
        this.creditCard = creditCard;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public Boolean getCanUsed() {
        return canUsed;
    }

    public void setCanUsed(Boolean canUsed) {
        this.canUsed = canUsed;
    }
}
