package com.feicuiedu.gitdroid.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.feicuiedu.gitdroid.Login.LoginActivity;
import com.feicuiedu.gitdroid.MainActivity;
import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.commons.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/26.
 */
public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.btnEnter)
    Button btnEnter;
    private ActivityUtils activityUtils;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils=new ActivityUtils(this);
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
    }
    @OnClick(R.id.btnEnter)
    public void click(){
        activityUtils.startActivity(MainActivity.class);
        finish();
    }
    @OnClick(R.id.btnLogin)
    public void login(){
        activityUtils.startActivity(LoginActivity.class);
        finish();
    }
}
