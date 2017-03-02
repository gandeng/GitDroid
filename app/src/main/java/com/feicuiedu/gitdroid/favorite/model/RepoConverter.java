package com.feicuiedu.gitdroid.favorite.model;

import android.support.annotation.NonNull;

import com.feicuiedu.gitdroid.commons.LogUtils;
import com.feicuiedu.gitdroid.favorite.Dao.RepoGroupDao;
import com.feicuiedu.gitdroid.hotrepo.repolist.modle.Repo;

import java.util.ArrayList;
import java.util.List;

/**
 * 将Repo(热门仓库)转换为LocalRepo(本地仓库)对象, 为了实现仓库的收藏功能
 * <p/>
 * 作者：yuanchao on 2016/8/4 0004 15:15
 * 邮箱：yuanchao@feicuiedu.com
 */
public class RepoConverter {

    private RepoConverter() {
    }

    /**
     * 将Repo(热门仓库)转换为LocalRepo(本地仓库)对象, 默认为未分类
     */
    public static @NonNull LoadRepo convert(@NonNull Repo repo,@NonNull RepoGroup repoGroup) {
        LoadRepo localRepo = new LoadRepo();
        LogUtils.e(repo.getOwner().toString());
        localRepo.setAvatar(repo.getOwner().getAvatar());
        localRepo.setDescription(repo.getDescription());
        localRepo.setFullName(repo.getFullName());
        localRepo.setId(repo.getId());
        localRepo.setName(repo.getName());
        localRepo.setStartCount(repo.getStarCount());
        localRepo.setForkCount(repo.getForkCount());
        localRepo.setRepoGroup(repoGroup);
        return localRepo;
    }

//    public static @NonNull List<LoadRepo> converAll(@NonNull List<Repo> repos) {
//        ArrayList<LoadRepo> localRepos = new ArrayList<LoadRepo>();
//        for (Repo repo : repos) {
//            localRepos.add(convert(repo));
//        }
//        return localRepos;
//    }

}
