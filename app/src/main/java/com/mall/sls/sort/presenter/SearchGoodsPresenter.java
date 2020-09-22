package com.mall.sls.sort.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.common.unit.TokenManager;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.GoodsItem;
import com.mall.sls.data.entity.Ignore;
import com.mall.sls.data.entity.SearchHistory;
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
public class SearchGoodsPresenter implements SortContract.SearchGoodsPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private SortContract.SearchGoodsView searchGoodsView;
    private int currentIndex = 1;  //当前index

    @Inject
    public SearchGoodsPresenter(RestApiService restApiService, SortContract.SearchGoodsView searchGoodsView) {
        this.restApiService = restApiService;
        this.searchGoodsView = searchGoodsView;
    }

    @Inject
    public void setupListener() {
        searchGoodsView.setPresenter(this);
    }

    @Override
    public void getSearchHistory(String refreshType) {
        if(TextUtils.equals(StaticData.REFLASH_ONE,refreshType)) {
            searchGoodsView.showLoading(StaticData.LOADING);
        }
        String queryString="null";
        String sign= SignUnit.signGet(RequestUrl.SEARCH_HISTORY,queryString);
        Disposable disposable = restApiService.getSearchHistory(sign)
                .flatMap(new RxRemoteDataParse<List<String>>())
                .compose(new RxSchedulerTransformer<List<String>>())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> history) throws Exception {
                        searchGoodsView.dismissLoading();
                        searchGoodsView.renderSearchHistory(history);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        searchGoodsView.dismissLoading();
                        searchGoodsView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void deleteHistory() {
        searchGoodsView.showLoading(StaticData.PROCESSING);
        String queryString="null";
        String sign= SignUnit.signDelete(RequestUrl.SEARCH_HISTORY,queryString);
        Disposable disposable = restApiService.deleteHistory(sign)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        searchGoodsView.dismissLoading();
                        searchGoodsView.renderDeleteHistory();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        searchGoodsView.dismissLoading();
                        searchGoodsView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getKeywordGoods(String refreshType, String keyword, String sortType, String orderType) {
        if (TextUtils.equals("1", refreshType)) {
            searchGoodsView.showLoading(StaticData.LOADING);
        }
        currentIndex = 1;
        String queryString = "keyword"+keyword+"&sortType" + sortType + "&orderType" + orderType + "&page=" + currentIndex + "&limit=" + StaticData.TEN_LIST_SIZE;
        String sign = SignUnit.signGet(RequestUrl.SEARCH_GOODS , queryString);
        Disposable disposable = restApiService.getKeywordGoods(sign, keyword, sortType, orderType, String.valueOf(currentIndex), StaticData.TEN_LIST_SIZE)
                .flatMap(new RxRemoteDataParse<GoodsItem>())
                .compose(new RxSchedulerTransformer<GoodsItem>())
                .subscribe(new Consumer<GoodsItem>() {
                    @Override
                    public void accept(GoodsItem goodsItem) throws Exception {
                        searchGoodsView.dismissLoading();
                        searchGoodsView.renderKeywordGoods(goodsItem);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        searchGoodsView.dismissLoading();
                        searchGoodsView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreKeywordGoods(String keyword, String sortType, String orderType) {
        currentIndex = currentIndex+1;
        String queryString = "keyword"+keyword+"&sortType" + sortType + "&orderType" + orderType + "&page=" + currentIndex + "&limit=" + StaticData.TEN_LIST_SIZE;
        String sign = SignUnit.signGet(RequestUrl.SEARCH_GOODS + keyword , queryString);
        Disposable disposable = restApiService.getKeywordGoods(sign, keyword, sortType, orderType, String.valueOf(currentIndex), StaticData.TEN_LIST_SIZE)
                .flatMap(new RxRemoteDataParse<GoodsItem>())
                .compose(new RxSchedulerTransformer<GoodsItem>())
                .subscribe(new Consumer<GoodsItem>() {
                    @Override
                    public void accept(GoodsItem goodsItem) throws Exception {
                        searchGoodsView.dismissLoading();
                        searchGoodsView.renderMoreKeywordGoods(goodsItem);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        searchGoodsView.dismissLoading();
                        searchGoodsView.showError(throwable);
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

    }
}
