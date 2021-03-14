package com.birdaaron.wanandroid.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.birdaaron.wanandroid.BR;
import com.birdaaron.wanandroid.databinding.ModuleItemArticleBinding;
import com.birdaaron.wanandroid.model.bean.ArticleItem;
import com.birdaaron.wanandroid.R;
import com.birdaaron.wanandroid.view.web.WebActivity;

import java.util.List;

import androidx.databinding.DataBindingUtil;

public class ArticleListAdapter extends BaseAdapter
{
    private Context mContext;
    private List<ArticleItem> mData;
    public ArticleListAdapter(Context context, List<ArticleItem> data)
    {
        this.mContext = context;
        this.mData = data;
    }
    @Override
    public int getCount()
    {
        return mData.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ModuleItemArticleBinding binding ;
        if(convertView==null)
        {
            binding = DataBindingUtil.inflate
                    (LayoutInflater.from(mContext),R.layout.module_item_article,parent,false);
            convertView = binding.getRoot();
        }
        else
            binding = DataBindingUtil.getBinding(convertView);
        binding.setVariable(BR.article,mData.get(position));
        binding.llItemArticleContainer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra("url",mData.get(position).getLink());
                mContext.startActivity(intent);
            }
        });
        return convertView;

    }

    public void setData(List<ArticleItem> mData)
    {
        this.mData = mData;
    }
    public List<ArticleItem> getData()
    {
        return mData;
    }
}

