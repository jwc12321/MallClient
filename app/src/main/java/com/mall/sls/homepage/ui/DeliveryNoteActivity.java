package com.mall.sls.homepage.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/10/14.
 * 描述：配送说明
 */
public class DeliveryNoteActivity extends BaseActivity {
    @BindView(R.id.close_iv)
    ImageView closeIv;

    public static void start(Context context) {
        Intent intent = new Intent(context, DeliveryNoteActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_note);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.close_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_iv:
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
