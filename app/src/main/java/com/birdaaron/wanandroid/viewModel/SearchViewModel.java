package com.birdaaron.wanandroid.viewModel;

import android.app.Application;
import android.os.Message;

import com.birdaaron.wanandroid.model.ArticleModel;
import com.birdaaron.wanandroid.model.bean.ArticleItem;
import com.birdaaron.wanandroid.util.SharePreferencesTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchViewModel extends AndroidViewModel
{
    private final SharePreferencesTool sharePreferencesTool;
    private final String sharePreferencesName = "searchHistory";
    private final String sharePreferencesKey = "history";
    private final String sharePreferencesDefaultValue = "";
    private int page = 0;
    public final static int ARTICLE_DATA = 0x00;
    public MutableLiveData<List<ArticleItem>> mArticleList;
    public MutableLiveData<String> history;
    public String key;

    public SearchViewModel(@NonNull Application application)
    {
        super(application);

        mArticleList = new MutableLiveData<>();
        mArticleList.setValue(new ArrayList<>());

        history = new MutableLiveData<>();
        sharePreferencesTool = new SharePreferencesTool(application);
        history.setValue(sharePreferencesTool.getString(sharePreferencesName,sharePreferencesKey,sharePreferencesDefaultValue));
    }

    public void addHistory(String str)
    {
        String oldHistory = history.getValue();
        String newHistory;
        if(!oldHistory.contains(str))
        {
            newHistory = oldHistory+str+",";
            history.setValue(newHistory);
            sharePreferencesTool.addString(sharePreferencesName,sharePreferencesKey,newHistory);
        }
    }
    public void clearHistory()
    {
        String newHistory = "";
        history.setValue(newHistory);
        sharePreferencesTool.addString(sharePreferencesName,sharePreferencesKey,newHistory);
    }
    public void setKey(String key)
    {
        this.key = key;
    }

    public void loadArticle()
    {
        ArticleModel am = new ArticleModel();
        am.getSearchResult(page, key,new SearchViewModel.MyHandler(this));
    }
    public void clearPage()
    {
        Objects.requireNonNull(mArticleList.getValue()).clear();//?
        this.page = 0;
    }
    public void addPage() { this.page++; }

    static class MyHandler extends android.os.Handler
    {
        private final SearchViewModel viewModel;
        public MyHandler (SearchViewModel viewModel)
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
