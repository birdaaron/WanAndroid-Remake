package com.birdaaron.wanandroid.view.web;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;

import com.birdaaron.wanandroid.R;
import com.birdaaron.wanandroid.databinding.ModuleActivityWebBinding;
import com.birdaaron.wanandroid.model.bean.ArticleItem;
import com.birdaaron.wanandroid.view.base.BaseActivity;
import com.birdaaron.wanandroid.viewModel.UserViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class WebActivity extends BaseActivity
{
    private UserViewModel mViewModel;
    private ModuleActivityWebBinding mBinding;
    private boolean isCollected;
    private ArticleItem mArticle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.module_activity_web);
        Intent intent = getIntent();
        mArticle = intent.getParcelableExtra("article");
        isCollected = mArticle.isCollect();

        mBinding.wvWeb.setWebViewClient(new WebViewClient());
        mBinding.wvWeb.loadUrl(mArticle.getLink());
        mBinding.tvWebTitle.setText(mArticle.getTitle());

        setCollectIcon();
        mBinding.ivWebCollect.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                isCollected = !isCollected;
                setCollectIcon();
                execCollect();
            }
        });
    }
    private void execCollect()
    {
        int id = mArticle.getOriginId()!=0?mArticle.getOriginId():mArticle.getId();
        if(isCollected)
            mViewModel.execCollect(id);
        else
            mViewModel.execUnCollect(id);
    }
    private void setCollectIcon()
    {
        if(isCollected)
            mBinding.ivWebCollect.setImageResource(R.drawable.ic_baseline_star_24);
        else
            mBinding.ivWebCollect.setImageResource(R.drawable.ic_baseline_star_border_24);
    }
}
