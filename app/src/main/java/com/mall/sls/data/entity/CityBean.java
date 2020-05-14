package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author jwc on 2020/5/14.
 * 描述：
 */
public class CityBean {
    @SerializedName("code")
    private String code;
    @SerializedName("name")
    private String name;
    @SerializedName("children")
    private List<AreaBean> areaBeans;
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AreaBean> getAreaBeans() {
        return areaBeans;
    }

    public void setAreaBeans(List<AreaBean> areaBeans) {
        this.areaBeans = areaBeans;
    }
}
