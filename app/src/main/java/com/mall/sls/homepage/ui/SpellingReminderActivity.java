package com.mall.sls.homepage.ui;

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
import com.mall.sls.common.widget.textview.MediumThickTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/19.
 * 描述：拼单提示
 */
public class SpellingReminderActivity extends BaseActivity {
    @BindView(R.id.close_iv)
    ImageView closeIv;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.content)
    ConventionalTextView content;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;

    private String mobile;
    private String surplus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelling_reminder);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        mobile=getIntent().getStringExtra(StaticData.MOBILE);
        surplus=getIntent().getStringExtra(StaticData.SURPLUS);
        title.setText("参与"+mobile+"的拼单");
        content.setText("仅剩"+surplus+"个名额");
    }

    @OnClick({R.id.close_iv,R.id.confirm_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_iv:
                finish();
                break;
            case R.id.confirm_bt:
                Intent backIntent = new Intent();
                setResult(Activity.RESULT_OK, backIntent);
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
