package com.mall.sls.homepage.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.FormatUtil;
import com.mall.sls.common.unit.HtmlUnit;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.unit.TCAgentUnit;
import com.mall.sls.common.unit.TimeUtil;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.DhmsTearDownView;
import com.mall.sls.common.widget.textview.DrawTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.common.widget.textview.TearDownView;
import com.mall.sls.data.entity.ConfirmOrderDetail;
import com.mall.sls.data.entity.GoodsDetailsInfo;
import com.mall.sls.data.entity.GoodsSpec;
import com.mall.sls.data.entity.GroupPeople;
import com.mall.sls.data.entity.GroupPurchase;
import com.mall.sls.data.entity.InvitationCodeInfo;
import com.mall.sls.data.entity.ProductListCallableInfo;
import com.mall.sls.homepage.DaggerHomepageComponent;
import com.mall.sls.homepage.HomepageContract;
import com.mall.sls.homepage.HomepageModule;
import com.mall.sls.homepage.presenter.GoodsDetailsPresenter;
import com.mall.sls.webview.unit.JSBridgeWebChromeClient;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/14.
 * 描述：活动团
 */
public class ActivityGroupGoodsActivity extends BaseActivity implements HomepageContract.GoodsDetailsView, TearDownView.TimeOutListener, DhmsTearDownView.TimeOutListener {


