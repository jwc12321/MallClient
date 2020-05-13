package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/5/13.
 * 描述：
 */
public class AddAddressRequest {
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
    //修改时候的地址id
    @SerializedName("id")
    private String id;

    public AddAddressRequest(String name, String tel, String gender, String province, String city, String county, String addressDetail, String areaCode, Boolean isDefault, String type, String id) {
        this.name = name;
        this.tel = tel;
        this.gender = gender;
        this.province = province;
        this.city = city;
        this.county = county;
        this.addressDetail = addressDetail;
        this.areaCode = areaCode;
        this.isDefault = isDefault;
        this.type = type;
        this.id = id;
    }
}
