package com.mall.sls.sort.presenter;

import android.text.TextUtils;

import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.GoodsItem;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.sort.SortContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/9/21.
 * 描述：
 */
public class CategoryGoodsPresenter implements SortContract.CategoryGoodsPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private SortContract.CategoryGoodsView categoryGoodsView;
    private int currentIndex = 1;  //当前index

    @Inject
    public CategoryGoodsPresenter(RestApiService restApiService, SortContract.CategoryGoodsView categoryGoodsView) {
        this.restApiService = restApiService;
        this.categoryGoodsView = categoryGoodsView;
    }


    @Inject
    public void setupListener() {
        categoryGoodsView.setPresenter(this);
    }

    @Override
    public void getCategoryGoods(String refreshType, String categoryId, String sortType, String orderType) {
        if (TextUtils.equals(StaticData.REFRESH_ONE, refreshType)) {
            categoryGoodsView.showLoading(StaticData.LOADING);
        }
        currentIndex = 1;
        String queryString = "sortType" + sortType + "&orderType" + orderType + "&page=" + currentIndex + "&limit=" + StaticData.TEN_LIST_SIZE;
        String sign = SignUnit.signGet(RequestUrl.SECOND_CATEGORY_ITEM + categoryId + RequestUrl.GOODS, queryString);
        Disposable disposable = restApiService.getCategoryGoods(sign, categoryId, sortType, orderType, String.valueOf(currentIndex), StaticData.TEN_LIST_SIZE)
                .flatMap(new RxRemoteDataParse<GoodsItem>())
                .compose(new RxSchedulerTransformer<GoodsItem>())
                .subscribe(new Consumer<GoodsItem>() {
                    @Override
                    public void accept(GoodsItem goodsItem) throws Exception {
                        categoryGoodsView.dismissLoading();
                        categoryGoodsView.renderCategoryGoods(goodsItem);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        categoryGoodsView.dismissLoading();
                        categoryGoodsView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreCategoryGoods(String categoryId, String sortType, String orderType) {
        currentIndex = currentIndex + 1;
        String queryString = "sortType" + sortType + "&orderType" + orderType + "&page=" + currentIndex + "&limit=" + StaticData.TEN_LIST_SIZE;
        String sign = SignUnit.signGet(RequestUrl.SECOND_CATEGORY_ITEM + categoryId + RequestUrl.GOODS, queryString);
        Disposable disposable = restApiService.getCategoryGoods(sign, categoryId, sortType, orderType, String.valueOf(currentIndex), StaticData.TEN_LIST_SIZE)
                .flatMap(new RxRemoteDataParse<GoodsItem>())
                .compose(new RxSchedulerTransformer<GoodsItem>())
                .subscribe(new Consumer<GoodsItem>() {
                    @Override
                    public void accept(GoodsItem goodsItem) throws Exception {
                        categoryGoodsView.dismissLoading();
                        categoryGoodsView.renderMoreCategoryGoods(goodsItem);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        categoryGoodsView.dismissLoading();
                        categoryGoodsView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        for (Disposable disposable : mDisposableList) {
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }
}
