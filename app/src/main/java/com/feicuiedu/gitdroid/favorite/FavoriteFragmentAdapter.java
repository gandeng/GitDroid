package com.feicuiedu.gitdroid.favorite;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.favorite.model.LoadRepo;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/4.
 */
public class FavoriteFragmentAdapter extends BaseAdapter {
    private List<LoadRepo> loadRepos;

    public FavoriteFragmentAdapter() {
        loadRepos = new ArrayList<LoadRepo>();
    }

    public void setData(Collection<LoadRepo> loadRepolist) {
        loadRepos.clear();
        loadRepos.addAll(loadRepolist);
        notifyDataSetChanged();
    }



    @Override
    public int getCount() {
        return loadRepos.size();
    }

    @Override
    public LoadRepo getItem(int i) {
        return loadRepos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_repo,viewGroup,false);
            view.setTag(new ViewHolder(view));
        }
        ViewHolder viewHolder= (ViewHolder) view.getTag();
        LoadRepo loadRepo=  getItem(i);
        viewHolder.tvRepoName.setText(loadRepo.getFullName());
        viewHolder.tvRepoInfo.setText(loadRepo.getDescription());
        viewHolder.tvRepoStars.setText(String.format("stars : %d", loadRepo.getStartCount()));
        if(loadRepo.getAvatar()!=null) {
            ImageLoader.getInstance().displayImage(loadRepo.getAvatar(), viewHolder.ivIcon);
        }else{
            viewHolder.ivIcon.setImageResource(R.drawable.ic_avatar);
        }

        return view;
    }

    static class ViewHolder{

        @BindView(R.id.ivIcon)
        ImageView ivIcon;
        @BindView(R.id.tvRepoName) TextView tvRepoName;
        @BindView(R.id.tvRepoInfo) TextView tvRepoInfo;
        @BindView(R.id.tvRepoStars)
        TextView tvRepoStars;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
