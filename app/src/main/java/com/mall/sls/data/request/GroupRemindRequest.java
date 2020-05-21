package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/5/21.
 * 描述：
 */
public class GroupRemindRequest {
    //团购规则ID
    @SerializedName("ruleId")
    private String ruleId;
    //1,提醒我 2,取消提醒
    @SerializedName("status")
    private String status;

    public GroupRemindRequest(String ruleId, String status) {
        this.ruleId = ruleId;
        this.status = status;
    }
}
