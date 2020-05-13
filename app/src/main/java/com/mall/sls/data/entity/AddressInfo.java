package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author jwc on 2020/5/8.
 * 描述：
 */
public class AddressInfo implements Serializable {
    //姓名
    @SerializedName("name")
    private String name;
    //电话
    @SerializedName("tel")
    private String tel;
    //性别 0：女 1：男
    @SerializedName("gender")
    private String gender;
    //省
    @SerializedName("province")
    private String province;
    //市
    @SerializedName("city")
    private String city;
    //区
    @SerializedName("county")
    private String county;
    //详细地址
    @SerializedName("addressDetail")
    private String addressDetail;
    //区code
    @SerializedName("areaCode")
    private String areaCode;
    //是否默认
    @SerializedName("isDefault")
    private Boolean isDefault;
    //学校 家  公司 其他
    @SerializedName("type")
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

