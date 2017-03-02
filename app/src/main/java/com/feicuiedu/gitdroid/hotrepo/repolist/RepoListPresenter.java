package com.feicuiedu.gitdroid.hotrepo.repolist;

import com.feicuiedu.gitdroid.NetClient.GitHubClient;
import com.feicuiedu.gitdroid.hotrepo.Language;
import com.feicuiedu.gitdroid.hotrepo.repolist.modle.Repo;
import com.feicuiedu.gitdroid.hotrepo.repolist.modle.RepoResult;
import com.feicuiedu.gitdroid.hotrepo.repolist.view.RepoListView;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：yuanchao on 2016/7/27 0027 17:30
 * 邮箱：yuanchao@feicuiedu.com
 */
public class RepoListPresenter {
    // 视图的接口
    private RepoListView repoListView;
    private int nextPage = 0;
    private Language language;

    private Call<RepoResult> repoCall;

    public RepoListPresenter(RepoListView repoListView, Language language) {
        this.repoListView = repoListView;
        this.language = language;
    }

    // 下拉刷新处理
    public void refresh() {
        // 隐藏loadmore
        repoListView.hideLoadMore();
        repoListView.showContentView();
        nextPage = 1; // 永远刷新最新数据
        repoCall = GitHubClient.getInstance().searchRepos(
                "language:" + language.getPath()
                , nextPage);
        repoCall.enqueue(repoCallback);
    }

    // 加载更多处理
    public void loadMore() {
        repoListView.showLoadMoreLoading();
        repoCall = GitHubClient.getInstance().searchRepos(
                "language:" + language.getPath()
                , nextPage);
        repoCall.enqueue(loadMoreCallback);
    }

    private final Callback<RepoResult> loadMoreCallback = new Callback<RepoResult>(){

        @Override public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
            repoListView.hideLoadMore();
            // 得到响应结果
            RepoResult repoResult = response.body();
            if (repoResult == null) {
                repoListView.showLoadMoreErro("结果为空!");
                return;
            }
            // 取出当前语言下的所有仓库
            List<Repo> repoList = repoResult.getRepoList();
            repoListView.addMoreData(repoList);
            nextPage++;
        }

        @Override public void onFailure(Call<RepoResult> call, Throwable t) {
            // 视图停止刷新
            repoListView.hideLoadMore();
            repoListView.showMessage("repoCallback onFailure" + t.getMessage());
        }
    };

    private final Callback<RepoResult> repoCallback = new Callback<RepoResult>() {
        @Override public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
            // 视图停止刷新
            repoListView.stopRefresh();
            // 得到响应结果
            RepoResult repoResult = response.body();
            if (repoResult == null) {
                repoListView.showErrorView("结果为空!");
                return;
            }
            // 当前搜索的语言,没有仓库
            if (repoResult.getTotalCount() <= 0) {
                repoListView.refreshData(null);
                repoListView.showEmptyView();
                return;
            }
            // 取出当前语言下的所有仓库
            List<Repo> repoList = repoResult.getRepoList();
            repoListView.refreshData(repoList);
            // 下拉刷新成功(1), 下一面则更新为2
            nextPage = 2;
        }

        @Override public void onFailure(Call<RepoResult> call, Throwable t) {
            // 视图停止刷新
            repoListView.stopRefresh();
            repoListView.showMessage("repoCallback onFailure" + t.getMessage());
        }
    };
}
