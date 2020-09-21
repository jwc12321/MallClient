package com.mall.sls.sort;

import dagger.Module;
import dagger.Provides;

/**
 * @author jwc on 2020/9/21.
 * 描述：
 */
@Module
public class SortModule {
    private SortContract.SortView sortView;
    private SortContract.CategoryGoodsView categoryGoodsView;
    private SortContract.SearchGoodsView searchGoodsView;

    public SortModule(SortContract.SortView sortView) {
        this.sortView = sortView;
    }

    public SortModule(SortContract.CategoryGoodsView categoryGoodsView) {
        this.categoryGoodsView = categoryGoodsView;
    }

    public SortModule(SortContract.SearchGoodsView searchGoodsView) {
        this.searchGoodsView = searchGoodsView;
    }

    @Provides
    SortContract.SortView provideSortView(){
        return sortView;
    }

    @Provides
    SortContract.CategoryGoodsView provideCategoryGoodsView(){
        return categoryGoodsView;
    }

    @Provides
    SortContract.SearchGoodsView provideSearchGoodsView(){
        return searchGoodsView;
    }
}
