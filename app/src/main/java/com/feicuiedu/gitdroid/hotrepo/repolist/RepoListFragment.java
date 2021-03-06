package com.feicuiedu.gitdroid.hotrepo.repolist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.commons.ActivityUtils;
import com.feicuiedu.gitdroid.commons.LogUtils;
import com.feicuiedu.gitdroid.compontents.FooterView;
import com.feicuiedu.gitdroid.favorite.Dao.DBHelp;
import com.feicuiedu.gitdroid.favorite.Dao.LoadRepoDao;
import com.feicuiedu.gitdroid.favorite.Dao.RepoGroupDao;
import com.feicuiedu.gitdroid.favorite.model.LoadRepo;
import com.feicuiedu.gitdroid.favorite.model.RepoConverter;
import com.feicuiedu.gitdroid.favorite.model.RepoGroup;
import com.feicuiedu.gitdroid.hotrepo.Language;
import com.feicuiedu.gitdroid.hotrepo.repolist.modle.Repo;
import com.feicuiedu.gitdroid.hotrepo.repolist.view.RepoListView;
import com.feicuiedu.gitdroid.repoinfo.RepoInfoActivity;
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
 * 仓库列表
 * <p/>
 * 将显示当前语言的所有仓库，有下拉刷新，上拉加载更多的效果
 * <p/>
 * 作者：yuanchao on 2016/7/27 0027 14:37
 * 邮箱：yuanchao@feicuiedu.com
 */
public class RepoListFragment extends Fragment
        implements RepoListView {

    private static final String KEY_LANGUAGE = "key_language";

    public static RepoListFragment getInstance(Language language){
        RepoListFragment fragment = new RepoListFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_LANGUAGE,language);
        fragment.setArguments(args);
        return fragment;
    }

    private Language getLanguage() {
        return (Language)getArguments().getSerializable(KEY_LANGUAGE);
    }

    @BindView(R.id.lvRepos) ListView listView;
    @BindView(R.id.repo_emptyView) TextView emptyView;
    @BindView(R.id.repo_errorView) TextView errorView;
    @BindView(R.id.ptrClassicFrameLayout) PtrClassicFrameLayout ptrFrameLayout;

    private RepoListAdapter adapter;
    private RepoGroupDao repoGroupDao;
    private LoadRepoDao loadRepoDao;

    // 用来做当前页面业务逻辑及视图更新的
    private RepoListPresenter presenter;

    private FooterView footerView; // 上拉加载更多的视图
    private ActivityUtils activityUtils;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        repoGroupDao = new RepoGroupDao(DBHelp.getInstance(getContext()));
        loadRepoDao = new LoadRepoDao(DBHelp.getInstance(getContext()));
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repo_list, container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        activityUtils = new ActivityUtils(this);
        presenter = new RepoListPresenter(this, getLanguage());

        adapter = new RepoListAdapter();
        listView.setAdapter(adapter);
        // 按下某个仓库后，进入此仓库详情
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Repo repo = adapter.getItem(position);
                RepoInfoActivity.open(getContext(),repo);
            }
        });
        registerForContextMenu(listView);

        // 初始下拉刷新
        initPullToRefresh();
        // 初始上拉加载更多
        initLoadMoreScroll();
        // 如果当前页面没有数据，开始自动刷新
        if (adapter.getCount() == 0) {
            ptrFrameLayout.postDelayed(new Runnable() {
                @Override public void run() {
                    ptrFrameLayout.autoRefresh();
                }
            }, 200);
        }
    }
    private static Repo currentRepo;
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId()==R.id.lvRepos){
            AdapterView.AdapterContextMenuInfo adapterContextMenuInfo= (AdapterView.AdapterContextMenuInfo) menuInfo;
            int position =adapterContextMenuInfo.position;
            currentRepo=adapter.getItem(position);
            LogUtils.e(currentRepo.toString());
            MenuInflater menuInflater = getActivity().getMenuInflater();
            menuInflater.inflate(R.menu.menu_hot_groups, menu);
            // 拿到子菜单,添加内容
            SubMenu subMenu = menu.findItem(R.id.sub_menu_favorite).getSubMenu();
            List<RepoGroup> repoGroups = repoGroupDao.queryForAll();
            // 都添加到menu_group_move这个组上
            for (RepoGroup repoGroup : repoGroups) {
                subMenu.add(R.id.menu_group_favorite, repoGroup.getId(), Menu.NONE, repoGroup.getName());
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id =item.getItemId();
        int groupid=item.getGroupId();
        if(groupid==R.id.menu_group_favorite){
            RepoGroup repoGroup=repoGroupDao.queryForId(id);
           LoadRepo loadRepo= RepoConverter.convert(currentRepo,repoGroup);
            loadRepoDao.createOrUpdate(loadRepo);
            activityUtils.showToast("收藏成功！");

        }
        return super.onContextItemSelected(item);
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

    // 下拉刷新视图实现----------------------------------------
    @Override
    public void showContentView() {
        ptrFrameLayout.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void showErrorView(String errorMsg) {
        ptrFrameLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyView() {
        ptrFrameLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
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
    public void refreshData(List<Repo> datas) {
        adapter.clear();
        adapter.addAll(datas);
    }

    // 上拉加载更多视图实现----------------------------------------
    @Override public void showLoadMoreLoading() {
        if (listView.getFooterViewsCount() == 0) {
            listView.addFooterView(footerView);
        }
        footerView.showLoading();
    }

    @Override public void hideLoadMore() {
        listView.removeFooterView(footerView);
    }

    @Override public void showLoadMoreErro(String erroMsg) {
        if (listView.getFooterViewsCount() == 0) {
            listView.addFooterView(footerView);
        }
        footerView.showError(erroMsg);
    }

    @Override public void addMoreData(List<Repo> datas) {
        adapter.addAll(datas);
    }
}