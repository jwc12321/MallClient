package com.mall.sls.address;

/**
 * @author jwc on 2020/5/13.
 * 描述：
 */

import com.mall.sls.ActivityScope;
import com.mall.sls.ApplicationComponent;
import com.mall.sls.address.ui.AddAddressActivity;
import com.mall.sls.address.ui.AddressManageActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {AddressModule.class})
public interface AddressComponent {
    void inject(AddressManageActivity addressManageActivity);
    void inject(AddAddressActivity addAddressActivity);
}
