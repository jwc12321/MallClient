package com.mall.sls.homepage.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/19.
 * 描述：
 */
public class CityNotOpenActivity extends BaseActivity {
    @BindView(R.id.item_ll)
    LinearLayout itemLl;
    @BindView(R.id.all_rl)
    RelativeLayout allRl;

    public static void start(Context context) {
        Intent intent = new Intent(context, CityNotOpenActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_not_open);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.item_ll,R.id.all_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_ll:
                break;
            case R.id.all_rl:
                finish();
                break;
            default:
        }
    }



    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
