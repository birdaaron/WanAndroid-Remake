package com.birdaaron.wanandroid.view.main.view.tagview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.birdaaron.wanandroid.R;
import com.birdaaron.wanandroid.databinding.ModuleViewMyTaglayoutTagBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

public class MyTag extends FrameLayout
{
    private ModuleViewMyTaglayoutTagBinding mBinding;

    private String text;
    public MyTag(@NonNull Context context,String text)
    {
        super(context);
        this.text = text;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.module_view_my_taglayout_tag,this,true);
        mBinding.tvTaglayoutTag.setText(text);

    }

    public String getText()
    {
        return text;
    }

    public MyTag(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void setSelected()
    {

        mBinding.tvTaglayoutTag.setTextColor(Color.WHITE);
        mBinding.tvTaglayoutTag.setBackgroundResource(R.drawable.shape_round_corner_button_selected);
    }
    public void setUnSelected()
    {

        mBinding.tvTaglayoutTag.setTextColor(getResources().getColor(R.color.defaultGray,null));
        mBinding.tvTaglayoutTag.setBackgroundResource(R.drawable.shape_round_corner_button_unselected);
    }

}
