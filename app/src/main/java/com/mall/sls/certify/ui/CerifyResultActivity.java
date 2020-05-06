package com.mall.sls.certify.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.unit.StaticHandler;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/6.
 * 验证结果
 */
public class CerifyResultActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.result_tv)
    ConventionalTextView resultTv;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;
    private Handler mHandler = new MyHandler(this);

    private static final int GO_MAIN = 1;
    private int time=3;
    private String countdown;

    public static void start(Context context) {
        Intent intent = new Intent(context, CerifyResultActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerify_result);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();

    }

    private void initView(){
        countdown="认证成功，"+time+"秒后自动返回首页";
        resultTv.setText(countdown);
        mHandler.sendEmptyMessageDelayed(GO_MAIN, 1000);
    }

    @OnClick({R.id.confirm_bt, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_bt:
                break;
            case R.id.back:
                finish();
                break;
            default:
        }
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }
    public static class MyHandler extends StaticHandler<CerifyResultActivity> {

        public MyHandler(CerifyResultActivity target) {
            super(target);
        }

        @Override
        public void handle(CerifyResultActivity target, Message msg) {
            switch (msg.what) {
                case GO_MAIN:
                    target.goMain();
                    break;
            }
        }
    }

    //跳转到主页
    private void goMain() {
        time--;
        if(time>0){
            countdown="认证成功，"+time+"秒后自动返回首页";
            resultTv.setText(countdown);
            mHandler.sendEmptyMessageDelayed(GO_MAIN, 1000);
        }else {
            finish();
        }
    }
}
