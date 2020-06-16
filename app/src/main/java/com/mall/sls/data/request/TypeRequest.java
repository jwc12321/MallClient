package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/6/16.
 * 描述：
 */
public class TypeRequest {
    //如果是1，则是注册赠券；如果是2，则是优惠券码兑换 如果是3 则是超级会员赠券；4-认证优惠券
    @SerializedName("type")
    private String type;

    public TypeRequest(String type) {
        this.type = type;
    }
}
