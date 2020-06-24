package com.mall.sls.cart;

/**
 * @author jwc on 2020/6/23.
 * 描述：
 */

import com.mall.sls.ActivityScope;
import com.mall.sls.ApplicationComponent;
import com.mall.sls.cart.ui.CartFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {CartModule.class})
public interface CartComponent {
    void inject(CartFragment cartFragment);
}
