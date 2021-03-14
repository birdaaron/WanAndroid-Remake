package com.birdaaron.wanandroid.view.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.birdaaron.wanandroid.R;
import com.birdaaron.wanandroid.databinding.ModuleFragmentMainProjectBinding;
import com.birdaaron.wanandroid.model.bean.ProjectItem;
import com.birdaaron.wanandroid.model.bean.ProjectTag;
import com.birdaaron.wanandroid.view.adapter.ProjectListAdapter;
import com.birdaaron.wanandroid.view.main.view.tagview.MyTagLayout;
import com.birdaaron.wanandroid.viewModel.ProjectViewModel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class MainProjectFragment extends Fragment
{
    private ModuleFragmentMainProjectBinding mBinding;
    private ProjectListAdapter mProjectListAdapter;
    private ProjectViewModel mViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.module_fragment_main_project,container,false);
        mViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);
        initRefreshLayout();
        initTags();
        loadProject();
        return mBinding.getRoot();
    }
    private void loadProject()
    {
        mProjectListAdapter = new ProjectListAdapter(getContext(),mViewModel.mProjectList.getValue());
        mBinding.setAdapter(mProjectListAdapter);
        mViewModel.mProjectList.observe(getActivity(), new Observer<List<ProjectItem>>()
        {
            @Override
            public void onChanged(List<ProjectItem> projectItems)
            {
                mProjectListAdapter.setData(projectItems);
                mProjectListAdapter.notifyDataSetChanged();
            }
        });
    }
    private void initTags()
    {
        mViewModel.mTagList.observe(getActivity(), new Observer<List<ProjectTag>>()
        {
            @Override
            public void onChanged(List<ProjectTag> projectTags)
            {

                List<String> tagsNameList = new ArrayList<>();
                for(ProjectTag tag : projectTags)
                    tagsNameList.add(tag.getName());
                mBinding.mtlMainArticleTags.initTags(tagsNameList, new MyTagLayout.MyTagClickListener()
                {
                    @Override
                    public void onClick(String tag)
                    {
                        if(!mViewModel.isSameTag(tag))
                        {
                            mViewModel.setTagIDByTagName(tag);
                            mBinding.rlMainProject.autoRefresh();
                        }
                    }
                });
            }
        });
    }
    private void initRefreshLayout()
    {
        mBinding.lvMainProject.setNestedScrollingEnabled(true);
        mBinding.rlMainProject.setOnLoadMoreListener(new OnLoadMoreListener()
        {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout)
            {

                mViewModel.loadProject();

                mBinding.rlMainProject.finishLoadMore();
            }
        });
        mBinding.rlMainProject.setOnRefreshListener(new OnRefreshListener()
        {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout)
            {
                mViewModel.clearPage();
                mViewModel.loadProject();
                mBinding.rlMainProject.finishRefresh();
            }
        });
    }
}
