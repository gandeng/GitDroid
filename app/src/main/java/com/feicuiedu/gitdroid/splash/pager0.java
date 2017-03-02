package com.feicuiedu.gitdroid.splash;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.feicuiedu.gitdroid.R;

/**
 * Created by Administrator on 2016/7/26.
 */
public class pager0 extends FrameLayout{

    public pager0(Context context) {
        this(context,null);

    }

    public pager0(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    public pager0(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    public void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.content_pager_0,this,true);
    }
}
