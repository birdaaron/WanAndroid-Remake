package com.birdaaron.wanandroid.view.collect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.birdaaron.wanandroid.R;
import com.birdaaron.wanandroid.databinding.ModuleFragmentCollectBinding;
import com.birdaaron.wanandroid.model.bean.ArticleItem;
import com.birdaaron.wanandroid.view.adapter.ArticleListAdapter;
import com.birdaaron.wanandroid.viewModel.UserViewModel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class CollectionFragment extends Fragment
{
    private ModuleFragmentCollectBinding mBinding;
    private ArticleListAdapter mArticleListAdapter;
    private UserViewModel mViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.module_fragment_collect,container,false);
        mViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        initRefreshLayout();
        initListView();
        mBinding.setViewModel(mViewModel);
        mViewModel.loadCollection();
        return mBinding.getRoot();

    }

    @Override
    public void onStart()
    {
        super.onStart();
        mBinding.srlCollect.autoRefresh();
    }

    private void initListView()
    {
        mArticleListAdapter = new ArticleListAdapter(getContext(),mViewModel.collectionList.getValue());
        mBinding.setAdapter(mArticleListAdapter);
        mViewModel.collectionList.observe(getActivity(), new Observer<List<ArticleItem>>()
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
        mBinding.lvCollect.setNestedScrollingEnabled(true);

        mBinding.srlCollect.setOnLoadMoreListener(new OnLoadMoreListener()
        {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout)
            {

                mViewModel.loadCollection();
                mBinding.srlCollect.finishLoadMore();
            }
        });
        mBinding.srlCollect.setOnRefreshListener(new OnRefreshListener()
        {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout)
            {
                mViewModel.clearPage();
                mViewModel.loadCollection();
                mBinding.srlCollect.finishRefresh();
            }
        });
    }

}
