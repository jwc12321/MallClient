package com.mall.sls.data.entity;


import com.stx.xhb.androidx.entity.SimpleBannerInfo;

public class CustomViewsInfo extends SimpleBannerInfo {

    private String info;

    public CustomViewsInfo(String info) {
        this.info = info;
    }

    @Override
    public String getXBannerUrl() {
        return info;
    }
}
