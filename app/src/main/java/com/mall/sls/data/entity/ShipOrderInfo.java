package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author jwc on 2020/6/8.
 * 描述：
 */
public class ShipOrderInfo implements Serializable {
    //物流状态
    @SerializedName("statusDesc")
    private String statusDesc;
    //物流时间
    @SerializedName("statusTime")
    private String statusTime;
    //物流详情
    @SerializedName("remark")
    private String  remark;

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(String statusTime) {
        this.statusTime = statusTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
