package com.feicuiedu.gitdroid;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.feicuiedu.gitdroid.Gank.GankFragment;
import com.feicuiedu.gitdroid.HotUser.HotUserFragment;
import com.feicuiedu.gitdroid.Login.CurrentUser;
import com.feicuiedu.gitdroid.Login.LoginActivity;
import com.feicuiedu.gitdroid.commons.ActivityUtils;
import com.feicuiedu.gitdroid.favorite.FavoriteFragment;
import com.feicuiedu.gitdroid.hotrepo.HotRepoFragment;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout; // 抽屉(包含内容+侧滑菜单)
    @BindView(R.id.navigationView)
    NavigationView navigationView; // 侧滑菜单视图
    private HotRepoFragment hotRepoFragment;
    private HotUserFragment hotUserFragment;
    private FavoriteFragment favoriteFragment;
    private GankFragment gankFragment;
    private Button btn_login;
    private ImageView ivIcon;
    private ActivityUtils activityUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置当前视图(也就是说，更改了当前视图内容,将导至onContentChanged方法触发)
        setContentView(R.layout.activity_main);

    }


    @Override public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        activityUtils=new ActivityUtils(this);
        // ActionBar处理
        setSupportActionBar(toolbar);
        // 设置navigationView的监听器
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(// 构建抽屉的监听
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();// 根据drawerlayout同步其当前状态
        // 设置抽屉监听
        drawerLayout.addDrawerListener(toggle);
        btn_login=ButterKnife.findById(navigationView.getHeaderView(0),R.id.Log_btnLogin);
        ivIcon=ButterKnife.findById(navigationView.getHeaderView(0),R.id.log_ivIcon);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityUtils.startActivity(LoginActivity.class);
                finish();
            }
        });
        // 默认显示的是热门仓库fragment
        hotRepoFragment = new HotRepoFragment();
        replaceFragment(hotRepoFragment);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(CurrentUser.isEmpty()){
            btn_login.setText(R.string.login_github);
            return;
        }
        btn_login.setText(R.string.switch_account);
        getSupportActionBar().setTitle(CurrentUser.getUser().getName());
        String ivUrl=CurrentUser.getUser().getAvatar();
        ImageLoader.getInstance().displayImage(ivUrl,ivIcon);
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // 热门仓库
            case R.id.github_hot_repo:
                // 默认显示的是热门仓库fragment
                hotRepoFragment = new HotRepoFragment();
                replaceFragment(hotRepoFragment);
                drawerLayout.closeDrawer(navigationView);
                break;
            case R.id.github_hot_coder:
                if (hotUserFragment==null) {
                    hotUserFragment = new HotUserFragment();
                }
                replaceFragment(hotUserFragment);
                drawerLayout.closeDrawer(navigationView);
                break;
            case R.id.arsenal_my_repo:
                if (favoriteFragment==null) {
                    favoriteFragment = new FavoriteFragment();
                }
                replaceFragment(favoriteFragment);
                drawerLayout.closeDrawer(navigationView);
                break;
            case R.id.tips_daily:
                if (gankFragment==null) {
                    gankFragment = new GankFragment();
                }
                replaceFragment(gankFragment);
                drawerLayout.closeDrawer(navigationView);
                break;


        }
        // 返回true，代表将该菜单项变为checked状态  tips_daily
        return true;
    }
}
