package com.mall.sls;

/**
 * Created by Administrator on 2017/12/15.
 */

public interface LoadDataView {
    void showMessage(String msg);

    void showError(Throwable e);

    void showLoading(String tips);

    void dismissLoading();
}
