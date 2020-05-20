package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author jwc on 2020/5/20.
 * 描述：
 */
public class TeamInfo {
    @SerializedName("list")
    private List<MyTeamInfo> teamInfos;

    public List<MyTeamInfo> getTeamInfos() {
        return teamInfos;
    }

    public void setTeamInfos(List<MyTeamInfo> teamInfos) {
        this.teamInfos = teamInfos;
    }
}
