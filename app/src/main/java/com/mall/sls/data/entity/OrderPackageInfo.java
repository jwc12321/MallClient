package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author jwc on 2020/6/30.
 * 描述：
 */
public class OrderPackageInfo {
    //订单配送状态
    @SerializedName("status")
    private String status ;
    //订单配送状态文本描述
    @SerializedName("statusDesc")
    private String statusDesc;
    //快递类型
    @SerializedName("shipChannel")
    private String shipChannel ;
    //快递编号
    @SerializedName("shipSn")
    private String shipSn;
    //是否显示查看地图按钮:0-不显示，1-显示
    @SerializedName("showMap")
    private String showMap ;
    //物流h5
    @SerializedName("sfH5Url")
    private String sfH5Url;
    //订单跟踪
    @SerializedName("shipOrderList")
    private List<ShipOrderInfo> shipOrderInfos;
    //商品列表
    @SerializedName("orderGoodsVoList")
    private List<OrderGoodsVo> orderGoodsVos;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getShipChannel() {
        return shipChannel;
    }

    public void setShipChannel(String shipChannel) {
        this.shipChannel = shipChannel;
    }

    public String getShipSn() {
        return shipSn;
    }

    public void setShipSn(String shipSn) {
        this.shipSn = shipSn;
    }

    public String getShowMap() {
        return showMap;
    }

    public void setShowMap(String showMap) {
        this.showMap = showMap;
    }

    public String getSfH5Url() {
        return sfH5Url;
    }

    public void setSfH5Url(String sfH5Url) {
        this.sfH5Url = sfH5Url;
    }

    public List<ShipOrderInfo> getShipOrderInfos() {
        return shipOrderInfos;
    }

    public void setShipOrderInfos(List<ShipOrderInfo> shipOrderInfos) {
        this.shipOrderInfos = shipOrderInfos;
    }

    public List<OrderGoodsVo> getOrderGoodsVos() {
        return orderGoodsVos;
    }

    public void setOrderGoodsVos(List<OrderGoodsVo> orderGoodsVos) {
        this.orderGoodsVos = orderGoodsVos;
    }
}
