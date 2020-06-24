package com.mall.sls.cart;

import com.mall.sls.BasePresenter;
import com.mall.sls.BaseView;
import com.mall.sls.data.entity.CartInfo;

/**
 * @author jwc on 2020/6/23.
 * 描述：
 */
public interface CartContract {
    interface CartPresenter extends BasePresenter{
        void getCartInfo(String refreshType);
        void deleteCartItem(String id);
        void cartUpdateNumber(String id, String number);
    }

    interface CartView extends BaseView<CartPresenter>{
        void renderCartInfo(CartInfo cartInfo);
        void renderDeleteCartItem(Boolean isBoolean);
        void renderCartUpdateNumber();
    }
}
