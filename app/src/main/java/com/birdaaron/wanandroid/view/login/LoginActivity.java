package com.birdaaron.wanandroid.view.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.birdaaron.wanandroid.R;
import com.birdaaron.wanandroid.databinding.ModuleActivityLoginBinding;
import com.birdaaron.wanandroid.view.base.BaseActivity;
import com.birdaaron.wanandroid.viewModel.UserViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class LoginActivity extends BaseActivity
{
    private ModuleActivityLoginBinding mBinding;
    private UserViewModel mViewModel;
    private int status = UserViewModel.LOGIN_READY;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.module_activity_login);
        mViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        mBinding.ivMineBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        mBinding.btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String username = mBinding.etLoginUsername.getText().toString();
                String password = mBinding.etLoginPassword.getText().toString();
                if(username.equals(""))
                    Toast.makeText(LoginActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                else if(password.equals(""))
                    Toast.makeText(LoginActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                else
                    mViewModel.execLogin(username,password);
            }
        });

        mViewModel.status.observe(this, new Observer<Integer>()
        {
            @Override
            public void onChanged(Integer integer)
            {
                switch (integer)
                {
                    case UserViewModel.LOGIN_SUCCESS:
                        Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                        mViewModel.clearStatus();
                        finish();
                        break;
                    case UserViewModel.LOGIN_FAILED:
                        Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                        mViewModel.clearStatus();
                        break;
                    case UserViewModel.SIGN_IN_SUCCESS:
                        break;
                    case UserViewModel.SIGN_IN_FAILED:
                        break;
                }
            }
        });
    }
}
