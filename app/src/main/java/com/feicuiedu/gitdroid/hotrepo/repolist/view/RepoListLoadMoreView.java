package com.feicuiedu.gitdroid.hotrepo.repolist.view;

import com.feicuiedu.gitdroid.hotrepo.repolist.modle.Repo;

import java.util.List;

/**
 * 加载更多的视图抽象
 * 作者：yuanchao on 2016/7/28 0028 11:34
 * 邮箱：yuanchao@feicuiedu.com
 */
public interface RepoListLoadMoreView {
    void showLoadMoreLoading();

    void hideLoadMore();

    void showLoadMoreErro(String erroMsg);

    void addMoreData(List<Repo> datas);
}