package com.feicuiedu.gitdroid.HotUser;

import com.feicuiedu.gitdroid.hotrepo.repolist.modle.Repo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2016/8/2.
 */
public class HotUserResult {
    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("incomplete_results")
    private boolean incompleteResults;

    @SerializedName("items")
    private List<HotUser> usetList;

    public int getTotalCount() {
        return totalCount;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public List<HotUser> getUserList() {
        return usetList;
    }

    @Override public String toString() {
        return "User{" +
                "id=" + totalCount+
                ", name='" + incompleteResults + '\'' +
                ", login='" + usetList + '\'' +
                '}';
    }

    //    {
//        "total_count": 2074901,
//            "incomplete_results": false,
//            "items": [{}]
//    }
}
