package com.birdaaron.wanandroid.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.birdaaron.wanandroid.BR;
import com.birdaaron.wanandroid.R;
import com.birdaaron.wanandroid.databinding.ModuleItemArticleBinding;
import com.birdaaron.wanandroid.databinding.ModuleItemProjectBinding;
import com.birdaaron.wanandroid.model.bean.ArticleItem;
import com.birdaaron.wanandroid.model.bean.ProjectItem;
import com.birdaaron.wanandroid.view.web.WebActivity;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

public class ProjectListAdapter extends BaseAdapter
{
    private Context mContext;
    private List<ProjectItem> mData;
    public ProjectListAdapter(Context context, List<ProjectItem> data)
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
        ModuleItemProjectBinding binding ;
        if(convertView==null)
        {
            binding = DataBindingUtil.inflate
                    (LayoutInflater.from(mContext), R.layout.module_item_project,parent,false);
            convertView = binding.getRoot();
        }
        else
            binding = DataBindingUtil.getBinding(convertView);
        binding.setVariable(BR.project,mData.get(position));
        binding.llItemProjectContainer.setOnClickListener(new View.OnClickListener()
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
    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView,String url)
    {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }
    public void setData(List<ProjectItem> mData)
    {
        this.mData = mData;
    }
    public List<ProjectItem> getData()
    {
        return mData;
    }
}
