package com.feicuiedu.gitdroid;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Created by Administrator on 2016/8/1.
 */
public class GitDroidApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DisplayImageOptions options=new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_avatar)
                .showImageOnFail(R.drawable.ic_avatar)
                .showImageOnLoading(R.drawable.ic_avatar)
                .cacheInMemory(true)//打开内存缓存
                .cacheOnDisk(true)//打开硬盘缓存
                .resetViewBeforeLoading(true)//在图片加载前清除数据
                .displayer(new RoundedBitmapDisplayer(getResources().getDimensionPixelOffset(R.dimen.dp_10)))
                .build();
        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration.Builder(this)
                .memoryCacheSize(5*1024*1024)
                .defaultDisplayImageOptions(options)
                .build();
        //初始化ImageLoad
        ImageLoader.getInstance().init(configuration);
    }
}
