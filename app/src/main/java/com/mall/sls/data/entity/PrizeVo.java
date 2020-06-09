package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author jwc on 2020/6/9.
 * 描述：
 */
public class PrizeVo implements Serializable {
    //抽奖截止时间
    @SerializedName("endTime")
    private String endTime;
    //图片Url
    @SerializedName("picUrl")
    private String picUrl;
    //抽奖价格
    @SerializedName("counterPrice")
    private String counterPrice;
    //商品详细介绍，是富文本格式
    @SerializedName("detail")
    private String detail;
    //商品id
    @SerializedName("goodsId")
    private String goodsId ;
    //参与人数
    @SerializedName("participantNumber")
    private String participantNumber;
    //参与人数
    @SerializedName("prizeId")
    private String prizeId;
    //状态 0初始化 1报名中 2待开奖 3已完成
    @SerializedName("prizeStatus")
    private String prizeStatus;
    //开奖时间
    @SerializedName("prizeTime")
    private String prizeTime;
    //抽奖名/标题
    @SerializedName("prizeTitle")
    private String prizeTitle;
    //商品价格
    @SerializedName("goodsPrice")
    private String goodsPrice;
    //商品宣传图片列表
    @SerializedName("gallery")
    private List<String> gallerys;

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getCounterPrice() {
        return counterPrice;
    }

    public void setCounterPrice(String counterPrice) {
        this.counterPrice = counterPrice;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getParticipantNumber() {
        return participantNumber;
    }

    public void setParticipantNumber(String participantNumber) {
        this.participantNumber = participantNumber;
    }

    public String getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(String prizeId) {
        this.prizeId = prizeId;
    }

    public String getPrizeStatus() {
        return prizeStatus;
    }

    public void setPrizeStatus(String prizeStatus) {
        this.prizeStatus = prizeStatus;
    }

    public String getPrizeTime() {
        return prizeTime;
    }

    public void setPrizeTime(String prizeTime) {
        this.prizeTime = prizeTime;
    }

    public String getPrizeTitle() {
        return prizeTitle;
    }

    public void setPrizeTitle(String prizeTitle) {
        this.prizeTitle = prizeTitle;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public List<String> getGallerys() {
        return gallerys;
    }

    public void setGallerys(List<String> gallerys) {
        this.gallerys = gallerys;
    }
}
