package com.birdaaron.wanandroid.viewModel;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.birdaaron.wanandroid.model.UserModel;
import com.birdaaron.wanandroid.model.bean.ArticleItem;
import com.birdaaron.wanandroid.model.bean.User;
import com.birdaaron.wanandroid.util.SharePreferencesTool;
import com.birdaaron.wanandroid.view.login.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import okhttp3.Cookie;

public class UserViewModel extends AndroidViewModel
{

    public final static int LOGIN_READY = -1;
    public final static int LOGIN_SUCCESS = 0x00;
    public final static int LOGIN_FAILED = 0x01;
    public final static int SIGN_IN_SUCCESS = 0x02;
    public final static int SIGN_IN_FAILED = 0x03;
    public final static int COLLECTION_DATA = 0x04;

    public MutableLiveData<String> username;
    public MutableLiveData<Integer> status;
    public MutableLiveData<Boolean> isLogin;
    public MutableLiveData<List<ArticleItem>> collectionList;

    private int collectionPage = 0;
    private final UserModel um = new UserModel(getApplication());
    private final UserViewModel.MyHandler mHandler = new MyHandler(this);
    public UserViewModel(@NonNull Application application)
    {
        super(application);
        status = new MutableLiveData<>();
        username = new MutableLiveData<>();
        isLogin = new MutableLiveData<>();
        collectionList = new MutableLiveData<>();
        collectionList.setValue(new ArrayList<>());
        checkLogin();
        clearStatus();
    }
    public void showLoginActivity()
    {
        Intent intent = new Intent(getApplication(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(intent);
    }

    public MutableLiveData<Boolean> getIsLogin()
    {
        return isLogin;
    }

    public void loadCollection()
    {
        if(isLogin.getValue()!=null&&isLogin.getValue())
            um.getCollectionList(collectionPage,mHandler);
    }
    public void clearPage()
    {
        Objects.requireNonNull(collectionList.getValue()).clear();//?
        collectionPage = 0;
    }
    public void addPage() { collectionPage++; }
    private void initUserName(List<Cookie> list)
    {
        for(Cookie cookie : list)
            if(cookie.name().equals("loginUserName"))
            {
                username.setValue(cookie.value());
                break;
            }
    }
    public void checkLogin()
    {
        boolean result = false;
        SharePreferencesTool tool = new SharePreferencesTool(getApplication());
        String cookieJson = tool.getString("loginCookie","cookie","");
        System.out.println(cookieJson);
        if(cookieJson.equals(""))
            isLogin.setValue(result);
        else
        {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Cookie>>(){}.getType();
            List<Cookie> list = gson.fromJson(cookieJson,type);
            result = list.size()!=1;
            isLogin.setValue(result);
            if(result)
                initUserName(list);
        }
        System.out.println(isLogin.getValue());
    }
    public void execLogOff()
    {
        clearCookie();
        clearStatus();
        clearUserName();
        clearLogin();
    }
    private void clearLogin()
    {
        isLogin.setValue(false);
    }
    private void clearUserName()
    {
        username.setValue("");
    }
    private void clearCookie()
    {
        SharePreferencesTool tool = new SharePreferencesTool(getApplication());
        tool.clear("loginCookie");
    }
    public void clearStatus()
    {

        status.setValue(LOGIN_READY);
    }
    public void execLogin(String username,String password)
    {
        um.execLogin(username,password,mHandler);
    }
    public void execCollectOrUnCollect(int id,int originId,boolean collect)
    {
        System.out.println("test-viewmodel-"+collect);
        if(collect)
            execCollect(id);
        else
            execUnCollect(originId);
    }
    public void execCollect(int id)
    {
        um.execCollect(id);
    }
    public void execUnCollect(int id)
    {
        um.execUnCollect(id);
    }
    static class MyHandler extends Handler
    {
        private final UserViewModel viewModel;
        public MyHandler (UserViewModel viewModel)
        {
            this.viewModel = viewModel;
        }
        @Override
        public void handleMessage(@NonNull Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case LOGIN_SUCCESS:
                    User user = (User)msg.obj;
                    viewModel.status.setValue(LOGIN_SUCCESS);
                    viewModel.username.setValue(user.getUsername());
                    break;
                case LOGIN_FAILED:
                    viewModel.status.setValue(LOGIN_FAILED);
                    break;
                case COLLECTION_DATA:
                    List<ArticleItem> result = (List<ArticleItem>)msg.obj;
                    if(result.size()!=0)
                    {
                        List<ArticleItem> updatedList = viewModel.collectionList.getValue();
                        Objects.requireNonNull(updatedList).addAll(result);
                        viewModel.collectionList.setValue(updatedList);
                        viewModel.addPage();
                    }
                    break;
            }

        }
    }
}
