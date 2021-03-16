package com.birdaaron.wanandroid.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.birdaaron.wanandroid.model.UserModel;
import com.birdaaron.wanandroid.model.bean.ArticleItem;

import com.birdaaron.wanandroid.model.bean.ProjectItem;
import com.birdaaron.wanandroid.model.bean.ProjectTag;
import com.birdaaron.wanandroid.model.bean.User;
import com.birdaaron.wanandroid.viewModel.MainViewModel;
import com.birdaaron.wanandroid.viewModel.ProjectViewModel;
import com.birdaaron.wanandroid.viewModel.SearchViewModel;
import com.birdaaron.wanandroid.viewModel.UserViewModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HTTPTool
{
    private final Gson gson = new Gson();
    private final OkHttpClient client;
    public HTTPTool()
    {
        client = new OkHttpClient();
    }
    public HTTPTool(Context context)
    {
        client = new OkHttpClient.Builder()
                .cookieJar(new CookieJar()
                {
                    private final SharePreferencesTool tool = new SharePreferencesTool(context);
                    private final Map<String, List<Cookie>> cookiesMap = new HashMap<>();
                    private final Gson gson = new Gson();
                    @Override
                    public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list)
                    {
                        cookiesMap.put(httpUrl.host(),list);
                        String json = gson.toJson(list);
                        tool.addString("loginCookie","cookie",json);
                    }

                    @NotNull
                    @Override
                    public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl)
                    {
                        List<Cookie> list = cookiesMap.get(httpUrl.host());
                        if(list==null)
                        {
                            String json = tool.getString("loginCookie","cookie","");
                            if(json.equals(""))
                                return new ArrayList<>();
                            else
                            {
                                Type type = new TypeToken<List<Cookie>>(){}.getType();
                                list = gson.fromJson(json,type);
                            }
                        }
                        return list!=null?list:new ArrayList<>();
                    }
                }).build();
    }

    public void execCollect(String id)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                String url = "https://www.wanandroid.com/lg/collect/"+id+"/json";
                RequestBody formBody = new FormBody.Builder().build();
                Request request =  new Request.Builder()
                        .url(url)
                        .post(formBody)
                        .build();
                try
                {
                    client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
    public void execLogin(String userName, String password,Handler handler)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                String url = "https://www.wanandroid.com/user/login";
                String result = null;
                RequestBody formBody = new FormBody.Builder()
                        .add("username",userName)
                        .add("password",password)
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
                finally
                {
                    User user = null;
                    Message msg = Message.obtain();


                    JsonObject obj1 = gson.fromJson(result,JsonObject.class);
                    if(obj1.get("errorCode").getAsInt()!=-1)
                    {
                        JsonObject obj2 = gson.fromJson(obj1.get("data"),JsonObject.class);
                        Type type = new TypeToken<User>(){}.getType();
                        user = gson.fromJson(obj2,type);
                        msg.what = UserViewModel.LOGIN_SUCCESS;
                        msg.obj = user;
                    }
                    else
                    {
                        msg.what = UserViewModel.LOGIN_FAILED;
                    }
                    handler.sendMessage(msg);
                }

            }
        }).start();
    }
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
