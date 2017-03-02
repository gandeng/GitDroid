package com.feicuiedu.gitdroid.HotUser;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/8/2.
 */
public class HotUser {
//
//    "login": "jeycin",
//            "id": 1809948,
//            "avatar_url": "https://avatars.githubusercontent.com/u/1809948?v=3",

    private String login;
    private int id;
    @SerializedName("avatar_url")
    private String avatar;
    @SerializedName("html_url")
    private String url;


    public String getLogin() {
        return login;
    }
    public int getId() {
        return id;
    }

    public String getAvatar() {
        return avatar;
    }
    public String getUrl(){
        return url;
    }

    @Override public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                '}';
    }

}
