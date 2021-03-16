package com.birdaaron.wanandroid.view.web;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebViewClient;

import com.birdaaron.wanandroid.R;
import com.birdaaron.wanandroid.databinding.ModuleActivityWebBinding;
import com.birdaaron.wanandroid.view.base.BaseActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class WebActivity extends BaseActivity
{
    private ModuleActivityWebBinding mBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.module_activity_web);
        Intent intent = getIntent();
        mBinding.wvWeb.setWebViewClient(new WebViewClient());
        mBinding.wvWeb.loadUrl(intent.getStringExtra("url"));
    }
}
