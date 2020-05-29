package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/5/29.
 * 描述：
 */
public class DescriptionRequest {
    @SerializedName("description")
    private String description;

    public DescriptionRequest(String description) {
        this.description = description;
    }
}
