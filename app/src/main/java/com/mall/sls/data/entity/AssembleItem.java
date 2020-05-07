package com.mall.sls.data.entity;

import java.util.List;

/**
 * @author jwc on 2020/5/7.
 * 描述：
 */
public class AssembleItem {
    private String imageUrl;
    private String name;

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

    public AssembleItem(String imageUrl, String name) {
        this.imageUrl = imageUrl;
        this.name = name;
    }
}
