package com.feicuiedu.gitdroid.Gank;

import com.feicuiedu.gitdroid.Gank.Client.GankClient;
import com.feicuiedu.gitdroid.Gank.Modle.GankItem;
import com.feicuiedu.gitdroid.Gank.Modle.GankResult;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/8/5.
 */
public class GankPresenter extends MvpBasePresenter<GankView> {
    private Call<GankResult> call;

    public GankPresenter(){

    }

    public void getGanks(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        int year =calendar.get(Calendar.YEAR);
        int month =calendar.get(Calendar.MONTH);
        int day =calendar.get(Calendar.DAY_OF_MONTH);
        call= GankClient.getInstance().getGank(year,month,day);
        call.enqueue(callback);
    }
    private Callback<GankResult> callback=new Callback<GankResult>() {
        @Override
        public void onResponse(Call<GankResult> call, Response<GankResult> response) {
            GankResult gankResult =response.body();
            if(gankResult==null){
                getView().showMessage("未知错误！");
                return;
            }
            if(gankResult.isError() || gankResult.getResults() ==null || gankResult.getResults().getAndroidItems() ==null){
                getView().showEmptyView();
                return;

            }

            List<GankItem> gankItems =gankResult.getResults().getAndroidItems();
            getView().hideEmptyView();
            getView().setData(gankItems);

        }

        @Override
        public void onFailure(Call<GankResult> call, Throwable t) {
            getView().showMessage("Error:"+t.getMessage());
        }
    };
}
