package com.birdaaron.wanandroid.model;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.birdaaron.wanandroid.model.bean.ArticleItem;
import com.birdaaron.wanandroid.model.bean.User;
import com.birdaaron.wanandroid.util.HTTPTool;
import com.birdaaron.wanandroid.viewModel.SearchViewModel;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;

public class UserModel
{

    private final HTTPTool httpTool;
    public UserModel(Context context)
    {
        httpTool = new HTTPTool(context);
    }
    public void execLogin(String username,String password,Handler handler)
    {
        httpTool.execLogin(username,password,handler);
    }
    public void getCollectionList(int page,Handler handler)
    {
        httpTool.getCollectionList("https://www.wanandroid.com/lg/collect/list/"+page+"/json",handler);
    }
    public void execCollect(int id)
    {
        httpTool.execCollect(id);
    }
    public void execUnCollect(int id )
    {
        httpTool.execUnCollect(id);
    }
}