    @Inject
    GoodsDetailsPresenter goodsDetailsPresenter;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.activity_rule_iv)
    ImageView activityRuleIv;
    @BindView(R.id.activity_name)
    ConventionalTextView activityName;
    @BindView(R.id.count_down_tv)
    ConventionalTextView countDownTv;
    @BindView(R.id.count_down)
    TearDownView countDown;
    @BindView(R.id.day_tv)
    MediumThickTextView dayTv;
    @BindView(R.id.goods_iv)
    ImageView goodsIv;
    @BindView(R.id.discountMember)
    ConventionalTextView discountMember;
    @BindView(R.id.goods_name)
    ConventionalTextView goodsName;
    @BindView(R.id.goods_introduction)
    ConventionalTextView goodsIntroduction;
    @BindView(R.id.current_price)
    MediumThickTextView currentPrice;
    @BindView(R.id.original_price)
    DrawTextView originalPrice;
    @BindView(R.id.goods_rl)
    RelativeLayout goodsRl;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;
    @BindView(R.id.snapped_up_number)
    ConventionalTextView snappedUpNumber;
    @BindView(R.id.count_down_time)
    DhmsTearDownView countDownTime;
    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.goods_detail_iv)
    ImageView goodsDetailIv;


    private String goodsId;
    private GoodsDetailsInfo goodsDetailsInfo;
    private String groupId;
    private String groupRulesId;

    private ProductListCallableInfo productListCallableInfo;
    private int goodsCount = 1;
    private List<GroupPeople> groupPeoples;
    private List<GroupPurchase> groupPurchases;
    private String wxUrl;
    private String inviteCode;
    private List<ProductListCallableInfo> productListCallableInfos;
    private List<String> checkSkus;
    private List<GoodsSpec> goodsSpecs;
    private String picUrl;

    public static void start(Context context, String goodsId) {
        Intent intent = new Intent(context, ActivityGroupGoodsActivity.class);
        intent.putExtra(StaticData.GOODS_ID, goodsId);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_group_goods);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        goodsId = getIntent().getStringExtra(StaticData.GOODS_ID);
        initWebView();
        goodsDetailsPresenter.getGoodsDetails(goodsId);
        goodsDetailsPresenter.getInvitationCodeInfo();

    }


    @Override
    protected void initializeInjector() {
        DaggerHomepageComponent.builder()
                .applicationComponent(getApplicationComponent())
                .homepageModule(new HomepageModule(this))
                .build()
                .inject(this);
    }

    private void initWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); //取消滚动条白边效果
        webView.setWebChromeClient(new JSBridgeWebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                // TODO Auto-generated method stub
                // handler.cancel();// Android默认的处理方式
                handler.proceed();// 接受所有网站的证书
                // handleMessage(Message msg);// 进行其他处理
            }
        });
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.getSettings().setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(webView.getSettings().MIXED_CONTENT_ALWAYS_ALLOW);  //注意安卓5.0以上的权限
        }
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void renderGoodsDetails(GoodsDetailsInfo goodsDetailsInfo) {
        this.goodsDetailsInfo = goodsDetailsInfo;
        if (goodsDetailsInfo != null) {
            GlideHelper.load(this, goodsDetailsInfo.getPicUrl(), R.mipmap.ic_launcher, goodsIv);
            activityName.setText(goodsDetailsInfo.getGroupName());
            goodsName.setText(goodsDetailsInfo.getName());
            discountMember.setText(goodsDetailsInfo.getDiscountMember() + "人成团");
            if (TextUtils.equals(StaticData.REFLASH_ZERO, goodsDetailsInfo.getGroupPeopleNum())) {
                snappedUpNumber.setVisibility(View.GONE);
            } else {
                snappedUpNumber.setVisibility(View.VISIBLE);
                snappedUpNumber.setText(goodsDetailsInfo.getGroupPeopleNum() + "人已抢" + goodsDetailsInfo.getGroupGoodsNum() + ",");
            }
            countDown.setTimeOutListener(this);
            countDownTime.setTimeOutListener(this);
            goodsIntroduction.setText(goodsDetailsInfo.getBrief());
            currentPrice.setText(NumberFormatUnit.goodsFormat(goodsDetailsInfo.getRetailPrice()));
            originalPrice.setText(NumberFormatUnit.goodsFormat(goodsDetailsInfo.getCounterPrice()));
            if (!TextUtils.isEmpty(goodsDetailsInfo.getNow()) && !TextUtils.isEmpty(goodsDetailsInfo.getGroupExpireTime())) {
                long now = FormatUtil.dateToStamp(goodsDetailsInfo.getNow());
                long groupExpireTime = FormatUtil.dateToStamp(goodsDetailsInfo.getGroupExpireTime());
                long day = FormatUtil.day(now, groupExpireTime);
                if (now < groupExpireTime) {
                    if (day > 0) {
                        dayTv.setText(day + "天");
                        dayTv.setVisibility(View.VISIBLE);
                        countDown.setVisibility(View.GONE);
                        countDownTv.setText(getString(R.string.remaining_spike));
                    } else {
                        dayTv.setVisibility(View.GONE);
                        countDown.setVisibility(View.VISIBLE);
                        countDown.startTearDown(groupExpireTime, now);
                        countDownTv.setText(getString(R.string.from_end));
                    }
                    countDownTime.startTearDown(groupExpireTime / 1000, now / 1000);
                }
            }
            groupPeoples = goodsDetailsInfo.getGroupPeoples();
            if (groupPeoples != null && groupPeoples.size() > 0) {
                for (int i = 0; i < groupPeoples.size(); i++) {
                    View view1 = View.inflate(this, R.layout.item_group_people, null);
                    TextView people = view1.findViewById(R.id.people);
                    long now = FormatUtil.dateToStamp(goodsDetailsInfo.getNow());
                    long createTime = FormatUtil.dateToStamp(groupPeoples.get(i).getAddTime());
                    String timeLast = TimeUtil.getTimeFormatText(String.valueOf(now), String.valueOf(createTime));
                    people.setText(groupPeoples.get(i).getNickname() + "在"+timeLast + "参与拼单");
                    viewFlipper.addView(view1);
                }
                viewFlipper.setFlipInterval(2000);
                viewFlipper.startFlipping();
            }
            groupPurchases = goodsDetailsInfo.getGroupPurchases();
            if (groupPurchases != null && groupPurchases.size() == 1) {
                groupId = groupPurchases.get(0).getGrouponId();
                groupRulesId = groupPurchases.get(0).getRulesId();
            }
            picUrl = goodsDetailsInfo.getPicUrl();
            goodsSpecs = goodsDetailsInfo.getGoodsSpecs();
            productListCallableInfos = goodsDetailsInfo.getProductListCallableInfos();
            if (productListCallableInfos != null && productListCallableInfos.size() == 1) {
                ProductListCallableInfo productListCallableInfo = productListCallableInfos.get(0);
                String specifications = productListCallableInfo.getSpecifications();
                if (!TextUtils.isEmpty(specifications)) {
                    checkSkus = Arrays.asList(specifications.split(","));
                }
            }
            if (!TextUtils.isEmpty(goodsDetailsInfo.getDetail())) {
                webView.loadDataWithBaseURL(null, HtmlUnit.getHtmlData(goodsDetailsInfo.getDetail()), "text/html", "utf-8", null);
            }
            goodsDetailIv.setVisibility(TextUtils.isEmpty(goodsDetailsInfo.getDetail())?View.GONE:View.VISIBLE);
        }

    }

    @Override
    public void renderConsumerPhone(String consumerPhone) {

    }

    @Override
    public void renderCartFastAdd(ConfirmOrderDetail confirmOrderDetail) {
        ConfirmOrderActivity.start(this, confirmOrderDetail, StaticData.REFLASH_FOUR, wxUrl, inviteCode);
    }

    @Override
    public void renderGroupRemind() {

    }

    @Override
    public void renderInvitationCodeInfo(InvitationCodeInfo invitationCodeInfo) {
        if (invitationCodeInfo != null) {
            wxUrl = invitationCodeInfo.getBaseUrl();
            inviteCode = invitationCodeInfo.getInvitationCode();
        }
    }

    @Override
    public void setPresenter(HomepageContract.GoodsDetailsPresenter presenter) {

    }

    @Override
    public void timeOut() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTime.cancel();
        countDown.cancel();
    }

    @OnClick({R.id.confirm_bt, R.id.back, R.id.goods_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_bt:
                TCAgentUnit.setEventId(this, getString(R.string.event_purchase_details));
                goSelectSpec(StaticData.REFLASH_ONE);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.goods_rl:
                ActivityGoodsDetailActivity.start(this, goodsId);
                finish();
                break;
            default:
        }
    }

    private void goSelectSpec(String type) {
        Intent intent = new Intent(this, SelectSpecActivity.class);
        intent.putExtra(StaticData.PRODUCT_LIST, (Serializable) goodsSpecs);
        intent.putExtra(StaticData.SPECIFICATION_LIST, (Serializable) productListCallableInfos);
        intent.putExtra(StaticData.PIC_URL, picUrl);
        intent.putExtra(StaticData.SKU_CHECK, (Serializable) checkSkus);
        intent.putExtra(StaticData.CHOICE_TYPE, type);
        startActivityForResult(intent, RequestCodeStatic.REQUEST_SPEC);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeStatic.REQUEST_SPEC:
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        productListCallableInfo = (ProductListCallableInfo) bundle.getSerializable(StaticData.SKU_INFO);
                        checkSkus = (List<String>) bundle.getSerializable(StaticData.SKU_CHECK);
                        goodsCount = bundle.getInt(StaticData.GOODS_COUNT);
                        goodsDetailsPresenter.cartFastAdd(goodsId, productListCallableInfo.getId(), true, String.valueOf(goodsCount), groupId, groupRulesId);
                    }
                    break;
                default:
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        TCAgentUnit.pageStart(this, getString(R.string.event_page));
    }

    @Override
    protected void onPause() {
        super.onPause();
        TCAgentUnit.pageEnd(this, getString(R.string.event_page));
    }
}
