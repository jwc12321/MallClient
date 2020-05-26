package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author jwc on 2020/5/13.
 * 描述：
 */
public class HomePageInfo {
    //城市是否开通 1 开通 0：未开通
    @SerializedName("status")
    private String status;
    //首页banner
    @SerializedName("banner")
    private List<BannerInfo> bannerInfos;
    //首页商品列表
    @SerializedName("hotGoodsList")
    private List<GoodsItemInfo> goodsItemInfos;
    //未读消息
    @SerializedName("unreadMsgCount")
    private String unreadMsgCount;
    @SerializedName("jingang")
    private List<JinGangInfo> jinGangInfos;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<BannerInfo> getBannerInfos() {
        return bannerInfos;
    }

    public void setBannerInfos(List<BannerInfo> bannerInfos) {
        this.bannerInfos = bannerInfos;
    }

    public List<GoodsItemInfo> getGoodsItemInfos() {
        return goodsItemInfos;
    }

    public void setGoodsItemInfos(List<GoodsItemInfo> goodsItemInfos) {
        this.goodsItemInfos = goodsItemInfos;
    }

    public String getUnreadMsgCount() {
        return unreadMsgCount;
    }

    public void setUnreadMsgCount(String unreadMsgCount) {
        this.unreadMsgCount = unreadMsgCount;
    }

    public List<JinGangInfo> getJinGangInfos() {
        return jinGangInfos;
    }

    public void setJinGangInfos(List<JinGangInfo> jinGangInfos) {
        this.jinGangInfos = jinGangInfos;
    }
}
