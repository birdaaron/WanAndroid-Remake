package com.birdaaron.wanandroid.view.main.view.tagview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.birdaaron.wanandroid.R;
import com.birdaaron.wanandroid.databinding.ModuleViewMyTaglayoutBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.widget.ViewPager2;

public class MyTagLayout extends FrameLayout
{
    private Context context;
    private int selectedTag = 0;
    private List<MyTag> tagList ;
    private ModuleViewMyTaglayoutBinding mBinding;
    public MyTagLayout(@NonNull Context context)
    {
        super(context);
    }

    public MyTagLayout(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mBinding = DataBindingUtil.inflate(inflater,R.layout.module_view_my_taglayout,this,true);
        tagList = new ArrayList<>();
        this.context = context;

    }
    public void initTags(List<String> tags,MyTagClickListener mClickListener)
    {
        for(int i = 0;i<tags.size();i++)
        {
            MyTag tag = new MyTag(context, tags.get(i));
            tag.setTag(i);
            tag.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    setSelected((int)tag.getTag());
                    if(mClickListener!=null)
                        mClickListener.onClick(tag.getText());
                }
            });
            tagList.add(tag);
            mBinding.llTaglayoutFold.addView(tag);
        }
        tagList.get(0).setSelected();
    }
    private void setSelected(int id)
    {
        if(id!=this.selectedTag)
        {
            MyTag selectedTag = tagList.get(id);
            MyTag unSelectedTag = tagList.get(this.selectedTag);
            this.selectedTag = id;
            selectedTag.setSelected();
            unSelectedTag.setUnSelected();
        }
    }
    public interface MyTagClickListener
    {
        void onClick(String tag);
    }
}
