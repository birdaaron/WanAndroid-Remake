package com.birdaaron.wanandroid.view.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;


import com.birdaaron.wanandroid.databinding.ActivityMainBinding;

import com.birdaaron.wanandroid.R;
import com.birdaaron.wanandroid.view.adapter.ArticleListAdapter;
import com.birdaaron.wanandroid.view.base.BaseActivity;
import com.birdaaron.wanandroid.view.collect.CollectionFragment;
import com.birdaaron.wanandroid.view.main.MainFragment;
import com.birdaaron.wanandroid.view.mine.MineFragment;
import com.birdaaron.wanandroid.viewModel.MainViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends BaseActivity
{
    private ActivityMainBinding mActivityMainBinding;
    private ArticleListAdapter mArticleListAdapter;
    private MainViewModel mViewModel;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        mActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mFragmentManager = getSupportFragmentManager();
        initBottomNavigation();
    }




    private void initBottomNavigation()
    {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.fcv_main, MainFragment.class,null).commit();
        mActivityMainBinding.bnvMain.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                switch (item.getItemId())
                {
                    case R.id.tab_menu_main:
                        transaction.replace(R.id.fcv_main,MainFragment.class,null).commit();
                        return true;
                    case R.id.tab_menu_mine:
                        transaction.replace(R.id.fcv_main, MineFragment.class,null).commit();
                        return true;
                    case R.id.tab_menu_collect:
                        transaction.replace(R.id.fcv_main, CollectionFragment.class,null).commit();
                        return true;
                }
                return false;
            }
        });
    }

}