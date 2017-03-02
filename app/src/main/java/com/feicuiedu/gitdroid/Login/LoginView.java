package com.feicuiedu.gitdroid.Login;

/**
 * Created by Administrator on 2016/8/1.
 */
public interface LoginView {
    // 显示进度
    void showProgress();

    void showMessage(String msg);

    // 重置WebView
    void resetWeb();

    // 导航切换至Main页面
    void navigateToMain();
}
