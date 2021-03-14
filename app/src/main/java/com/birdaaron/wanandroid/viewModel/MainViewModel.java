package com.birdaaron.wanandroid.viewModel;

import android.os.Message;

import com.birdaaron.wanandroid.model.ArticleModel;
import com.birdaaron.wanandroid.model.bean.ArticleItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel
{
    private int page = 0;
    public final static int ARTICLE_DATA = 0x00;
    public MutableLiveData<List<ArticleItem>> mArticleList ;
    public MainViewModel()
    {
        mArticleList = new MutableLiveData<>();
        mArticleList.setValue(new ArrayList<>());
        this.loadArticle();
    }
    public void loadArticle()
    {
        ArticleModel am = new ArticleModel();
        am.getArticleList(page, new MyHandler(this));
    }
    public void clearPage()
    {
        Objects.requireNonNull(mArticleList.getValue()).clear();//?
        this.page = 0;
    }
    public void addPage() { this.page++; }

    static class MyHandler extends android.os.Handler
    {
        private final MainViewModel viewModel;
        public MyHandler (MainViewModel viewModel)
        {
            this.viewModel = viewModel;
        }
        @Override
        public void handleMessage(@NonNull Message msg)
        {
            super.handleMessage(msg);
            if(msg.what == ARTICLE_DATA)
            {
                List<ArticleItem> result = (List<ArticleItem>)msg.obj;
                if(result.size()!=0)
                {
                    List<ArticleItem> updatedList = viewModel.mArticleList.getValue();
                    Objects.requireNonNull(updatedList).addAll(result);
                    viewModel.mArticleList.setValue(updatedList);
                    viewModel.addPage();
                }

            }
        }
    }
}
