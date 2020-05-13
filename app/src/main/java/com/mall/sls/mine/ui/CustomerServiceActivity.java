package com.mall.sls.mine.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.order.ui.DeliveryinfoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/13.
 * 描述：客服
 */
public class CustomerServiceActivity extends BaseActivity {
    @BindView(R.id.close_iv)
    ImageView closeIv;
    @BindView(R.id.confirm_bt)
    ConventionalTextView confirmBt;
    private ClipboardManager myClipboard;
    private ClipData myClip;


    public static void start(Context context) {
        Intent intent = new Intent(context, CustomerServiceActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cusiomer_service);
        ButterKnife.bind(this);
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.close_iv,R.id.confirm_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_iv://
                finish();
                break;
            case R.id.confirm_bt:
                myClip = ClipData.newPlainText("text", "11111");
                myClipboard.setPrimaryClip(myClip);
                showMessage(getString(R.string.copy_successfully));
                break;
            default:
        }
    }


}
