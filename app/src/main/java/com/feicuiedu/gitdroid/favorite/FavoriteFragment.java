package com.feicuiedu.gitdroid.favorite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.commons.ActivityUtils;
import com.feicuiedu.gitdroid.commons.LogUtils;
import com.feicuiedu.gitdroid.favorite.Dao.DBHelp;
import com.feicuiedu.gitdroid.favorite.Dao.LoadRepoDao;
import com.feicuiedu.gitdroid.favorite.Dao.RepoGroupDao;
import com.feicuiedu.gitdroid.favorite.model.LoadRepo;
import com.feicuiedu.gitdroid.favorite.model.RepoGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/4.
 */
public class FavoriteFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {
    @BindView(R.id.tvGroupType)
    TextView tvGroupType;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.btnFilter)
    ImageButton btnFilter;
    private ActivityUtils activityUtils;
    private FavoriteFragmentAdapter adapter;
    private RepoGroupDao repoGroupDao;
    private LoadRepoDao loadRepoDao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        repoGroupDao = new RepoGroupDao(DBHelp.getInstance(getContext()));
        loadRepoDao = new LoadRepoDao(DBHelp.getInstance(getContext()));

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        adapter = new FavoriteFragmentAdapter();
        listView.setAdapter(adapter);
        setData(R.id.repo_group_all);
        //注册上下文菜单
        registerForContextMenu(listView);
    }


    @OnClick(R.id.btnFilter)
    public void onclick(View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.setOnMenuItemClickListener(this);
        // menu项(注意，上面只有全部和未分类)
        popupMenu.inflate(R.menu.menu_popup_repo_groups);
        // 向menu上添加我们自己的各类别
        // 1. 拿到MENU
        Menu menu = popupMenu.getMenu();
        //2. 拿到数据(DAO)
        List<RepoGroup> repoGroups = repoGroupDao.queryForAll();
        for (RepoGroup repoGroup : repoGroups) {
            menu.add(Menu.NONE, repoGroup.getId(), Menu.NONE, repoGroup.getName());
        }
        //展示出来
        popupMenu.show();
    }

    private void setData(int groupId) {
        switch (groupId) {
            case R.id.repo_group_all:
                adapter.setData(loadRepoDao.queryForAll());
                break;
            case R.id.repo_group_no:
                adapter.setData(loadRepoDao.queryForNoGroup());
                break;
            default:
                adapter.setData(loadRepoDao.queryForGroupId(groupId));
                break;
        }
    }

    //当前仓库的类别
    private int currentLoadRepoID;

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        String title = item.getTitle().toString();
        tvGroupType.setText(title);
        currentLoadRepoID = item.getItemId();
        setData(currentLoadRepoID);
        return true;
    }

    //当前操作的仓库
    private LoadRepo currentLoadRepo;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.listView) {
            // 得到当前ListView的ContextMenu，选中时选择的位置
            AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
            int position = adapterContextMenuInfo.position;
            currentLoadRepo = adapter.getItem(position);
            MenuInflater menuInflater = getActivity().getMenuInflater();
            menuInflater.inflate(R.menu.menu_context_favorite, menu);
            // 拿到子菜单,添加内容
            SubMenu subMenu = menu.findItem(R.id.sub_menu_move).getSubMenu();
            List<RepoGroup> repoGroups = repoGroupDao.queryForAll();
            // 都添加到menu_group_move这个组上
            for (RepoGroup repoGroup : repoGroups) {
                subMenu.add(R.id.menu_group_move, repoGroup.getId(), Menu.NONE, repoGroup.getName());
            }

        }
    }

    @Override public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        // 删除
        if (id == R.id.delete) {
            // 删除当前选择的(长按listview某一个item后，将弹出contextmenu)本地仓库
            loadRepoDao.delete(currentLoadRepo);
            // 重置当前选择的分类下的本地仓库列表
            setData(currentLoadRepoID);
            return true;
        }
        // 移动至
        int groupId = item.getGroupId();
        if (groupId == R.id.menu_group_move) {
            // 未分类
            if (id == R.id.repo_group_no) {
                // 将当前选择的本地仓库类别重置为null(无类别)
                currentLoadRepo.setRepoGroup(null);
            }
            // 其它分类 id= 1,2,3,4,5,6
            else {
                // 得到“其它分类”的类别对象,将当前选择的本地仓库类别重置为当前类别
                currentLoadRepo.setRepoGroup(repoGroupDao.queryForId(id));
            }
            // 更新数据库数据
            loadRepoDao.createOrUpdate(currentLoadRepo);
            setData(currentLoadRepoID);
            return true;
        }
        return super.onContextItemSelected(item);
    }

//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.delete) {
//            //删除操作
//            loadRepoDao.delete(currentLoadRepo);
//            setData(currentLoadRepoID);
//            return true;
//        }
//        int groupid = item.getGroupId();
//        LogUtils.e("getGroupId："+groupid);
//        if (groupid == R.id.menu_group_move) {
//
//            if (id == R.id.repo_group_no) {
//                //将当前操作的类别重置为空
//                LogUtils.e("将当前操作的类别重置为空：bbbbbbbbbbbbb");
//                currentLoadRepo.setRepoGroup(null);
//            } else {
//                //重置当前仓库的类别
//                LogUtils.e("重置当前仓库的类别：aaaaaaaaaaaaaaaaa");
//                currentLoadRepo.setRepoGroup(repoGroupDao.queryForId(id));
//                LogUtils.e("cccccccccccccccc");
//            }
//            //更新数据库中的仓库
//            loadRepoDao.createOrUpdate(currentLoadRepo);
//            setData(currentLoadRepoID);
//            return true;
//        }
//        return super.onContextItemSelected(item);
//    }
}
