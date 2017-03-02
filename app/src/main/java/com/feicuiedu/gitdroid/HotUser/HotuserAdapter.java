package com.feicuiedu.gitdroid.HotUser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.hotrepo.repolist.modle.Repo;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/2.
 */
public class HotuserAdapter extends BaseAdapter {
    private  ArrayList<HotUser> datas;
    public HotuserAdapter(){
        datas=new ArrayList<HotUser>();
    }


    public void addAll(Collection<HotUser> HotUsers){
        datas.addAll(HotUsers);
        notifyDataSetChanged();
    }

    public void clear(){
        datas.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public HotUser getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.layout_item_hotuser, viewGroup, false);
            view.setTag(new ViewHolder(view));
        }
        ViewHolder viewHolder= (ViewHolder) view.getTag();
        HotUser hotUser=getItem(i);// 当前item选的"数据"
        viewHolder.tvuserName.setText(hotUser.getLogin());
        viewHolder.tvuserInfo.setText("主页："+hotUser.getUrl());
        ImageLoader.getInstance().displayImage(hotUser.getAvatar(), viewHolder.ivuserIcon);
        return view;
    }

    static class ViewHolder{
        @BindView(R.id.ivuserIcon)
        ImageView ivuserIcon;
        @BindView(R.id.tvuserName)
        TextView tvuserName;
        @BindView(R.id.tvuserInfo)
        TextView tvuserInfo;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
