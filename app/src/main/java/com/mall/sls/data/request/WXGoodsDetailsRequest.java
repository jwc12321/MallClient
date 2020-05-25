package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/5/25.
 * 描述：
 */
public class WXGoodsDetailsRequest {
    //产品ID
    @SerializedName("goodsProductId")
    private String goodsProductId;
    //团购ID
    @SerializedName("grouponId")
    private String grouponId;

    public WXGoodsDetailsRequest(String goodsProductId, String grouponId) {
        this.goodsProductId = goodsProductId;
        this.grouponId = grouponId;
    }
}
