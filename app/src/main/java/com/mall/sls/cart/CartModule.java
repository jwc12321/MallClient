package com.mall.sls.cart;

import dagger.Module;
import dagger.Provides;

/**
 * @author jwc on 2020/6/23.
 * 描述：
 */
@Module
public class CartModule {
    private CartContract.CartView cartView;

    public CartModule(CartContract.CartView cartView) {
        this.cartView = cartView;
    }

    @Provides
    CartContract.CartView provideCartView(){
        return cartView;
    }
}
