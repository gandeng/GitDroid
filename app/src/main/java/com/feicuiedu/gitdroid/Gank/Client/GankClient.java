package com.feicuiedu.gitdroid.Gank.Client;

import com.feicuiedu.gitdroid.Gank.Modle.GankResult;
import com.feicuiedu.gitdroid.commons.LoggingInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2016/8/5.
 */
public class GankClient implements GankAPI {
    private static GankClient sClient;

    public static GankClient getInstance() {
        if (sClient == null) {
            sClient = new GankClient();
        }
        return sClient;
    }

    private GankAPI gankAPI;

    private GankClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        gankAPI = retrofit.create(GankAPI.class);

    }

    @Override
    public Call<GankResult> getGank(@Path("year") int year, @Path("month") int month, @Path("day") int day) {
        return gankAPI.getGank(year,month,day);
    }
}
