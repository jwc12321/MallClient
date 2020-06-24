package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author jwc on 2020/6/24.
 * 描述：
 */
public class GeneralGoodsDetailsInfo {
    //购物车有效商品数量
    @SerializedName("cartCount")
    private String cartCount;
    //普通商品对象
    @SerializedName("goodsBaseVo")
    private GoodsBaseVo goodsBaseVo;
    //规格列表
    @SerializedName("specificationList")
    private List<GoodsSpec> goodsSpecs;
    //规格价格列表
    @SerializedName("productList")
    private List<ProductListCallableInfo> productListCallableInfos;

    public String getCartCount() {
        return cartCount;
    }

    public void setCartCount(String cartCount) {
        this.cartCount = cartCount;
    }

    public GoodsBaseVo getGoodsBaseVo() {
        return goodsBaseVo;
    }

    public void setGoodsBaseVo(GoodsBaseVo goodsBaseVo) {
        this.goodsBaseVo = goodsBaseVo;
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
}
