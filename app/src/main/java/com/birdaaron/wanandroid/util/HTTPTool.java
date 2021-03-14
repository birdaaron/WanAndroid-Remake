package com.birdaaron.wanandroid.util;

import android.os.Handler;
import android.os.Message;

import com.birdaaron.wanandroid.model.bean.ArticleItem;

import com.birdaaron.wanandroid.model.bean.ProjectItem;
import com.birdaaron.wanandroid.model.bean.ProjectTag;
import com.birdaaron.wanandroid.viewModel.MainViewModel;
import com.birdaaron.wanandroid.viewModel.ProjectViewModel;
import com.birdaaron.wanandroid.viewModel.SearchViewModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HTTPTool
{
    private OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    public void getSearchResult(String key,String url, Handler handler)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                String result = null;
                RequestBody formBody = new FormBody.Builder()
                        .add("k",key)
                        .build();
                Request request =  new Request.Builder()
                        .url(url)
                        .post(formBody)
                        .build();
                try(Response response = client.newCall(request).execute())
                {
                    result = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    List<ArticleItem> resultList = null;
                    JsonObject obj1 = gson.fromJson(result,JsonObject.class);
                    JsonObject obj2 = gson.fromJson(obj1.get("data"),JsonObject.class);
                    Type type = new TypeToken<List<ArticleItem>>(){}.getType();//1
                    resultList = gson.fromJson(obj2.get("datas"),type);
                    Message msg = Message.obtain();
                    msg.what = SearchViewModel.ARTICLE_DATA;
                    msg.obj = resultList;
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }
    public void getArticleString(String url, Handler handler)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                String result = null;
                Request request =  new Request.Builder()
                        .url(url)
                        .get()
                        .build();
                try(Response response = client.newCall(request).execute())
                {
                    result = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    List<ArticleItem> resultList = null;
                    JsonObject obj1 = gson.fromJson(result,JsonObject.class);
                    JsonObject obj2 = gson.fromJson(obj1.get("data"),JsonObject.class);
                    Type type = new TypeToken<List<ArticleItem>>(){}.getType();//1
                    resultList = gson.fromJson(obj2.get("datas"),type);
                    Message msg = Message.obtain();
                    msg.what = MainViewModel.ARTICLE_DATA;
                    msg.obj = resultList;
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }
    public void getProjectString(String url, Handler handler)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                String result = null;
                Request request =  new Request.Builder()
                        .url(url)
                        .get()
                        .build();
                try(Response response = client.newCall(request).execute())
                {
                    result = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    List<ArticleItem> resultList = null;
                    JsonObject obj1 = gson.fromJson(result,JsonObject.class);
                    JsonObject obj2 = gson.fromJson(obj1.get("data"),JsonObject.class);
                    Type type = new TypeToken<List<ProjectItem>>(){}.getType();//1
                    resultList = gson.fromJson(obj2.get("datas"),type);
                    Message msg = Message.obtain();
                    msg.what = ProjectViewModel.PROJECT_DATA;
                    msg.obj = resultList;
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }
    public void getProjectTagList(String url, Handler handler)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                String result = null;
                Request request =  new Request.Builder()
                        .url(url)
                        .get()
                        .build();
                try(Response response = client.newCall(request).execute())
                {
                    result = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    List<ProjectTag> resultList = null;
                    JsonObject obj = gson.fromJson(result,JsonObject.class);
                    Type type = new TypeToken<List<ProjectTag>>(){}.getType();//1
                    resultList = gson.fromJson(obj.get("data"),type);

                    Message msg = Message.obtain();
                    msg.what = ProjectViewModel.PROJECT_TAGS;
                    msg.obj = resultList;
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }

}
