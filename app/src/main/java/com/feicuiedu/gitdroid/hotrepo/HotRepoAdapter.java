package com.feicuiedu.gitdroid.hotrepo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.feicuiedu.gitdroid.hotrepo.repolist.RepoListFragment;

import java.util.List;

/**
 * 作者：yuanchao on 2016/7/27 0027 14:33
 * 邮箱：yuanchao@feicuiedu.com
 */
public class HotRepoAdapter extends FragmentPagerAdapter {
    private List<Language> languages;

    public HotRepoAdapter(FragmentManager fm, Context context) {
        super(fm);
        languages = Language.getDefaultLanguages(context);
    }

    @Override public Fragment getItem(int position) {
        // RepoListFragment
        return RepoListFragment.getInstance(languages.get(position));
    }

    @Override public int getCount() {
        return languages.size();
    }

    @Override public CharSequence getPageTitle(int position) {
        return languages.get(position).getName();
    }
}
