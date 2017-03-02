package com.feicuiedu.gitdroid.splash;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.feicuiedu.gitdroid.R;

/**
 * Created by Administrator on 2016/7/26.
 */
public class pager1 extends FrameLayout {

    public pager1(Context context) {
        this(context,null);
    }

    public pager1(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public pager1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.content_pager_1,this,true);
    }
}
