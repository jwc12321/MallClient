package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/6/4.
 * 描述：
 */
public class CodeRequest {
    @SerializedName("code")
    private String code;

    public CodeRequest(String code) {
        this.code = code;
    }
}
