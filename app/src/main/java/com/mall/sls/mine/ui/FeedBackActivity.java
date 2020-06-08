package com.mall.sls.mine.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.widget.edittextview.ForbidEmojiEditText;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.mine.DaggerMineComponent;
import com.mall.sls.mine.MineContract;
import com.mall.sls.mine.MineModule;
import com.mall.sls.mine.presenter.FeedBackPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author jwc on 2020/5/14.
 * 描述：请填写备注
 */
public class FeedBackActivity extends BaseActivity implements MineContract.FeedBackView {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.remark_et)
    EditText remarkEt;
    @BindView(R.id.word_count)
    ConventionalTextView wordCount;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;

    private String remark;

    @Inject
    FeedBackPresenter feedBackPresenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, FeedBackActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        setHeight(back, title, null);
    }


    @OnTextChanged({R.id.remark_et})
    public void checkRemarkEnable() {
        remark = remarkEt.getText().toString().trim();
        wordCount.setText(remark.length() + "/200");
        confirmBt.setEnabled(!TextUtils.isEmpty(remark));
    }
    @Override
    protected void initializeInjector() {
        DaggerMineComponent.builder()
                .applicationComponent(getApplicationComponent())
                .mineModule(new MineModule(this))
                .build()
                .inject(this);
    }



    @OnClick({R.id.back, R.id.confirm_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.confirm_bt:
                confirm();
                break;
            default:
        }
    }

    private void confirm() {
        if (TextUtils.isEmpty(remark)) {
            showMessage(getString(R.string.input_feedback));
            return;
        }
        feedBackPresenter.addFeedBack(remark);
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void renderAddFeedBack(Boolean isBoolean) {
        if(isBoolean) {
            showMessage(getString(R.string.feed_back_success));
            finish();
        }
    }

    @Override
    public void setPresenter(MineContract.FeedBackPresenter presenter) {

    }
}
