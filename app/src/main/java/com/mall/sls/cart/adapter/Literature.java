package com.mall.sls.cart.adapter;

/**
 * @author jwc on 2020/6/28.
 * 描述：
 */
public interface Literature {
    int TYPE_NORMAL = 101;
    int TYPE_EMPTY = 102;
    int TYPE_CANCEL = 103;

    int getType();
}
