package com.feicuiedu.gitdroid.HotUser;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.commons.ActivityUtils;
import com.feicuiedu.gitdroid.commons.LogUtils;
import com.feicuiedu.gitdroid.compontents.FooterView;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by Administrator on 2016/8/2.
 */
public class HotUserFragment extends Fragment implements UserListView{
    @BindView(R.id.user_lvRepos)
    ListView listView;
    @BindView(R.id.user_ptrClassicFrameLayout)
    PtrClassicFrameLayout ptrFrameLayout;
    @BindView(R.id.user_emptyView)
    TextView user_emptyView;
    @BindView(R.id.user_errorView)
    TextView user_errorView;
    private ActivityUtils activityUtils;
    private HotuserAdapter adapter;
    private FooterView footerView;
    private HotuserListPresenter presenter;
    private ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.hotuser_layout,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        activityUtils = new ActivityUtils(this);
        presenter=new HotuserListPresenter(this);
        adapter = new HotuserAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String  url=adapter.getItem(i).getUrl();
                LogUtils.e(url);
                String name =adapter.getItem(i).getLogin();
                LogUtils.e(name);
                HotUserActivity.open(getContext(),url,name);
            }
        });
        presenter.refresh();
        // 初始下拉刷新
        initPullToRefresh();
        // 初始上拉加载更多
        initLoadMoreScroll();
    }


    private void initLoadMoreScroll() {
        footerView = new FooterView(getContext());
        Mugen.with(listView, new MugenCallbacks() {
            // listview，滚动到底部,将触发此方法
            @Override public void onLoadMore() {
                // 执行上拉加载数据的业务处理
                presenter.loadMore();
            }

            // 是否正在加载中
            // 其内部将用此方法来判断是否触发onLoadMore
            @Override public boolean isLoading() {
                return listView.getFooterViewsCount() > 0 && footerView.isLoading();
            }

            // 是否已加载完成所有数据
            // 其内部将用此方法来判断是否触发onLoadMore
            @Override public boolean hasLoadedAllItems() {
                return listView.getFooterViewsCount() > 0 && footerView.isComplete();
            }
        }).start();
    }

    private void initPullToRefresh() {
        // 使用当前对象做为key，来记录上一次的刷新时间,如果两次下拉太近，将不会触发新刷新
        ptrFrameLayout.setLastUpdateTimeRelateObject(this);
        // 关闭header所用时长
        ptrFrameLayout.setDurationToCloseHeader(1500);
        // 下拉刷新监听处理
        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            // 当你"下拉时",将触发此方法
            @Override public void onRefreshBegin(PtrFrameLayout frame) {
                // 去做数据的加载，做具体的业务
                // 也就是说，你要抛开视图，到后台线程去做你的业务处理(数据刷新加载)
                presenter.refresh();
            }
        });
        // 以下代码（只是修改了header样式）
        StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.initWithString("I LIKE " + " JAVA");
        header.setPadding(0, 60, 0, 60);
        // 修改Ptr的HeaderView效果
        ptrFrameLayout.setHeaderView(header);
        ptrFrameLayout.addPtrUIHandler(header);
        ptrFrameLayout.setBackgroundResource(R.color.colorRefresh);
    }

    //上啦加载更多数据--------------------------------------------
    @Override
    public void showLoadMoreLoading() {
        if (listView.getFooterViewsCount() == 0) {
            listView.addFooterView(footerView);
        }
        footerView.showLoading();

    }

    @Override
    public void hideLoadMore() {
        listView.removeFooterView(footerView);

    }

    @Override
    public void showLoadMoreErro(String erroMsg) {
        if (listView.getFooterViewsCount() == 0) {
            listView.addFooterView(footerView);
        }
        footerView.showError(erroMsg);
    }

    @Override
    public void addMoreData(List<HotUser> datas) {
        adapter.addAll(datas);

    }
//下拉刷新数据--------------------------------------------
    @Override
    public void showContentView() {
        ptrFrameLayout.setVisibility(View.VISIBLE);
        user_emptyView.setVisibility(View.GONE);
        user_errorView.setVisibility(View.GONE);

    }

    @Override
    public void showErrorView(String errorMsg) {
        ptrFrameLayout.setVisibility(View.GONE);
        user_emptyView.setVisibility(View.GONE);
        user_errorView.setVisibility(View.VISIBLE);

    }

    @Override
    public void showEmptyView() {
        ptrFrameLayout.setVisibility(View.GONE);
        user_emptyView.setVisibility(View.VISIBLE);
        user_errorView.setVisibility(View.GONE);

    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void stopRefresh() {
        ptrFrameLayout.refreshComplete();
    }

    @Override
    public void refreshData(List<HotUser> data) {
        adapter.clear();
        adapter.addAll(data);
    }
}
