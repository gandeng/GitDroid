package com.feicuiedu.gitdroid.hotrepo.repolist.view;

import com.feicuiedu.gitdroid.hotrepo.repolist.modle.Repo;

import java.util.List;

/**
 * 作者：yuanchao on 2016/7/28 0028 10:14
 * 邮箱：yuanchao@feicuiedu.com
 */
public interface RepoListPtrView {
    void showContentView();
    void showErrorView(String errorMsg);
    void showEmptyView();
    void showMessage(String msg);
    void stopRefresh();
    void refreshData(List<Repo> data);
}
