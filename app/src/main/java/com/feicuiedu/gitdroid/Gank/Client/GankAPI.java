package com.feicuiedu.gitdroid.Gank.Client;

import com.feicuiedu.gitdroid.Gank.Modle.GankResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2016/8/5.
 */
public interface GankAPI {
    String ENDPOINT = "http://gank.io/api/";
    @GET("day/{year}/{month}/{day}")
    Call<GankResult> getGank(
            @Path("year") int year,
            @Path("month") int month,
            @Path("day") int day
    );
}
