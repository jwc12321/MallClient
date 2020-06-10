package com.mall.sls.lottery.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.FormatUtil;
import com.mall.sls.common.unit.HtmlUnit;
import com.mall.sls.common.unit.MainStartManager;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.unit.PayResult;
import com.mall.sls.common.unit.PayTypeInstalledUtils;
import com.mall.sls.common.unit.StaticHandler;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.common.widget.textview.WhiteDMSTearDownView;
import com.mall.sls.data.entity.CustomViewsInfo;
import com.mall.sls.data.entity.JoinPrizeInfo;
import com.mall.sls.data.entity.PrizeVo;
import com.mall.sls.data.entity.WXPaySignResponse;
import com.mall.sls.data.event.PayAbortEvent;
import com.mall.sls.data.event.WXSuccessPayEvent;
import com.mall.sls.homepage.ui.SelectPayTypeActivity;
import com.mall.sls.homepage.ui.TitleContentActivity;
import com.mall.sls.lottery.DaggerLotteryComponent;
import com.mall.sls.lottery.LotteryContract;
import com.mall.sls.lottery.LotteryModule;
import com.mall.sls.lottery.presenter.LotteryDetailsPresenter;
import com.mall.sls.mainframe.ui.MainFrameActivity;
import com.mall.sls.webview.unit.JSBridgeWebChromeClient;
import com.stx.xhb.androidx.XBanner;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/6/9.
 * 描述：抽奖详情
 */
