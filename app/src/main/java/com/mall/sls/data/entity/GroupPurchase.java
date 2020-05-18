package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author jwc on 2020/5/14.
 * 描述：
 */
public class GroupPurchase implements Serializable {
    //团购活动id
    @SerializedName("grouponId")
    private String grouponId;
    //电话
    @SerializedName("mobile")
    private String mobile;
    //剩余人数
    @SerializedName("surplus")
    private String surplus;
    //规则id
    @SerializedName("rulesId")
    private String rulesId;

    public String getGrouponId() {
        return grouponId;
    }

    public void setGrouponId(String grouponId) {
        this.grouponId = grouponId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSurplus() {
        return surplus;
    }

    public void setSurplus(String surplus) {
        this.surplus = surplus;
    }

    public String getRulesId() {
        return rulesId;
    }

    public void setRulesId(String rulesId) {
        this.rulesId = rulesId;
    }
}
