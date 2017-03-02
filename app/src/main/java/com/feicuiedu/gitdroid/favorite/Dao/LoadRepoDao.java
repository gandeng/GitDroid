package com.feicuiedu.gitdroid.favorite.Dao;

import com.feicuiedu.gitdroid.favorite.model.LoadRepo;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/8/4.
 */
public class LoadRepoDao {
    private Dao<LoadRepo, Long> dao;

    public LoadRepoDao(DBHelp dbHelp) {
        try {
            dao = dbHelp.getDao(LoadRepo.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * 添加或更新本地仓库数据
     */
    public void createOrUpdate(LoadRepo loadRepo) {
        try {
            dao.createOrUpdate(loadRepo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * 添加或更新本地仓库数据
     */
    public void createOrUpdate(List<LoadRepo> localRepos) {
        for (LoadRepo localRepo : localRepos) {
            createOrUpdate(localRepo);
        }
    }

    /**
     * 删除本地仓库数据
     */
    public void delete(LoadRepo localRepo){
        try {
            dao.delete(localRepo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询本地仓库(图像处理的，架构的...,能查到全部或未分类的？？？)
     *
     * @param groupId 类别ID号
     * @return 仓库列表
     */
    public List<LoadRepo> queryForGroupId(int groupId){
        try {

            return dao.queryForEq(LoadRepo.COLUMN_GROUP_ID, groupId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询本地仓库(未分类的)
     */
    public List<LoadRepo> queryForNoGroup(){
        try {
            return dao.queryBuilder().where().isNull(LoadRepo.COLUMN_GROUP_ID).query();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询本地仓库(全部的)
     */
    public List<LoadRepo> queryForAll(){
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
