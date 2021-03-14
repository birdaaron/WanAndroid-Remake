package com.birdaaron.wanandroid.view.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.birdaaron.wanandroid.R;
import com.birdaaron.wanandroid.databinding.ModuleFragmentMainArticleBinding;
import com.birdaaron.wanandroid.model.bean.ArticleItem;
import com.birdaaron.wanandroid.view.adapter.ArticleListAdapter;
import com.birdaaron.wanandroid.viewModel.MainViewModel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChild;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class MainArticleFragment extends Fragment
{
    private ModuleFragmentMainArticleBinding mBinding;
    private ArticleListAdapter mArticleListAdapter;
    private MainViewModel mViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.module_fragment_main_article,container,false);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        initRefreshLayout();
        loadArticle();
        return mBinding.getRoot();

    }
    private void loadArticle()
    {
        mArticleListAdapter = new ArticleListAdapter(getContext(),mViewModel.mArticleList.getValue());
        mBinding.setAdapter(mArticleListAdapter);
        mViewModel.mArticleList.observe(getActivity(), new Observer<List<ArticleItem>>()
        {
            @Override
            public void onChanged(List<ArticleItem> articleItems)
            {
                mArticleListAdapter.setData(articleItems);
                mArticleListAdapter.notifyDataSetChanged();
            }
        });
    }
    private void initRefreshLayout()
    {
        mBinding.lvArticleListMain.setNestedScrollingEnabled(true);

        mBinding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener()
        {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout)
            {

                mViewModel.loadArticle();
                mBinding.refreshLayout.finishLoadMore();
            }
        });
        mBinding.refreshLayout.setOnRefreshListener(new OnRefreshListener()
        {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout)
            {
                mViewModel.clearPage();
                mViewModel.loadArticle();
                mBinding.refreshLayout.finishRefresh();
            }
        });
    }
}
