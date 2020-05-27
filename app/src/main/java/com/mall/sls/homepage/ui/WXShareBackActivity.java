package com.mall.sls.homepage.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.print.PageRange;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.makeramen.roundedimageview.RoundedImageView;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.WXShareManager;
import com.mall.sls.common.widget.textview.MSTearDownView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.mainframe.ui.MainFrameActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/26.
 * 描述：
 */
public class WXShareBackActivity extends BaseActivity {
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.count_down)
    MSTearDownView countDown;
    @BindView(R.id.surplus)
    MediumThickTextView surplus;
    @BindView(R.id.weixin_iv)
    ImageView weixinIv;
    @BindView(R.id.home_iv)
    ImageView homeIv;
    @BindView(R.id.head_photo)
    RoundedImageView headPhoto;
    @BindView(R.id.three_iv)
    ImageView threeIv;

    private String choiceType;
    private String goodsId;
    private String activityUrl;
    private String endTime;
    private WXShareManager wxShareManager;

    public static void start(Context context, String choiceType,String goodsId,String activityUrl,String endTime) {
        Intent intent = new Intent(context, WXShareBackActivity.class);
        intent.putExtra(StaticData.GOODS_ID, goodsId);
        intent.putExtra(StaticData.CHOICE_TYPE,choiceType);
        intent.putExtra(StaticData.ACTIVITY_URL,activityUrl);
        intent.putExtra(StaticData.END_TIME,endTime);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wx_share_back);
        ButterKnife.bind(this);
        setHeight(null,title,null);
        initView();
    }

    private void initView(){
        EventBus.getDefault().register(this);
        wxShareManager = WXShareManager.getInstance(this);
        choiceType=getIntent().getStringExtra(StaticData.CHOICE_TYPE);
        goodsId=getIntent().getStringExtra(StaticData.GOODS_ID);
        activityUrl=getIntent().getStringExtra(StaticData.ACTIVITY_URL);
        endTime=getIntent().getStringExtra(StaticData.END_TIME);
    }


    @OnClick({R.id.home_iv, R.id.weixin_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_iv:
                MainFrameActivity.start(this);
                finish();
                break;
            case R.id.weixin_iv:
                shareWx();
                break;
            default:
        }
    }

    private void shareWx(){
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.app_icon);
        wxShareManager.shareUrlToWX(false, activityUrl+"?goodsId="+goodsId, bitmap, "百度", "我是百度");
    }

    //分享成功
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShareSuccess(String code) {
        showMessage(getString(R.string.share_success));

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
