package com.mall.sls.homepage.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.WXShareManager;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.mine.ui.SelectShareTypeActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/19.
 * 描述：
 */
public class ManyGroupActivity extends BaseActivity {
    @BindView(R.id.close_iv)
    ImageView closeIv;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.weixin_iv)
    ImageView weixinIv;
    private WXShareManager wxShareManager;
    private String goodsId;
    private String wxUrl;
    private String inviteCode;
    private String nameText;
    private String briefText;
    private String backType;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_many_group);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        EventBus.getDefault().register(this);
        wxShareManager = WXShareManager.getInstance(this);
        goodsId = getIntent().getStringExtra(StaticData.GROUPON_ID);
        wxUrl=getIntent().getStringExtra(StaticData.WX_URL);
        inviteCode=getIntent().getStringExtra(StaticData.INVITE_CODE);
        nameText = getIntent().getStringExtra(StaticData.GOODS_NAME);
        briefText = getIntent().getStringExtra(StaticData.GOODS_BRIEF);
    }


    //分享成功
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShareSuccess(String code) {
        Intent backIntent = new Intent();
        backIntent.putExtra(StaticData.BACK_TYPE, StaticData.REFLASH_ONE);
        setResult(Activity.RESULT_OK, backIntent);
        finish();

    }

    @OnClick({R.id.close_iv, R.id.weixin_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_iv:
                Intent backIntent = new Intent();
                backIntent.putExtra(StaticData.BACK_TYPE, StaticData.REFLASH_ZERO);
                setResult(Activity.RESULT_OK, backIntent);
                finish();
                break;
            case R.id.weixin_iv:
                Intent intent = new Intent(this, SelectShareTypeActivity.class);
                startActivityForResult(intent, RequestCodeStatic.SELECT_SHARE_TYPE);
                break;
            default:
        }
    }

    private void shareWx(boolean isFriend) {
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.app_icon);
        String url = wxUrl + "activity/" + goodsId + "?inviteCode=" + inviteCode;
        wxShareManager.shareUrlToWX(isFriend, url, bitmap, nameText, briefText);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeStatic.SELECT_SHARE_TYPE:
                    if (data != null) {
                        backType = data.getStringExtra(StaticData.BACK_TYPE);
                        shareWx(TextUtils.equals(StaticData.REFLASH_ONE, backType));
                    }
                    break;
                default:
            }
        }
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
