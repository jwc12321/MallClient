package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author jwc on 2020/5/18.
 * 描述：
 */
public class MineInfo {
    @SerializedName("userInfo")
    private UserInfo userInfo;
    @SerializedName("list")
    private List<MineRewardInfo> mineRewardInfos;
    //超级会员描述
    @SerializedName("vipDescription")
    private String vipDescription;
    //超级过期日期
    @SerializedName("vipExpireDate")
    private String vipExpireDate;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<MineRewardInfo> getMineRewardInfos() {
        return mineRewardInfos;
    }

    public void setMineRewardInfos(List<MineRewardInfo> mineRewardInfos) {
        this.mineRewardInfos = mineRewardInfos;
    }

    public String getVipDescription() {
        return vipDescription;
    }

    public void setVipDescription(String vipDescription) {
        this.vipDescription = vipDescription;
    }

    public String getVipExpireDate() {
        return vipExpireDate;
    }

    public void setVipExpireDate(String vipExpireDate) {
        this.vipExpireDate = vipExpireDate;
    }
}
