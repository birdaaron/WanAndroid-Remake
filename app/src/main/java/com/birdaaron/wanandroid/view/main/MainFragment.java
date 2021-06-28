package com.birdaaron.wanandroid.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.birdaaron.wanandroid.R;
import com.birdaaron.wanandroid.databinding.ModuleFragmentMainBinding;
import com.birdaaron.wanandroid.view.search.SearchActivity;
import com.birdaaron.wanandroid.viewModel.MainViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MainFragment extends Fragment
{
    private ModuleFragmentMainBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.module_fragment_main,container,false);
        mBinding.llMainSearchBar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
        mBinding.vp2Main.setAdapter(new FragmentStateAdapter(this)
        {
            @NonNull
            @Override
            public Fragment createFragment(int position)
            {
                switch (position)
                {
                    case 0 :
                        return new MainArticleFragment();
                    case 1:
                        return new MainProjectFragment();
                }
                return new MainArticleFragment();
            }

            @Override
            public int getItemCount()
            {
                return 2;
            }
        });
        new TabLayoutMediator(mBinding.tlMain, mBinding.vp2Main, true, new TabLayoutMediator.TabConfigurationStrategy()
        {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position)
            {
                switch (position)
                {
                    case 0:
                        tab.setText("文章");
                        break;
                    case 1:
                        tab.setText("项目");
                        break;
                }
            }
        }).attach();
        return mBinding.getRoot();
    }
}
