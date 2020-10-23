package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/5/4.
 */

public class UploadFileRequest {
    @SerializedName("file")
    private String file;
    @SerializedName("type")
    private String type;

    public UploadFileRequest(String file, String type) {
        this.file = file;
        this.type = type;
    }
}
