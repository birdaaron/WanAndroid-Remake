package com.birdaaron.wanandroid.model.bean;

import android.os.Parcel;

public class ProjectItem extends ArticleItem
{
    private String desc;
    private String envelopePic;

    public ProjectItem(Parcel source)
    {
        super(source);
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String getEnvelopePic()
    {
        return envelopePic;
    }

    public void setEnvelopePic(String envelopePic)
    {
        this.envelopePic = envelopePic;
    }
}
