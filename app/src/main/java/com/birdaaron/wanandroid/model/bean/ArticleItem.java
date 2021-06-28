package com.birdaaron.wanandroid.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ArticleItem implements Parcelable
{
    private int id;
    private int originId;
    private String chapterName;
    private String author;
    private String niceDate;
    private String title;
    private String link;
    private String shareUser;
    private boolean collect;

    public int getOriginId()
    {
        return originId;
    }

    public void setOriginId(int originId)
    {
        this.originId = originId;
    }

    public boolean isCollect()
    {
        return collect;
    }

    public void setCollect(boolean collect)
    {
        this.collect = collect;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getShareUser()
    {
        return shareUser;
    }

    public void setShareUser(String shareUser)
    {
        this.shareUser = shareUser;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNiceDate() {
        return niceDate;
    }

    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(id);
        dest.writeInt(originId);
        dest.writeString(chapterName);
        dest.writeString(author);
        dest.writeString(niceDate);
        dest.writeString(title);
        dest.writeString(link);
        dest.writeString(shareUser);
        dest.writeBoolean(collect);
    }
    public static final Creator<ArticleItem> CREATOR = new Creator<ArticleItem>()
    {
        @Override
        public ArticleItem createFromParcel(Parcel source)
        {
            return new ArticleItem(source);
        }

        @Override
        public ArticleItem[] newArray(int size)
        {
            return new ArticleItem[size];
        }
    };
    public ArticleItem(Parcel source)
    {
        id = source.readInt();
        originId = source.readInt();
        chapterName = source.readString();
        author = source.readString();
        niceDate = source.readString();
        title = source.readString();
        link = source.readString();
        shareUser = source.readString();
        collect = source.readBoolean();
    }
}
