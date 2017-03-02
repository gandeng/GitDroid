package com.feicuiedu.gitdroid.Gank;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.feicuiedu.gitdroid.Gank.Modle.GankItem;
import com.feicuiedu.gitdroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/5.
 */
public class GankAdapter extends BaseAdapter {
    private List<GankItem> lists ;

    public void setData(List<GankItem> lists){
        lists.clear();
        lists.addAll(lists);
    }

    public GankAdapter(){
        lists=new ArrayList<GankItem>();
    }
    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public GankItem getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_gank,viewGroup,false);
            view.setTag(new ViewHolder(view));
        }
        ViewHolder viewHolder= (ViewHolder) view.getTag(i);
        GankItem gankItem=lists.get(i);
        viewHolder.textView.setText(gankItem.getDesc());

        return view;
    }

    class ViewHolder{
        private TextView textView;
        public ViewHolder(View view){
            textView= (TextView) view.findViewById(R.id.gank_item);
        }
    }
}
