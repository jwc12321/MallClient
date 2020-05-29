package com.mall.sls.homepage.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.makeramen.roundedimageview.RoundedImageView;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.AvatarUrlManager;
import com.mall.sls.common.unit.FormatUtil;
import com.mall.sls.common.unit.WXShareManager;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MSTearDownView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.mainframe.ui.MainFrameActivity;
import com.mall.sls.mine.ui.SelectShareTypeActivity;

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
public class WXShareBackActivity extends BaseActivity implements MSTearDownView.TimeOutListener {
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
    @BindView(R.id.successful_order)
    ConventionalTextView successfulOrder;
    @BindView(R.id.count_down_ll)
    LinearLayout countDownLl;

    private String choiceType;
    private String goodsId;
    private String wxUrl;
    private String inviteCode;
    private String endTime;
    private WXShareManager wxShareManager;
    private long groupExpireTime;
    private long now;
    private String nameText;
    private String briefText;
    private String backType;
    private String grouponId;
    private String goodsProductId;

    public static void start(Context context, String choiceType, String nameText, String briefText, String goodsId, String wxUrl,String inviteCode, String endTime, String grouponId, String goodsProductId) {
        Intent intent = new Intent(context, WXShareBackActivity.class);
        intent.putExtra(StaticData.GOODS_ID, goodsId);
        intent.putExtra(StaticData.GOODS_NAME, nameText);
        intent.putExtra(StaticData.GOODS_BRIEF, briefText);
        intent.putExtra(StaticData.CHOICE_TYPE, choiceType);
        intent.putExtra(StaticData.WX_URL,wxUrl);
        intent.putExtra(StaticData.INVITE_CODE,inviteCode);
        intent.putExtra(StaticData.END_TIME, endTime);
        intent.putExtra(StaticData.GROUPON_ID, grouponId);
        intent.putExtra(StaticData.GOODS_PRODUCT_ID, goodsProductId);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wx_share_back);
        ButterKnife.bind(this);
        setHeight(null, title, null);
        initView();
    }

    private void initView() {
        EventBus.getDefault().register(this);
        wxShareManager = WXShareManager.getInstance(this);
        choiceType = getIntent().getStringExtra(StaticData.CHOICE_TYPE);
        nameText = getIntent().getStringExtra(StaticData.GOODS_NAME);
        briefText = getIntent().getStringExtra(StaticData.GOODS_BRIEF);
        goodsId = getIntent().getStringExtra(StaticData.GOODS_ID);
        wxUrl=getIntent().getStringExtra(StaticData.WX_URL);
        inviteCode=getIntent().getStringExtra(StaticData.INVITE_CODE);
        endTime = getIntent().getStringExtra(StaticData.END_TIME);
        grouponId = getIntent().getStringExtra(StaticData.GROUPON_ID);
        goodsProductId = getIntent().getStringExtra(StaticData.GOODS_PRODUCT_ID);
        GlideHelper.load(this, AvatarUrlManager.getAvatarUrl(), R.mipmap.icon_defalut_head, headPhoto);
        now = System.currentTimeMillis();
        if (TextUtils.equals(StaticData.REFLASH_ZERO, choiceType)) {//日常团
            groupExpireTime = now + 15 * 60 * 1000;
            countDown.startTearDown(groupExpireTime / 1000, now / 1000);
            surplus.setText(getString(R.string.invite_one_people));
            countDownLl.setVisibility(View.VISIBLE);
            successfulOrder.setVisibility(View.GONE);
        } else {//活动团
            groupExpireTime = FormatUtil.dateToStamp(endTime);
            countDown.startTearDown(groupExpireTime / 1000, now / 1000);
            surplus.setText(getString(R.string.invite_friend_for_reward));
        }
    }


    @OnClick({R.id.home_iv, R.id.weixin_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_iv:
                MainFrameActivity.start(this);
                finish();
                break;
            case R.id.weixin_iv:
                Intent intent = new Intent(this, SelectShareTypeActivity.class);
                startActivityForResult(intent, RequestCodeStatic.SELECT_SHARE_TYPE);
                break;
            default:
        }
    }

    private void shareActivityWx(boolean isFriend) {
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.app_icon);
        String url = wxUrl + "activity/" + goodsId + "?inviteCode=" + inviteCode;
        wxShareManager.shareUrlToWX(isFriend, url, bitmap, nameText, briefText);
    }

    private void shareGroupWx(boolean isFriend) {
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.app_icon);
        String url = wxUrl + "group/" + grouponId + "/" + goodsProductId + "?inviteCode=" + inviteCode;
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
                        if (TextUtils.equals(StaticData.REFLASH_ZERO, choiceType)) {
                            shareGroupWx(TextUtils.equals(StaticData.REFLASH_ONE, backType));
                        } else {
                            shareActivityWx(TextUtils.equals(StaticData.REFLASH_ONE, backType));
                        }
                    }
                    break;
                default:
            }
        }
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

    @Override
    public void timeOut() {
        if (TextUtils.equals(StaticData.REFLASH_ZERO, choiceType)) {
            countDownLl.setVisibility(View.GONE);
            successfulOrder.setVisibility(View.VISIBLE);
            surplus.setVisibility(View.INVISIBLE);
        }
    }
}
