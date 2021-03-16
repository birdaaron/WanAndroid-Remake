package com.birdaaron.wanandroid.view.mine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.birdaaron.wanandroid.R;
import com.birdaaron.wanandroid.databinding.ModuleFragmentMineBinding;
import com.birdaaron.wanandroid.view.login.LoginActivity;
import com.birdaaron.wanandroid.viewModel.UserViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class MineFragment extends Fragment
{
    private ModuleFragmentMineBinding mBinding;
    private UserViewModel mViewModel;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.module_fragment_mine,container,false);
        mViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        mViewModel.isLogin.observe(getActivity(), new Observer<Boolean>()
        {
            @Override
            public void onChanged(Boolean aBoolean)
            {
                int loginVisibility = aBoolean?View.GONE:View.VISIBLE;
                int infoVisibility = aBoolean?View.VISIBLE:View.GONE;
                mBinding.llMineInfo.setVisibility(infoVisibility);
                mBinding.llMineLogin.setVisibility(loginVisibility);

            }
        });
        mBinding.setMViewModel(mViewModel);
        mBinding.llMineLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        mBinding.llMineInfo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showLogOffDialog();
            }
        });
        return mBinding.getRoot();
    }
    @Override
    public void onStart()
    {
        super.onStart();
        mViewModel.checkLogin();
    }
    private void showLogOffDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("是否退出账号？");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                mViewModel.isLogin.setValue(false);
                mViewModel.clearCookie();
            }
        });
        builder.setNegativeButton("否", null);
        builder.show();
    }
}
