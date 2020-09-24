package com.mall.sls.certify.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.webview.ui.WebViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/6.
 */
public class StartVerifyActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;
    @BindView(R.id.face_protocol)
    ConventionalTextView faceProtocol;

    public static void start(Context context) {
        Intent intent = new Intent(context, StartVerifyActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_verify);
        ButterKnife.bind(this);
        setHeight(back, title, null);
    }

    @OnClick({R.id.confirm_bt, R.id.back, R.id.face_protocol})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_bt:
                CerifyResultActivity.start(this);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.face_protocol://人脸识别认证协议
                WebViewActivity.start(this, StaticData.OCR_AGREEMENT);
                break;
            default:
        }
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
