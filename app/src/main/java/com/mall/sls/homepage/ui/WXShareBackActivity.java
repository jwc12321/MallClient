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
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.MainStartManager;
import com.mall.sls.common.unit.PayTypeInstalledUtils;
import com.mall.sls.common.unit.QRCodeFileUtils;
import com.mall.sls.common.unit.WXShareManager;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.mainframe.ui.MainFrameActivity;
import com.mall.sls.mine.ui.SelectShareTypeActivity;
import com.mall.sls.order.ui.GoodsOrderDetailsActivity;

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


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.tip)
    MediumThickTextView tip;
    @BindView(R.id.order_iv)
    ImageView orderIv;
    @BindView(R.id.weixin_iv)
    ImageView weixinIv;
    @BindView(R.id.home_iv)
    ImageView homeIv;
    @BindView(R.id.look_order)
    ConventionalTextView lookOrder;
    @BindView(R.id.share_iv)
    ImageView shareIv;
    private String goodsId;
    private String wxUrl;
    private String inviteCode;
    private WXShareManager wxShareManager;
    private String nameText;
    private String briefText;
    private String backType;
    private String grouponId;
    private String goodsProductId;
    private String goodsOrderId;
    private String picUrl;
    private Bitmap shareBitMap;

    //1:单独购买 2：发起拼单 3：拼团 4：百人团
    private String purchaseType;

    public static void start(Context context, String purchaseType, String nameText, String briefText, String goodsId, String wxUrl, String inviteCode, String grouponId, String goodsProductId, String goodsOrderId, String picUrl) {
        Intent intent = new Intent(context, WXShareBackActivity.class);
        intent.putExtra(StaticData.GOODS_ID, goodsId);
        intent.putExtra(StaticData.GOODS_NAME, nameText);
        intent.putExtra(StaticData.GOODS_BRIEF, briefText);
        intent.putExtra(StaticData.PURCHASE_TYPE, purchaseType);
        intent.putExtra(StaticData.WX_URL, wxUrl);
        intent.putExtra(StaticData.INVITE_CODE, inviteCode);
        intent.putExtra(StaticData.GROUPON_ID, grouponId);
        intent.putExtra(StaticData.GOODS_PRODUCT_ID, goodsProductId);
        intent.putExtra(StaticData.GOODS_ORDER_ID, goodsOrderId);
        intent.putExtra(StaticData.PIC_URL, picUrl);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wx_share_back);
        ButterKnife.bind(this);
        setHeight(back, null, null);
        initView();
    }

    private void initView() {
        EventBus.getDefault().register(this);
        wxShareManager = WXShareManager.getInstance(this);
        purchaseType = getIntent().getStringExtra(StaticData.PURCHASE_TYPE);
        nameText = getIntent().getStringExtra(StaticData.GOODS_NAME);
        briefText = getIntent().getStringExtra(StaticData.GOODS_BRIEF);
        goodsId = getIntent().getStringExtra(StaticData.GOODS_ID);
        wxUrl = getIntent().getStringExtra(StaticData.WX_URL);
        inviteCode = getIntent().getStringExtra(StaticData.INVITE_CODE);
        grouponId = getIntent().getStringExtra(StaticData.GROUPON_ID);
        goodsProductId = getIntent().getStringExtra(StaticData.GOODS_PRODUCT_ID);
        goodsOrderId = getIntent().getStringExtra(StaticData.GOODS_ORDER_ID);
        picUrl = getIntent().getStringExtra(StaticData.PIC_URL);
        if (TextUtils.equals(StaticData.REFLASH_ONE, purchaseType) || TextUtils.equals(StaticData.REFLASH_THREE, purchaseType)) {
            orderIv.setVisibility(View.VISIBLE);
            weixinIv.setVisibility(View.GONE);
            tip.setVisibility(View.INVISIBLE);
            lookOrder.setVisibility(View.INVISIBLE);
        } else {
            orderIv.setVisibility(View.GONE);
            weixinIv.setVisibility(View.VISIBLE);
            tip.setVisibility(View.VISIBLE);
            lookOrder.setVisibility(View.VISIBLE);
        }
        GlideHelper.load(this,picUrl, R.mipmap.icon_default_goods, shareIv);
    }


    @OnClick({R.id.home_iv, R.id.weixin_iv, R.id.back, R.id.order_iv, R.id.look_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_iv://首页
                MainStartManager.saveMainStart(StaticData.REFLASH_ONE);
                MainFrameActivity.start(this);
                finish();
                break;
            case R.id.weixin_iv://微信分享
                if (!PayTypeInstalledUtils.isWeixinAvilible(WXShareBackActivity.this)) {
                    showMessage(getString(R.string.install_weixin));
                    return;
                }
                shareBitMap = QRCodeFileUtils.createBitmap3(shareIv,150,150);//直接url转bitmap背景白色变成黑色，后面想到方法可以改善
                Intent intent = new Intent(this, SelectShareTypeActivity.class);
                startActivityForResult(intent, RequestCodeStatic.SELECT_SHARE_TYPE);
                break;
            case R.id.back://返回
                finish();
                break;
            case R.id.order_iv:
            case R.id.look_order:
                GoodsOrderDetailsActivity.start(this, goodsOrderId);
                finish();
                break;
            default:
        }
    }

    private void shareActivityWx(boolean isFriend) {
//        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.app_icon);
        String url = wxUrl + "activity/" + goodsId + StaticData.WX_INVITE_CODE + inviteCode;
        wxShareManager.shareUrlToWX(isFriend, url, shareBitMap, nameText, briefText);
    }

    private void shareGroupWx(boolean isFriend) {
//        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.app_icon);
        String url = wxUrl + "group/" + grouponId + "/" + goodsProductId + StaticData.WX_INVITE_CODE + inviteCode;
        wxShareManager.shareUrlToWX(isFriend, url, shareBitMap, nameText, briefText);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeStatic.SELECT_SHARE_TYPE:
                    if (data != null) {
                        backType = data.getStringExtra(StaticData.BACK_TYPE);
                        if (TextUtils.equals(StaticData.REFLASH_TWO, purchaseType)) {
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

}
