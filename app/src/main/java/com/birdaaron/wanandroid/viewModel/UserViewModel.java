package com.birdaaron.wanandroid.viewModel;

import android.app.Application;
import android.os.Handler;
import android.os.Message;

import com.birdaaron.wanandroid.model.UserModel;
import com.birdaaron.wanandroid.model.bean.User;
import com.birdaaron.wanandroid.util.SharePreferencesTool;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.annotation.NonNull;
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
    public MutableLiveData<String> username;
    public MutableLiveData<Integer> status;
    public MutableLiveData<Boolean> isLogin;
    private final UserModel um = new UserModel(getApplication());
    private final UserViewModel.MyHandler mHandler = new MyHandler(this);
    public UserViewModel(@NonNull Application application)
    {
        super(application);
        status = new MutableLiveData<>();
        username = new MutableLiveData<>();
        isLogin = new MutableLiveData<>();
        checkLogin();
        clearStatus();
    }
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

    }
    public void clearCookie()
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
            if(msg.what==LOGIN_SUCCESS)
            {
                User user = (User)msg.obj;
                viewModel.username.setValue(user.getUsername());
                viewModel.status.setValue(LOGIN_SUCCESS);
            }
            else if(msg.what==LOGIN_FAILED)
            {
                System.out.println(viewModel.status.getValue());
                viewModel.status.setValue(LOGIN_FAILED);
            }
        }
    }
}
