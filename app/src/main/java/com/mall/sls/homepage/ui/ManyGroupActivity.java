package com.mall.sls.homepage.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;

/**
 * @author jwc on 2020/5/19.
 * 描述：
 */
public class ManyGroupActivity extends BaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_many_group);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
