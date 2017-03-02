package com.feicuiedu.gitdroid.HotUser;

import com.feicuiedu.gitdroid.hotrepo.repolist.modle.Repo;

import java.util.List;

/**
 * Created by Administrator on 2016/8/2.
 */
public interface UserListLoadMoreView {
    void showLoadMoreLoading();

    void hideLoadMore();

    void showLoadMoreErro(String erroMsg);

    void addMoreData(List<HotUser> datas);

}
