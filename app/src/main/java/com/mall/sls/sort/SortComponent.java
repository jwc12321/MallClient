package com.mall.sls.sort;

/**
 * @author jwc on 2020/9/21.
 * 描述：
 */

import com.mall.sls.ActivityScope;
import com.mall.sls.ApplicationComponent;
import com.mall.sls.sort.ui.CategoryGoodsActivity;
import com.mall.sls.sort.ui.SearchGoodsActivity;
import com.mall.sls.sort.ui.SortFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {SortModule.class})
public interface SortComponent {
    void inject(SortFragment fragment);
    void inject(CategoryGoodsActivity activity);
    void inject(SearchGoodsActivity activity);
}
