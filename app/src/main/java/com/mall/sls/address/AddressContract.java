package com.mall.sls.address;

import com.mall.sls.BasePresenter;
import com.mall.sls.BaseView;
import com.mall.sls.common.unit.VerifyManager;
import com.mall.sls.data.entity.AddressInfo;
import com.mall.sls.data.entity.ProvinceBean;
import com.mall.sls.data.request.AddAddressRequest;

import java.util.List;

/**
 * @author jwc on 2020/5/13.
 * 描述：
 */
public interface AddressContract {
    interface AddressManagePresenter extends BasePresenter{
        void getAddressInfo();
    }

    interface AddressManageView extends BaseView<AddressManagePresenter>{
        void renderAddressInfo(List<AddressInfo> addressInfos);
    }

    interface AddAddressPresenter extends BasePresenter{
        void addAddress(AddAddressRequest addAddressRequest);
        void getAreas();
        void deleteAddress(String id);
    }

    interface AddAddressView extends BaseView<AddAddressPresenter>{
        void renderAddAddress();
        void renderAresa(List<ProvinceBean> provinceBeans);
        void renderDeleteAddress();
    }
}
