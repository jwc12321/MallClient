package com.mall.sls.sort.presenter;

import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.FirstCategory;
import com.mall.sls.data.entity.FirstCategoryInfo;
import com.mall.sls.data.entity.SecondCategory;
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
public class SortPresenter implements SortContract.SortPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private SortContract.SortView sortView;

    @Inject
    public SortPresenter(RestApiService restApiService, SortContract.SortView sortView) {
        this.restApiService = restApiService;
        this.sortView = sortView;
    }

    @Inject
    public void setupListener() {
        sortView.setPresenter(this);
    }

    @Override
    public void getFirstCategory() {
        sortView.dismissLoading();
        sortView.showLoading(StaticData.LOADING);
        String queryString = "null";
        String sign = SignUnit.signGet(RequestUrl.FIRST_CATEGORY, queryString);
        Disposable disposable = restApiService.getFirstCategory(sign)
                .flatMap(new RxRemoteDataParse<FirstCategory>())
                .compose(new RxSchedulerTransformer<FirstCategory>())
                .subscribe(new Consumer<FirstCategory>() {
                    @Override
                    public void accept(FirstCategory firstCategory) throws Exception {
                        sortView.dismissLoading();
                        sortView.renderFirstCategory(firstCategory);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        sortView.dismissLoading();
                        sortView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getSecondCategory(String categoryId) {
        sortView.dismissLoading();
        sortView.showLoading(StaticData.LOADING);
        String queryString = "null";
        String sign = SignUnit.signGet(RequestUrl.SECOND_CATEGORY+categoryId+RequestUrl.GOODS, queryString);
        Disposable disposable = restApiService.getSecondCategory(sign,categoryId)
                .flatMap(new RxRemoteDataParse<SecondCategory>())
                .compose(new RxSchedulerTransformer<SecondCategory>())
                .subscribe(new Consumer<SecondCategory>() {
                    @Override
                    public void accept(SecondCategory secondCategory) throws Exception {
                        sortView.dismissLoading();
                        sortView.renderSecondCategory(secondCategory);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        sortView.dismissLoading();
                        sortView.showError(throwable);
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
