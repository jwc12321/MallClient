package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * @author jwc on 2020/10/26.
 * 描述：
 */
public class PointsRecord {
    @SerializedName("records")
    private List<PointsRecordInfo> pointsRecordInfos;

    public List<PointsRecordInfo> getPointsRecordInfos() {
        return pointsRecordInfos;
    }

    public void setPointsRecordInfos(List<PointsRecordInfo> pointsRecordInfos) {
        this.pointsRecordInfos = pointsRecordInfos;
    }
}
