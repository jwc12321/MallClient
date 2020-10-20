package com.mall.sls.homepage.ui;

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

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.unit.HtmlUnit;
import com.mall.sls.homepage.DaggerHomepageComponent;
import com.mall.sls.homepage.HomepageContract;
import com.mall.sls.homepage.HomepageModule;
import com.mall.sls.homepage.presenter.ShipInfoPresenter;
import com.mall.sls.webview.unit.JSBridgeWebChromeClient;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/10/14.
 * 描述：配送说明
 */
public class DeliveryNoteActivity extends BaseActivity implements HomepageContract.ShipInfoView {
    @BindView(R.id.close_iv)
    ImageView closeIv;

    @Inject
    ShipInfoPresenter shipInfoPresenter;
    @BindView(R.id.webView)
    WebView webView;

    public static void start(Context context) {
        Intent intent = new Intent(context, DeliveryNoteActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_note);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        initWebView();
        shipInfoPresenter.getShipInfo();
    }

    private void initWebView() {
        webView.setBackgroundColor(getResources().getColor(R.color.backGround57));
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
    protected void initializeInjector() {
        DaggerHomepageComponent.builder()
                .applicationComponent(getApplicationComponent())
                .homepageModule(new HomepageModule(this))
                .build()
                .inject(this);
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

    @Override
    public void renderShipInfo(String shipInfo) {
        if (!TextUtils.isEmpty(shipInfo)) {
            if(isNightMode(this)) {
                webView.loadDataWithBaseURL(null, HtmlUnit.getBlackHtmlData(shipInfo), "text/html", "utf-8", null);
            }else {
                webView.loadDataWithBaseURL(null, HtmlUnit.getHtmlData(shipInfo), "text/html", "utf-8", null);
            }
        }
    }

    @Override
    public void setPresenter(HomepageContract.ShipInfoPresenter presenter) {

    }
}
