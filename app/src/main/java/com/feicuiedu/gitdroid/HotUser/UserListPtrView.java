package com.feicuiedu.gitdroid.HotUser;

import com.feicuiedu.gitdroid.hotrepo.repolist.modle.Repo;

import java.util.List;

/**
 * Created by Administrator on 2016/8/2.
 */
public interface UserListPtrView {
    void showContentView();
    void showErrorView(String errorMsg);
    void showEmptyView();
    void showMessage(String msg);
    void stopRefresh();
    void refreshData(List<HotUser> data);
}
