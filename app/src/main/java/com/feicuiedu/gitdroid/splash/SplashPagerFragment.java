package com.feicuiedu.gitdroid.splash;

import android.animation.ArgbEvaluator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.feicuiedu.gitdroid.R;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;


/**
 * Created by Administrator on 2016/7/26.
 */
public class SplashPagerFragment extends Fragment{
    @BindView(R.id.content) FrameLayout frameLayout;
    @BindView(R.id.layoutPhone) FrameLayout phoneFrame;
    @BindView(R.id.ivPhoneFont) ImageView ivPhoneFont;
    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.indicator) CircleIndicator indicator;
    private SplashPagerAdapter adapter;
    @BindColor(R.color.colorRed) int colorRed;
    @BindColor(R.color.colorYellow) int colorYellow;
    @BindColor(R.color.colorGreen) int colorGreen;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_pager,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        adapter=new SplashPagerAdapter(getContext());
        //设置适配器
        viewPager.setAdapter(adapter);
        //设置指示器索要适配的viewPager
        indicator.setViewPager(viewPager);
        viewPager.addOnPageChangeListener(ViewColorListener);
        viewPager.addOnPageChangeListener(phoneChageListener);

    }
    private ViewPager.OnPageChangeListener ViewColorListener =new ViewPager.OnPageChangeListener(){
        ArgbEvaluator argbEvaluator=new ArgbEvaluator();
        //滑动式
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if(position==0) {
                int color = (int) argbEvaluator.evaluate(positionOffset, colorGreen, colorRed);
                frameLayout.setBackgroundColor(color);
            }
            if(position==1) {
                int color = (int) argbEvaluator.evaluate(positionOffset, colorRed, colorYellow);
                frameLayout.setBackgroundColor(color);
            }
        }
        //滑动完成
        @Override
        public void onPageSelected(int position) {

        }
        //滑动状态改变
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private ViewPager.OnPageChangeListener phoneChageListener =new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if(position==0) {
                float scale = 0.3f + positionOffset*0.7f;
                phoneFrame.setScaleX(scale);
                phoneFrame.setScaleY(scale);
                int scroll= (int) ((positionOffset-1)*360);
                phoneFrame.setTranslationX(scroll);
            }
            if(position==1){
                phoneFrame.setTranslationX(-positionOffsetPixels);
            }
        }
        //滑动完成
        @Override
        public void onPageSelected(int position) {
            if(position==2){
               pager2 pager2= (pager2) adapter.getView(position);
                pager2.showAnimation();
            }

        }
        //滑动状态改变
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
