package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/9/15.
 * 描述：
 */
public class CertifyInfo {
    //姓名
    @SerializedName("realName")
    private String realName;
    //身份证号
    @SerializedName("idCard")
    private String idCard;
    //是否已经实名认证
    @SerializedName("certified")
    private Boolean certified;
    //手机号
    @SerializedName("mobile")
    private String mobile;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Boolean getCertified() {
        return certified;
    }

    public void setCertified(Boolean certified) {
        this.certified = certified;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
