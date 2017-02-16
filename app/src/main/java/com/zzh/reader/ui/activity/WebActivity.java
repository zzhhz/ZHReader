package com.zzh.reader.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zzh.reader.R;
import com.zzh.reader.base.BaseReaderActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by ZZH on 17/2/16
 *
 * @Date: 17/2/16 14:46
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description: 打开网页
 */
public class WebActivity extends BaseReaderActivity {

    @BindView(R.id.webView)
    protected WebView mWebView;
    public static final String OPEN_URL = "OPEN_URL";
    public static final int REFRESH = 0;

    @BindView(R.id.activity_web)
    protected SwipeRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.setClickable(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.color_refresh);
        mRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        mRefreshLayout.setRefreshing(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                    if (mRefreshLayout.isRefreshing()){
                        mRefreshLayout.setRefreshing(false);
                    }

            }
        });
    }

    @Override
    protected void initData() {

        Intent intent = getIntent();
        if (intent == null)
        {
            finish();
        }
        String url = intent.getStringExtra(OPEN_URL);
        mWebView.loadUrl(url);

    }

    @Override
    protected void initSetListener() {

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.sendEmptyMessageDelayed(REFRESH, 3000);
            }
        });
    }

    @Override
    protected void handlerMessage(Message message) {
        switch (message.what){
            case REFRESH:
                if (mRefreshLayout.isRefreshing())
                    mRefreshLayout.setRefreshing(false);
                break;
        }
    }

    @Override
    public void onClick(View v) {

    }
}
