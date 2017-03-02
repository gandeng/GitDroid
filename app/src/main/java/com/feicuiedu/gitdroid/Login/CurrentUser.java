package com.feicuiedu.gitdroid.Login;

/**
 * 此类是一个用来缓存当前用户信息的，极简单的实现
 *
 * 我们就是强用来保存
 *
 * 作者：yuanchao on 2016/7/29 0029 15:57
 * 邮箱：yuanchao@feicuiedu.com
 */
public class CurrentUser {
    private CurrentUser(){}

    private static String accessToken;

    private static User user;

    // 当前是否有token
    public static boolean hasAccessToken(){
        return accessToken != null;
    }
    // 当前是否是"空的"(还没有登陆)
    public static boolean isEmpty(){
        return accessToken == null || user == null;
    }
    // 清除信息
    public static void clear(){
        accessToken = null;
        user = null;
    }


    public static void setAccessToken(String accessToken) {
        CurrentUser.accessToken = accessToken;
    }

    public static void setUser(User user) {
        CurrentUser.user = user;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static User getUser() {
        return user;
    }
}
