package com.mall.sls.data.entity;

import java.util.List;

/**
 * @author jwc on 2020/5/7.
 * 描述：
 */
public class AssembleInfo {
    private String imageUrl;
    private String name;
    private List<AssembleItem> assembleItems;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AssembleItem> getAssembleItems() {
        return assembleItems;
    }

    public void setAssembleItems(List<AssembleItem> assembleItems) {
        this.assembleItems = assembleItems;
    }
}
