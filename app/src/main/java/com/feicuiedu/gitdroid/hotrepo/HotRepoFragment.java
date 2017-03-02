package com.feicuiedu.gitdroid.hotrepo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feicuiedu.gitdroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 热门仓库Fragment
 *
 * 里面放着一个ViewPager，在Adapter里面，每一个pager面都是一个Fragment
 *
 * 作者：yuanchao on 2016/7/27 0027 14:27
 * 邮箱：yuanchao@feicuiedu.com
 */
public class HotRepoFragment extends Fragment{

    @BindView(R.id.repo_viewPager) ViewPager viewPager;
    @BindView(R.id.tabLayout) TabLayout tabLayout;

    private HotRepoAdapter adapter;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hot_repo, container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        adapter = new HotRepoAdapter(getChildFragmentManager(),getContext());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
