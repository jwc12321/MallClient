package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author jwc on 2020/5/14.
 * 描述：
 */
public class GroupPeople implements Serializable {
    //时间
    @SerializedName("addTime")
    private String addTime;
    //昵称
    @SerializedName("nickname")
    private String nickname;

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
