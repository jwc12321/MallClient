package com.mall.sls.mainframe.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.ViewFinder;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.shouzhong.scanner.Callback;
import com.shouzhong.scanner.Result;
import com.shouzhong.scanner.ScannerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/9/8.
 * 描述：
 */
public class ScanActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.scannerView)
    ScannerView scannerView;
    @BindView(R.id.title)
    MediumThickTextView title;

    private Boolean enableIdCard;
    private Boolean enableBankCard;
    private Vibrator vibrator;

    public static void start(Context context, Boolean enableIdCard, Boolean enableBankCard) {
        Intent intent = new Intent(context, ScanActivity.class);
        intent.putExtra(StaticData.ENABLE_ID_CARD, enableIdCard);
        intent.putExtra(StaticData.ENABLE_BANK_CARD, enableBankCard);
        ((Activity) context).startActivityForResult(intent, RequestCodeStatic.SCAN);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);
        setHeight(back,title,null );
        initView();
    }

    private void initView() {
        enableIdCard = getIntent().getBooleanExtra(StaticData.ENABLE_ID_CARD, false);
        enableBankCard = getIntent().getBooleanExtra(StaticData.ENABLE_BANK_CARD, false);
        scannerView.setViewFinder(new ViewFinder(this));
        scannerView.setShouldAdjustFocusArea(true);
        scannerView.setSaveBmp(true);
        scannerView.setEnableBankCard(enableBankCard);
        scannerView.setEnableIdCard(enableIdCard);
        scannerView.setCallback(new Callback() {
            @Override
            public void result(Result result) {
                scannerView.restartPreviewAfterDelay(2000);
                backResult(result);
//                startVibrator();
            }
        });
    }

    private void backResult(Result result) {
        scannerView.onPause();
        Intent intent = new Intent();
        intent.putExtra(StaticData.SCAN_DATA,result.data);
        intent.putExtra(StaticData.SCAN_PATH,result.path);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.onPause();
    }

    @Override
    protected void onDestroy() {
        if (vibrator != null) {
            vibrator.cancel();
            vibrator = null;
        }
        super.onDestroy();
    }

    private void startVibrator() {
        if (vibrator == null)
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(300);
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }


    @OnClick({R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            default:
        }
    }
}
