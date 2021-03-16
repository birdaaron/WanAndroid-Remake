package com.birdaaron.wanandroid.viewModel;

import android.os.Message;

import com.birdaaron.wanandroid.model.ArticleModel;
import com.birdaaron.wanandroid.model.bean.ProjectItem;
import com.birdaaron.wanandroid.model.bean.ProjectTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProjectViewModel extends ViewModel
{
    private int tagId = 294;
    private int page = 1;
    public final static int PROJECT_DATA = 0x00,PROJECT_TAGS = 0x01;
    public MutableLiveData<List<ProjectItem>> mProjectList;
    public MutableLiveData<List<ProjectTag>> mTagList;
    private final ArticleModel am = new ArticleModel();
    private final ProjectViewModel.MyHandler myHandler = new ProjectViewModel.MyHandler(this);
    public ProjectViewModel()
    {
        mProjectList = new MutableLiveData<>();
        mTagList = new MutableLiveData<>();
        mProjectList.setValue(new ArrayList<>());
        loadTagList();
        loadProject();
    }
    public boolean isSameTag(String name)
    {
        return getTagIDByTagName(name)==this.tagId;
    }
    public void setTagIDByTagName(String name)
    {
        int id = getTagIDByTagName(name);
        if(id!=-1)
            this.tagId = id;
    }
    private int getTagIDByTagName(String name)
    {
        List<ProjectTag> list = mTagList.getValue();
        for(ProjectTag tag : list)
            if(tag.getName().equals(name))
            {
                return tag.getId();
            }
        return -1;
    }
    public void loadTagList()
    {
        am.getProjectTagList(myHandler);

    }
    public void loadProject()
    {
        am.getProjectList(tagId,page, myHandler);
    }
    public void clearPage()
    {
        Objects.requireNonNull(mProjectList.getValue()).clear();//?
        this.page = 0;
    }
    public void addPage() { this.page++; }


    static class MyHandler extends android.os.Handler
    {
        private final ProjectViewModel viewModel;
        public MyHandler (ProjectViewModel viewModel)
        {
            this.viewModel = viewModel;
        }
        @Override
        public void handleMessage(@NonNull Message msg)
        {
            super.handleMessage(msg);
            if(msg.what == PROJECT_DATA)
            {
                List<ProjectItem> result = (List<ProjectItem>)msg.obj;
                if(result.size()!=0)
                {
                    List<ProjectItem> updatedList = viewModel.mProjectList.getValue();
                    Objects.requireNonNull(updatedList).addAll(result);
                    viewModel.mProjectList.setValue(updatedList);
                    viewModel.addPage();
                }
            }
            else if(msg.what == PROJECT_TAGS)
            {
                List<ProjectTag> result = (List<ProjectTag>)msg.obj;

                viewModel.mTagList.setValue(result);
            }
        }
    }
}
