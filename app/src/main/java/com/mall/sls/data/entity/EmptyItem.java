package com.mall.sls.data.entity;

import com.mall.sls.cart.adapter.Literature;

/**
 * @author jwc on 2020/6/28.
 * 描述：
 */
public class EmptyItem implements Literature {
    @Override
    public int getType() {
        return Literature.TYPE_EMPTY;
    }
}
