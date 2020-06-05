package com.mall.sls.data.event;

/**
 * Created by JWC on 2017/5/4.
 */

public class WXLoginEvent {
    private String code;

    public WXLoginEvent(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
