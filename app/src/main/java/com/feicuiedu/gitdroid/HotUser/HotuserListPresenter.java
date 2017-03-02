package com.feicuiedu.gitdroid.HotUser;

import com.feicuiedu.gitdroid.NetClient.GitHubClient;
import com.feicuiedu.gitdroid.commons.LogUtils;
import com.feicuiedu.gitdroid.hotrepo.repolist.modle.Repo;
import com.feicuiedu.gitdroid.hotrepo.repolist.modle.RepoResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/8/2.
 */
public class HotuserListPresenter {
    private UserListView userListView;
    private int nextPage=0;
    private Call<HotUserResult> usercall;
    public HotuserListPresenter(UserListView userListView){
        this.userListView=userListView;
    }

    public void loadMore() {
        userListView.showLoadMoreLoading();
        if (usercall!=null)usercall.cancel();
        usercall= GitHubClient.getInstance().getUser("followers:>1000",nextPage);
        usercall.enqueue(userLoadcall);
    }

    public void refresh() {
        userListView.hideLoadMore();
        userListView.showContentView();
        nextPage=1;//永远加载第一页
        if (usercall!=null)usercall.cancel();
        usercall= GitHubClient.getInstance().getUser("followers:>1000",nextPage);
        usercall.enqueue(usercallBack);
    }
    private Callback<HotUserResult> userLoadcall =new Callback<HotUserResult>() {
        @Override
        public void onResponse(Call<HotUserResult> call, Response<HotUserResult> response) {
            userListView.hideLoadMore();
            // 得到响应结果
            HotUserResult hotUserResult = response.body();
            if (hotUserResult == null) {
                userListView.showLoadMoreErro("结果为空!");
                return;
            }
            // 取出当前语言下的所有仓库
            List<HotUser> userList = hotUserResult.getUserList();
            userListView.addMoreData(userList);
            nextPage++;
        }

        @Override
        public void onFailure(Call<HotUserResult> call, Throwable t) {
            // 视图停止刷新
            userListView.hideLoadMore();
            userListView.showMessage("repoCallback onFailure" + t.getMessage());

        }
    };
    private Callback<HotUserResult> usercallBack=new Callback<HotUserResult>() {
        @Override
        public void onResponse(Call<HotUserResult> call, Response<HotUserResult> response) {
            userListView.stopRefresh();
            HotUserResult result=response.body();
            LogUtils.e(result+"");
            if (result==null){
                userListView.showMessage("结果为空");
                return;
            }
            if (result.getTotalCount()<=0){
                userListView.refreshData(null);
                userListView.showEmptyView();
                return;
            }
            List<HotUser> lists=result.getUserList();

            userListView.refreshData(lists);
            // 下拉刷新成功(1), 下一面则更新为2
            nextPage = 2;
        }

        @Override
        public void onFailure(Call<HotUserResult> call, Throwable t) {
            // 视图停止刷新
            userListView.stopRefresh();
            userListView.showMessage("repoCallback onFailure" + t.getMessage());
        }
    };
}
