package com.mall.sls.address;

import dagger.Module;
import dagger.Provides;

/**
 * @author jwc on 2020/5/7.
 * 描述：
 */
@Module
public class AddressModule {
    private AddressContract.AddressManageView addressManageView;
    private AddressContract.AddAddressView addAddressView;

    public AddressModule(AddressContract.AddressManageView addressManageView) {
        this.addressManageView = addressManageView;
    }

    public AddressModule(AddressContract.AddAddressView addAddressView) {
        this.addAddressView = addAddressView;
    }

    @Provides
    AddressContract.AddressManageView provideAddressManageView(){
        return addressManageView;
    }

    @Provides
    AddressContract.AddAddressView provideAddAddressView(){
        return addAddressView;
    }
}
