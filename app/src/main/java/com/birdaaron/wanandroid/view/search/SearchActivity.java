package com.birdaaron.wanandroid.view.search;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import com.birdaaron.wanandroid.R;
import com.birdaaron.wanandroid.databinding.ModuleActivitySearchBinding;
import com.birdaaron.wanandroid.model.bean.ArticleItem;
import com.birdaaron.wanandroid.view.adapter.ArticleListAdapter;
import com.birdaaron.wanandroid.viewModel.SearchViewModel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import me.gujun.android.taggroup.TagGroup;

public class SearchActivity extends AppCompatActivity
{
    private ModuleActivitySearchBinding mBinding;
    private SearchViewModel mViewModel;
    private ArticleListAdapter mArticleListAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this,R.layout.module_activity_search);
        mViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        initStatusBar();
        initSearch();
        refreshHistory();
    }
    private void initStatusBar()
    {
        getWindow().setStatusBarColor(ContextCompat.getColor(this,android.R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
    private void initSearch()
    {
        initSearchResult(); //搜索结果模块
        initSearchInput(); //搜索输入模块
        initSearchHistory();//搜索历史模块
    }
    private void initSearchInput()
    {
        //清除搜索输入
        mBinding.ibSearchInputClear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mBinding.etSearchInput.getText().clear();
            }
        });
        //进行搜索时显示搜索结果listview，未搜索时显示历史记录
        mBinding.etSearchInput.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s)
            {
                if(!s.toString().isEmpty()&&mBinding.ibSearchInputClear.getVisibility()==View.INVISIBLE)
                {
                    mBinding.ibSearchInputClear.setVisibility(View.VISIBLE);//搜索输入不为空时，显示输入清空图标
                }
                else if(s.toString().isEmpty())
                {
                    mBinding.ibSearchInputClear.setVisibility(View.INVISIBLE);
                    mBinding.rlSearchResult.setVisibility(View.GONE);
                    mBinding.rlSearchHistory.setVisibility(View.VISIBLE);
                }
            }
        });

        //点击搜索按钮进行搜索
        mBinding.tvSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String input = mBinding.etSearchInput.getText().toString();
                if(!input.isEmpty())
                {
                    //添加进历史记录
                    SharedPreferences sp  = getSharedPreferences("searchHistory",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    String history = sp.getString("history","");
                    if(!history.contains(input))
                    {
                        editor.putString("history",history+input+",");
                        editor.apply();//commit?
                        refreshHistory();
                    }
                    //显示搜索结果
                    mBinding.rlSearchResult.setVisibility(View.VISIBLE);
                    mBinding.rlSearchResult.autoRefresh();
                }
            }
        });
        //返回按钮
        mBinding.ivSearchBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }
    private void initSearchResult()
    {
        //对搜索出的文章列表进行观察，一旦变化就更新listview
        mArticleListAdapter = new ArticleListAdapter(this,mViewModel.mArticleList.getValue());
        mBinding.setAdapter(mArticleListAdapter);
        mViewModel.mArticleList.observe(this, new Observer<List<ArticleItem>>()
        {
            @Override
            public void onChanged(List<ArticleItem> articleItems)
            {
                mArticleListAdapter.setData(articleItems);
                mArticleListAdapter.notifyDataSetChanged();
            }
        });

        mBinding.rlSearchResult.setOnLoadMoreListener(new OnLoadMoreListener()
        {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout)
            {

                mViewModel.loadArticle();
                mBinding.rlSearchResult.finishLoadMore();
            }
        });
        mBinding.rlSearchResult.setOnRefreshListener(new OnRefreshListener()
        {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout)
            {
                String input = mBinding.etSearchInput.getText().toString();
                mViewModel.setKey(input);
                mViewModel.clearPage();
                mViewModel.loadArticle();
                mBinding.rlSearchResult.finishRefresh();
            }
        });
    }
    private void initSearchHistory()
    {
        //点击历史记录tag进行搜索
        mBinding.tgSearchHistory.setOnTagClickListener(new TagGroup.OnTagClickListener()
        {
            @Override
            public void onTagClick(String tag)
            {
                mBinding.etSearchInput.setText(tag);
                mBinding.tvSearch.callOnClick();
            }
        });
        //历史记录清空按钮
        mBinding.ivSearchHistoryClear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SharedPreferences sp  = getSharedPreferences("searchHistory",MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("history","");
                editor.apply();
                refreshHistory();
            }
        });
    }

    private void refreshHistory()
    {
        SharedPreferences sp = getSharedPreferences("searchHistory",MODE_PRIVATE);
        String history = sp.getString("history","");
        if(history.equals(""))
            mBinding.tgSearchHistory.setTags(new String[]{});//better way?
        else
        {
            String[] tags = history.split(",");
            mBinding.tgSearchHistory.setTags(tags);
        }

    }
}
