package com.mall.sls.data.entity;

import java.util.List;

public class ProductSku {
    private String name;
    private List<ProductSkuInfo> productSkuInfos;

    public ProductSku(String name, List<ProductSkuInfo> productSkuInfos) {
        this.name = name;
        this.productSkuInfos = productSkuInfos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductSkuInfo> getProductSkuInfos() {
        return productSkuInfos;
    }

    public void setProductSkuInfos(List<ProductSkuInfo> productSkuInfos) {
        this.productSkuInfos = productSkuInfos;
    }
}
