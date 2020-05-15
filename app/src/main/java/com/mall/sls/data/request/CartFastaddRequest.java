package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/5/15.
 * 描述：
 */
public class CartFastaddRequest {
    //商品ID
    @SerializedName("goodsId")
    private String goodsId;
    //SKU ID
    @SerializedName("productId")
    private String productId;
    //是否团购
    @SerializedName("isGroup")
    private boolean isGroup;
    //购买数量
    @SerializedName("number")
    private String number;
    //团购信息ID 发起拼团及单独购买不传
    @SerializedName("groupId")
    private String groupId;
    //团购规则ID
    @SerializedName("groupRulesId")
    private String groupRulesId;

    public CartFastaddRequest(String goodsId, String productId, boolean isGroup, String number, String groupId, String groupRulesId) {
        this.goodsId = goodsId;
        this.productId = productId;
        this.isGroup = isGroup;
        this.number = number;
        this.groupId = groupId;
        this.groupRulesId = groupRulesId;
    }
}
