package com.feicuiedu.gitdroid.HotUser;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.commons.LogUtils;
import com.feicuiedu.gitdroid.hotrepo.repolist.modle.Repo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HotUserActivity extends AppCompatActivity {
    @BindView(R.id.user_webView)
    WebView webView;
    @BindView(R.id.user_toolbar)
    Toolbar toolbar;
    @BindView(R.id.user_progressBar)
    ProgressBar progressBar;
    private static final String KEY_USERURL = "key_userurl";
    private static final String KEY_USERNAME = "key_username";
    private String name;
    private String Url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_user);
    }
    public static void open(Context context, @NonNull String htmlUrl,@NonNull String name) {
        Intent intent = new Intent(context, HotUserActivity.class);
        intent.putExtra(KEY_USERURL, htmlUrl);
        intent.putExtra(KEY_USERNAME,name);
        context.startActivity(intent);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        name=getIntent().getStringExtra(KEY_USERNAME);
        LogUtils.e(name);
        Url=getIntent().getStringExtra(KEY_USERURL);
        LogUtils.e(Url);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(name);

        initWebView();
    }

    private void initWebView() {
        WebSettings webSettings = webView.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        //加载需要显示的网页
        webView.loadUrl(Url);
        //设置Web视图
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
