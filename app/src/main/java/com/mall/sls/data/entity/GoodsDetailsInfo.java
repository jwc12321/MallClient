package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.PropertyResourceBundle;

import retrofit2.http.GET;

/**
 * @author jwc on 2020/5/13.
 * 描述：
 */
public class GoodsDetailsInfo implements Serializable {
    //产品秒速
    @SerializedName("brief")
    private String brief;
    //商品id
    @SerializedName("goodsId")
    private String goodsId;
    //商品宣传图片列表
    @SerializedName("gallery")
    private List<String> gallerys;
    //商品名
    @SerializedName("name")
    private String name;
    //原价
    @SerializedName("counterPrice")
    private String counterPrice;
    //单位
    @SerializedName("unit")
    private String unit;
    //规格列表
    @SerializedName("specificationList")
    private List<GoodsSpec> goodsSpecs;
    //规格价格列表
    @SerializedName("productList")
    private List<ProductListCallableInfo> productListCallableInfos;
    //总销量
    @SerializedName("salesQuantity")
    private String salesQuantity;
    //多少人在拼单
    @SerializedName("groupNum")
    private String groupNum;
    //该商品团购列表
    @SerializedName("groupPurchaseList")
    private List<GroupPurchase> groupPurchases;
    //图片url
    @SerializedName("picUrl")
    private String picUrl;
    //商品过期使时间
    @SerializedName("groupExpireTime")
    private String groupExpireTime;
    //当前时间
    @SerializedName("now")
    private String now;
    //团购价
    @SerializedName("retailPrice")
    private String retailPrice;
    //团购名
    @SerializedName("groupName")
    private String groupName;
    //100件成团
    @SerializedName("discountMember")
    private String discountMember;
    //已抢数量 ，
    @SerializedName("groupGoodsNum")
    private String groupGoodsNum;
    //520人已抢
    @SerializedName("groupPeopleNum")
    private String groupPeopleNum;
    //拼团的人
    @SerializedName("groupPeopleList")
    private List<GroupPeople> groupPeoples;
    //详情
    @SerializedName("detail")
    private String detail;
    //团购开始时间
    @SerializedName("startTime")
    private String startTime;
    //订阅状态 1,提醒我 2,取消提醒
    @SerializedName("subscriptionStatus")
    private String subscriptionStatus;
    //团购规则id
    @SerializedName("rulesId")
    private String rulesId;

    @SerializedName("hotGoodsList")
    private List<GoodsItemInfo> goodsItemInfos;
    //剩余人数
    @SerializedName("surplus")
    private String surplus;
    //规格
    @SerializedName("specifications")
    private List<String> specifications;
    //拼主
    @SerializedName("memberPhoneList")
    private List<String> memberPhoneList;

    //H5详情数据 团购Id
    @SerializedName("groupId")
    private String groupId;
    //团购规则Id
    @SerializedName("grouponRulesId")
    private String grouponRulesId;
    //发货方式 1同城配送 2快递配送
    @SerializedName("courierType")
    private String courierType;
    //商品类型 1普通商品 2普通团商品 3活动团商品 4抽奖商品
    @SerializedName("goodsType")
    private String goodsType;



    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public List<String> getGallerys() {
        return gallerys;
    }

    public void setGallerys(List<String> gallerys) {
        this.gallerys = gallerys;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCounterPrice() {
        return counterPrice;
    }

    public void setCounterPrice(String counterPrice) {
        this.counterPrice = counterPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<GoodsSpec> getGoodsSpecs() {
        return goodsSpecs;
    }

    public void setGoodsSpecs(List<GoodsSpec> goodsSpecs) {
        this.goodsSpecs = goodsSpecs;
    }

    public List<ProductListCallableInfo> getProductListCallableInfos() {
        return productListCallableInfos;
    }

    public void setProductListCallableInfos(List<ProductListCallableInfo> productListCallableInfos) {
        this.productListCallableInfos = productListCallableInfos;
    }

    public String getSalesQuantity() {
        return salesQuantity;
    }

    public void setSalesQuantity(String salesQuantity) {
        this.salesQuantity = salesQuantity;
    }

    public String getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }

    public List<GroupPurchase> getGroupPurchases() {
        return groupPurchases;
    }

    public void setGroupPurchases(List<GroupPurchase> groupPurchases) {
        this.groupPurchases = groupPurchases;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getGroupExpireTime() {
        return groupExpireTime;
    }

    public void setGroupExpireTime(String groupExpireTime) {
        this.groupExpireTime = groupExpireTime;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDiscountMember() {
        return discountMember;
    }

    public void setDiscountMember(String discountMember) {
        this.discountMember = discountMember;
    }

    public String getGroupGoodsNum() {
        return groupGoodsNum;
    }

    public void setGroupGoodsNum(String groupGoodsNum) {
        this.groupGoodsNum = groupGoodsNum;
    }

    public String getGroupPeopleNum() {
        return groupPeopleNum;
    }

    public void setGroupPeopleNum(String groupPeopleNum) {
        this.groupPeopleNum = groupPeopleNum;
    }

    public List<GroupPeople> getGroupPeoples() {
        return groupPeoples;
    }

    public void setGroupPeoples(List<GroupPeople> groupPeoples) {
        this.groupPeoples = groupPeoples;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public String getRulesId() {
        return rulesId;
    }

    public void setRulesId(String rulesId) {
        this.rulesId = rulesId;
    }

    public List<GoodsItemInfo> getGoodsItemInfos() {
        return goodsItemInfos;
    }

    public void setGoodsItemInfos(List<GoodsItemInfo> goodsItemInfos) {
        this.goodsItemInfos = goodsItemInfos;
    }

    public String getSurplus() {
        return surplus;
    }

    public void setSurplus(String surplus) {
        this.surplus = surplus;
    }

    public List<String> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(List<String> specifications) {
        this.specifications = specifications;
    }

    public List<String> getMemberPhoneList() {
        return memberPhoneList;
    }

    public void setMemberPhoneList(List<String> memberPhoneList) {
        this.memberPhoneList = memberPhoneList;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGrouponRulesId() {
        return grouponRulesId;
    }

    public void setGrouponRulesId(String grouponRulesId) {
        this.grouponRulesId = grouponRulesId;
    }

    public String getCourierType() {
        return courierType;
    }

    public void setCourierType(String courierType) {
        this.courierType = courierType;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }
}
