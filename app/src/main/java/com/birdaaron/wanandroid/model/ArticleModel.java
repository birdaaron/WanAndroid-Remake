package com.birdaaron.wanandroid.model;

import android.os.Handler;


import com.birdaaron.wanandroid.util.HTTPTool;

public class ArticleModel
{
    private final HTTPTool httpTool = new HTTPTool();
    //private final mHandler handler
    public void getArticleList(int index,Handler handler)
    {
        httpTool.getArticleString("https://www.wanandroid.com/article/list/"+index+"/json",handler);
    }
    public void getProjectList(int cid,int index,Handler handler)
    {
        httpTool.getProjectString("https://www.wanandroid.com/project/list/"+index+"/json?cid="+cid,handler);
    }
    public void getProjectTagList(Handler handler)
    {
        httpTool.getProjectTagList("https://www.wanandroid.com/project/tree/json",handler);
    }
    public void getSearchResult(int index,String key,Handler handler)
    {
        httpTool.getSearchResult(key,"https://www.wanandroid.com/article/query/"+index+"/json",handler);
    }
}
