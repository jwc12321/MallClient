package com.mall.sls;




import com.mall.sls.data.remote.RestApiModule;
import com.mall.sls.data.remote.RestApiService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 2017/12/27.
 */
@Singleton
@Component(modules = {ApplicationModule.class, RestApiModule.class})
public interface ApplicationComponent {
    void inject(BaseActivity activity);
    RestApiService getRestApiService();
}
