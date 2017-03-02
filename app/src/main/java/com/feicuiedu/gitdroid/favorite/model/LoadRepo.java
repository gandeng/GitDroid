package com.feicuiedu.gitdroid.favorite.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2016/8/4.
 */
@DatabaseTable (tableName = "local_repo")
public class LoadRepo {
    public static final String COLUMN_GROUP_ID="group_id";
    @DatabaseField (id = true)
    @SerializedName("id")
    private int id;

    @DatabaseField
    private String name;

    @DatabaseField(columnName = "full_name")
    @SerializedName("full_name")
    private String fullName;

    @DatabaseField
    private String description;

    @DatabaseField(columnName = "start_count")
    @SerializedName("stargazers_count")
    private int startCount;

    @DatabaseField(columnName = "fork_count")
    @SerializedName("forks_count")
    private int forkCount;

    @DatabaseField(columnName = "avatar_url")
    @SerializedName("avatar_url")
    private String avatar;

    @DatabaseField (columnName =COLUMN_GROUP_ID ,foreign = true,canBeNull = true)
    @SerializedName("group")
    private RepoGroup repoGroup;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }

    public int getStartCount() {
        return startCount;
    }

    public int getForkCount() {
        return forkCount;
    }

    public String getAvatar() {
        return avatar;
    }

    public RepoGroup getRepoGroup() {
        return repoGroup;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setForkCount(int forkCount) {
        this.forkCount = forkCount;
    }

    public void setStartCount(int startCount) {
        this.startCount = startCount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRepoGroup(RepoGroup repoGroup) {
        this.repoGroup = repoGroup;
    }

    public static List<LoadRepo> getDefaultLoadRepo(Context context){
        try {
            InputStream inputStream =context.getAssets().open("defaultrepos.json");
            String content = IOUtils.toString(inputStream);
            Gson gson=new Gson();
            return gson.fromJson(content,new TypeToken<List<LoadRepo>>(){}.getType());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
