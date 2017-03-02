package com.feicuiedu.gitdroid.Gank;

import com.feicuiedu.gitdroid.Gank.Modle.GankItem;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

/**
 * Created by Administrator on 2016/8/5.
 */
public interface GankView extends MvpView {

    void setData(List<GankItem> list);
    void hideEmptyView();
    void showEmptyView();
    void showMessage(String msg);
}
