package com.birdaaron.wanandroid.view.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.birdaaron.wanandroid.R;
import com.birdaaron.wanandroid.databinding.ModuleFragmentMineBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class MineFragment extends Fragment
{
    private ModuleFragmentMineBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.module_fragment_mine,container,false);
        return mBinding.getRoot();
    }
}
