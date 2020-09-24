package com.mall.sls.mine.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.ConventionalTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/26.
 * 描述：
 */
public class SelectShareTypeActivity extends BaseActivity {
    @BindView(R.id.wx_friends)
    ImageView wxFriends;
    @BindView(R.id.wx_circle)
    ImageView wxCircle;
    @BindView(R.id.cancel_bt)
    ConventionalTextView cancelBt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_share_type);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.cancel_bt,R.id.wx_friends, R.id.wx_circle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_bt:
                finish();
                break;
            case R.id.wx_friends:
                selectBack(StaticData.REFRESH_ZERO);
                break;
            case R.id.wx_circle:
                selectBack(StaticData.REFRESH_ONE);
                break;
            default:
        }
    }

    private void selectBack(String  type){
        Intent backIntent = new Intent();
        backIntent.putExtra(StaticData.BACK_TYPE, type);
        setResult(Activity.RESULT_OK, backIntent);
        finish();
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