public class LotteryDetailActivity extends BaseActivity implements LotteryContract.LotteryDetailsView,NestedScrollView.OnScrollChangeListener {
    @BindView(R.id.banner)
    XBanner banner;
    @BindView(R.id.goods_price)
    MediumThickTextView goodsPrice;
    @BindView(R.id.count_down)
    WhiteDMSTearDownView countDown;
    @BindView(R.id.count_down_ll)
    LinearLayout countDownLl;
    @BindView(R.id.goods_name)
    MediumThickTextView goodsName;
    @BindView(R.id.prizeTime)
    ConventionalTextView prizeTime;
    @BindView(R.id.participantNumber)
    ConventionalTextView participantNumber;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.scrollview)
    NestedScrollView scrollview;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.home_iv)
    ImageView homeIv;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;
    @BindView(R.id.day_tv)
    ConventionalTextView dayTv;

    private String prizeNumber;
    private PrizeVo prizeVo;
    private int screenWidth;
    private int screenHeight;
    private List<String> banners;
    private List<CustomViewsInfo> data;
    private String nowTime;
    private String name;
    private String picUrl;
    private String goodsCount;
    private String counterPrice;
    private BigDecimal priceBg;
    private BigDecimal countBg;
    private BigDecimal totalBg;
    private String selectType;
    private String prizeId;
    private String prizeTimeText;
    private Handler mHandler = new MyHandler(this);

    @Inject
    LotteryDetailsPresenter lotteryDetailsPresenter;

    public static void start(Context context, String prizeNumber, PrizeVo prizeVo) {
        Intent intent = new Intent(context, LotteryDetailActivity.class);
        intent.putExtra(StaticData.PRIZE_NUMBER, prizeNumber);
        intent.putExtra(StaticData.PRIZE_VO, prizeVo);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_lotter_detail);
        ButterKnife.bind(this);
        setHeight(back, null, null);
        initView();
    }

    private void initView() {
        EventBus.getDefault().register(this);
        prizeNumber = getIntent().getStringExtra(StaticData.PRIZE_NUMBER);
        prizeVo = (PrizeVo) getIntent().getSerializableExtra(StaticData.PRIZE_VO);
        scrollview.setOnScrollChangeListener(this);
        settingHeight();
        xBannerInit();
        initWebView();
        lotteryDetailsPresenter.getSystemTime();

    }

    @Override
    protected void initializeInjector() {
        DaggerLotteryComponent.builder()
                .applicationComponent(getApplicationComponent())
                .lotteryModule(new LotteryModule(this))
                .build()
                .inject(this);
    }

    private void initData() {
        if (prizeVo != null) {
            banners = prizeVo.getGallerys();
            if (data == null) {
                data = new ArrayList<>();
            } else {
                data.clear();
            }
            if (banners != null) {
                for (String bannerInfo : banners) {
                    data.add(new CustomViewsInfo(bannerInfo));
                }
            }
            banner.setPointsIsVisible(data.size() > 1);
            banner.setAutoPlayAble(data.size() > 1);
            banner.setBannerData(R.layout.xbanner_item, data);
            name=prizeVo.getPrizeTitle();
            picUrl=prizeVo.getPicUrl();
            goodsPrice.setText("¥" + NumberFormatUnit.twoDecimalFormat(prizeVo.getCounterPrice()));
            prizeTimeText=prizeVo.getPrizeTime();
            prizeTime.setText(prizeVo.getPrizeTime() + " 开奖");
            participantNumber.setVisibility(TextUtils.equals(StaticData.REFLASH_ZERO, prizeVo.getParticipantNumber()) ? View.GONE : View.VISIBLE);
            participantNumber.setText(prizeVo.getParticipantNumber() + "人参与");
            goodsName.setText(prizeVo.getPrizeTitle());
            prizeId=prizeVo.getPrizeId();
            if (!TextUtils.isEmpty(prizeVo.getDetail())) {
                webView.loadDataWithBaseURL(null, HtmlUnit.getHtmlData(prizeVo.getDetail()), "text/html", "utf-8", null);
            }
            if (!TextUtils.isEmpty(nowTime) && !TextUtils.isEmpty(prizeVo.getEndTime())) {
                long now = FormatUtil.dateToStamp(nowTime);
                long groupExpireTime = FormatUtil.dateToStamp(prizeVo.getEndTime());
                if (now < groupExpireTime) {
                    countDownLl.setVisibility(View.VISIBLE);
                    long day = FormatUtil.day(now, groupExpireTime);
                    if (day > 0) {
                        dayTv.setText(day + "天");
                        dayTv.setVisibility(View.VISIBLE);
                        countDown.setVisibility(View.GONE);
                    } else {
                        dayTv.setVisibility(View.GONE);
                        countDown.setVisibility(View.VISIBLE);
                        countDown.startTearDown(groupExpireTime / 1000, now / 1000);
                    }
                } else {
                    countDownLl.setVisibility(View.GONE);
                }
            }else {
                countDownLl.setVisibility(View.GONE);
            }
            counterPrice=prizeVo.getPrice();
            if(TextUtils.equals(StaticData.REFLASH_ONE,prizeVo.getPrizeStatus())){
                confirmBt.setEnabled(true);
                confirmBt.setBackgroundResource(R.drawable.common_twenty_back_one_bg);
                if(TextUtils.equals(StaticData.REFLASH_ZERO,prizeVo.getPrice())||TextUtils.equals("0.00",prizeVo.getPrice())){
                    confirmBt.setText("0"+getString(R.string.yuan_draw));
                }else {
                    confirmBt.setText(NumberFormatUnit.twoDecimalFormat(prizeVo.getPrice())+getString(R.string.yuan_draw));
                }
            }else if(TextUtils.equals(StaticData.REFLASH_TWO,prizeVo.getPrizeStatus())){
                confirmBt.setEnabled(false);
                confirmBt.setBackgroundResource(R.drawable.common_twenty_back_twenty_three_bg);
                confirmBt.setText(getString(R.string.waiting_draw));
            }else {
                confirmBt.setEnabled(false);
                confirmBt.setBackgroundResource(R.drawable.common_twenty_back_fifty_bg);
                confirmBt.setText(getString(R.string.is_over));
            }
        }
    }

    private void settingHeight() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = screenWidth;
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) banner.getLayoutParams(); //取控件textView当前的布局参数
        linearParams.height = screenHeight;// 控件的高强制
        linearParams.width = screenWidth;// 控件的高强制
        banner.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
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


    private void xBannerInit() {
        //设置广告图片点击事件
        banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
            }
        });
        //加载广告图片
        banner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                //在此处使用图片加载框架加载图片，demo中使用glide加载，可替换成自己项目中的图片加载框架
                RoundedImageView roundedImageView = (RoundedImageView) view;
                CustomViewsInfo customViewsInfo = ((CustomViewsInfo) model);
                Glide.with(LotteryDetailActivity.this).load(customViewsInfo.getXBannerUrl()).error(R.mipmap.ic_launcher).diskCacheStrategy(DiskCacheStrategy.ALL).into(roundedImageView);
            }
        });
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY <= 0) {   //设置标题的背景颜色
            titleRel.setBackgroundColor(Color.argb((int) 0, 144, 151, 166));
        } else if (scrollY > 0 && scrollY <= titleRel.getHeight()) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) scrollY / titleRel.getHeight();
            float alpha = (255 * scale);
            titleRel.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
        } else {    //滑动到banner下面设置普通颜色
            titleRel.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
        }
    }

    @Override
    public void renderSystemTime(String time) {
        nowTime=time;
        initData();
    }

    @Override
    public void renderJoinPrizeInfo(JoinPrizeInfo joinPrizeInfo) {
        if(joinPrizeInfo!=null){
            if(TextUtils.equals(StaticData.REFLASH_TWO,selectType)){//免费
                LotteryResultActivity.start(this,prizeId,prizeTimeText);
                finish();
            }else if(TextUtils.equals(StaticData.REFLASH_ONE,selectType)&&!TextUtils.isEmpty(joinPrizeInfo.getAliPaySign())){//支付宝
                startAliPay(joinPrizeInfo.getAliPaySign());
            }else if(TextUtils.equals(StaticData.REFLASH_ZERO,selectType)&&joinPrizeInfo.getWxPaySignResponse()!=null){//微信
                wechatPay(joinPrizeInfo.getWxPaySignResponse());

            }
        }
    }

    @Override
    public void setPresenter(LotteryContract.LotteryDetailsPresenter presenter) {

    }

    @OnClick({R.id.back,R.id.home_iv,R.id.confirm_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.home_iv:
                MainStartManager.saveMainStart(StaticData.REFLASH_ONE);
                MainFrameActivity.start(this);
                finish();
                break;
            case R.id.confirm_bt:
                confirm();
                break;
            default:
        }
    }

    private void confirm(){
        if(TextUtils.equals(StaticData.REFLASH_ZERO,prizeNumber)){
            Intent intent = new Intent(this, TitleContentActivity.class);
            intent.putExtra(StaticData.COMMON_TITLE, getString(R.string.no_lottery_time));
            intent.putExtra(StaticData.COMMON_CONTENT, getString(R.string.do_the_task));
            intent.putExtra(StaticData.CANCEL_TEXT, getString(R.string.i_know));
            intent.putExtra(StaticData.CONFIRM_TEXT, getString(R.string.go_shop));
            startActivityForResult(intent, RequestCodeStatic.TIP_PAGE);
        }else {
            Intent intent = new Intent(this, SelectQuantityActivity.class);
            intent.putExtra(StaticData.PRIZE_NUMBER,prizeNumber);
            intent.putExtra(StaticData.GOODS_NAME,name);
            intent.putExtra(StaticData.PIC_URL,picUrl);
            startActivityForResult(intent, RequestCodeStatic.SELECT_QUANTITY);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeStatic.SELECT_QUANTITY:
                    if (data != null) {
                        goodsCount = data.getStringExtra(StaticData.COUNT);
                        selectPayType();
                    }
                    break;
                case RequestCodeStatic.PAY_TYPE://选择支付方式
                    if (data != null) {
                        selectType = data.getStringExtra(StaticData.SELECT_TYPE);
                        if (TextUtils.equals(StaticData.REFLASH_ZERO, selectType)) {
                            //微信
                            if (PayTypeInstalledUtils.isWeixinAvilible(LotteryDetailActivity.this)) {
                                lotteryDetailsPresenter.getJoinPrizeInfo(prizeId,goodsCount,selectType);
                            } else {
                                showMessage(getString(R.string.install_weixin));
                            }
                        } else if(TextUtils.equals(StaticData.REFLASH_ONE, selectType)){
                            if (PayTypeInstalledUtils.isAliPayInstalled(LotteryDetailActivity.this)) {
                                lotteryDetailsPresenter.getJoinPrizeInfo(prizeId,goodsCount,selectType);
                            } else {
                                showMessage(getString(R.string.install_alipay));
                            }
                        }
                    }
                    break;
                case RequestCodeStatic.TIP_PAGE:
                    MainFrameActivity.start(this);
                    finish();
                    break;
                default:
            }
        }
    }


    private void selectPayType(){
        priceBg = new BigDecimal(TextUtils.isEmpty(counterPrice) ? "0" : counterPrice).setScale(2, RoundingMode.HALF_UP);
        countBg = new BigDecimal(goodsCount);
        totalBg=priceBg.multiply(countBg);
        if(totalBg.compareTo(new BigDecimal(0))>0){
            Intent intent = new Intent(this, SelectPayTypeActivity.class);
            intent.putExtra(StaticData.CHOICE_TYPE, StaticData.REFLASH_TWO);
            intent.putExtra(StaticData.PAYMENT_AMOUNT, totalBg.toPlainString());
            startActivityForResult(intent, RequestCodeStatic.PAY_TYPE);
        }else {
            selectType=StaticData.REFLASH_TWO;
            lotteryDetailsPresenter.getJoinPrizeInfo(prizeId,goodsCount,"");
        }
    }


    private void startAliPay(String sign) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                PayTask payTask = new PayTask(LotteryDetailActivity.this);
                Map<String, String> result = payTask.payV2(sign, true);
                Message message = Message.obtain();
                message.what = RequestCodeStatic.SDK_PAY_FLAG;
                message.obj = result;
                mHandler.sendMessage(message);
            }
        };

        new Thread(runnable).start();
    }

    public static class MyHandler extends StaticHandler<LotteryDetailActivity> {

        public MyHandler(LotteryDetailActivity target) {
            super(target);
        }

        @Override
        public void handle(LotteryDetailActivity target, Message msg) {
            switch (msg.what) {
                case RequestCodeStatic.SDK_PAY_FLAG:
                    target.alpay(msg);
                    break;
            }
        }
    }

    //跳转到主页
    private void alpay(Message msg) {
        PayResult payResult = new PayResult((Map<String, String>) msg.obj);
        String resultStatus = payResult.getResultStatus();
        if (TextUtils.equals(resultStatus, "9000")) {
            LotteryResultActivity.start(this,prizeId,prizeTimeText);
            finish();
        } else if (TextUtils.equals(resultStatus, "6001")) {
            showMessage(getString(R.string.pay_cancel));
        } else {
            showMessage(getString(R.string.pay_failed));
        }
    }


    public void wechatPay(WXPaySignResponse wxPaySignResponse) {
        // 将该app注册到微信
        IWXAPI wxapi = WXAPIFactory.createWXAPI(this, StaticData.WX_APP_ID);
        PayReq request = new PayReq();
        request.appId = wxPaySignResponse.getAppid();
        request.partnerId = wxPaySignResponse.getPartnerId();
        request.prepayId = wxPaySignResponse.getPrepayId();
        request.packageValue = wxPaySignResponse.getPackageValue();
        request.nonceStr = wxPaySignResponse.getNonceStr();
        request.timeStamp = wxPaySignResponse.getTimestamp();
        request.sign = wxPaySignResponse.getSign();
        wxapi.sendReq(request);
    }

    //支付成功
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPaySuccess(WXSuccessPayEvent event) {
        LotteryResultActivity.start(this,prizeId,prizeTimeText);
        finish();
    }

    //支付失败
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayCancel(PayAbortEvent event) {
        if (event != null) {
            if (event.code == -1) {
                showMessage(getString(R.string.pay_failed));
            } else if (event.code == -2) {
                showMessage(getString(R.string.pay_cancel));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
