package com.feicuiedu.gitdroid.Login;

import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.feicuiedu.gitdroid.NetClient.GitHubApi;
import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.commons.ActivityUtils;
import com.feicuiedu.gitdroid.commons.LogUtils;
import com.feicuiedu.gitdroid.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Administrator on 2016/7/29.
 */
public class LoginActivity extends AppCompatActivity implements LoginView{
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.gifImageView)
    GifImageView gifImageView;
    private ActivityUtils activityUtils;
    private LoginPresenter presenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        activityUtils=new ActivityUtils(this);
        ButterKnife.bind(this);
        presenter=new LoginPresenter(this);
        initWebView();

    }

    private void initWebView() {
        // 删除所有的Cookie,主要为了清除以前的登陆记录
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        // 授权登陆URL
        webView.loadUrl(GitHubApi.AUTH_RUL);
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);
        // 主要为了监听进度
        webView.setWebChromeClient(webChromeClient);
        // 监听webview(url会刷新的)
        webView.setWebViewClient(webViewClient);
    }
    private WebChromeClient webChromeClient = new WebChromeClient(){
        @Override public void onProgressChanged(WebView view, int newProgress) {
            if(newProgress >= 100){
                LogUtils.e("aaaaaaaaaaaaaaaaaaa");
                gifImageView.setVisibility(View.GONE);

            }
        }
    };
    private WebViewClient webViewClient = new WebViewClient() {
        // 每当webview"刷新"时,此方法将触发 (密码输错了时！输对了时！等等情况web页面都会刷新变化的)
        @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 检测是不是我们的CALL_BACK
            LogUtils.e("bbbbbbbbbbbbbbbbb");
            Uri uri = Uri.parse(url);
            LogUtils.e(GitHubApi.CALL_BACK);
            LogUtils.e(uri.getScheme());
            if (GitHubApi.CALL_BACK.equals(uri.getScheme())) {
                // 获取code
                LogUtils.e("ccccccccccccccccccc");
                String code = uri.getQueryParameter("code");
                // 用code做登陆业务工作
                presenter.login(code);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    };


    @Override
    public void showProgress() {
        gifImageView.setVisibility(View.VISIBLE);

    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);

    }

    @Override
    public void resetWeb() {
        initWebView();
    }

    @Override
    public void navigateToMain() {
        activityUtils.startActivity(MainActivity.class);
        finish();

    }
}
