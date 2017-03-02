package com.feicuiedu.gitdroid.splash;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/7/26.
 */
public class SplashPagerAdapter  extends PagerAdapter{
        private View[] views;
    public SplashPagerAdapter(Context context){
        views=new View[]{
                new pager0(context),
                new pager1(context),
                new pager2(context)

        };
    }
    public View getView(int position){
        return views[position];
    }
    @Override
    public int getCount() {
        return views.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views[position]);
        return views[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views[position]);
    }
}
