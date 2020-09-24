package com.mall.sls.webview.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebHistoryItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * Created by JWC on 2018/4/27.
 */

public class WebViewActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.webView)
    WebView webView;

    private String webUrl;
    private String userAgent;

    public static void start(Context context, String webUrl) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(StaticData.WEB_URL, webUrl);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        webUrl = getIntent().getStringExtra(StaticData.WEB_URL);
        webView.clearCache(true);
        WebSettings settings = webView.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(
                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);// 打开本地缓存提供JS调用,至关重要
        //theme-dark
        //theme-light
        userAgent = settings.getUserAgentString();
        if (isNightMode(this)) {
            settings.setUserAgentString(userAgent + " " + StaticData.THEME_DARK);
        } else {
            settings.setUserAgentString(userAgent + " " + StaticData.THEME_LIGHT);
        }
        //设置是否支持缩放
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);

        //设置是否显示缩放按钮
        settings.setDisplayZoomControls(false);

        //设置自适应屏幕宽度
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                getWebTitle();
            }

        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                // TODO Auto-generated method stub
                // handler.cancel();// Android默认的处理方式
                handler.proceed();// 接受所有网站的证书
                // handleMessage(Message msg);// 进行其他处理
            }
        });

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
                // TODO: 2017-5-6 处理下载事件
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
        webView.loadUrl(webUrl);
    }

    private void getWebTitle() {
        WebBackForwardList forwardList = webView.copyBackForwardList();
        WebHistoryItem item = forwardList.getCurrentItem();
        if (item != null) {
            title.setText(item.getTitle());
        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
