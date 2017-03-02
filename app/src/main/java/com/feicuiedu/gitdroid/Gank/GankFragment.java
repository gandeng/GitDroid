package com.feicuiedu.gitdroid.Gank;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.feicuiedu.gitdroid.Gank.Modle.GankItem;
import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.commons.ActivityUtils;
import com.hannesdorfmann.mosby.mvp.MvpFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/5.
 */
public class GankFragment extends MvpFragment<GankView,GankPresenter> implements GankView {
    private ActivityUtils activityUtils;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.emptyView)
    FrameLayout frameLayout;
    @BindView(R.id.btn_Filter)
    ImageButton button;
    @BindView(R.id.content)
    ListView listView;

    private Date date;
    private SimpleDateFormat simpleDateFormat;
    private Calendar calendar;
    private GankAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils=new ActivityUtils(this);
        calendar=Calendar.getInstance(Locale.CANADA);
        date=new Date(System.currentTimeMillis());
    }

    @Override
    public GankPresenter createPresenter() {
        return new GankPresenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        tvDate.setText(simpleDateFormat.format(date));
        adapter=new GankAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(clickListener);
    }
    private ListView.OnItemClickListener clickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            GankItem gankItem=adapter.getItem(i);
            activityUtils.startBrowser(gankItem.getUrl());
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gank,container,false);
    }

    @Override
    public void setData(List<GankItem> list) {

    }

    @Override
    public void hideEmptyView() {
        frameLayout.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyView() {
        frameLayout.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        YoYo.with(Techniques.FadeIn).duration(500).playOn(frameLayout);
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);

    }
}
