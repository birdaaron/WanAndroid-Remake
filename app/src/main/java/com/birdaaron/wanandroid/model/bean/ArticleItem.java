package com.birdaaron.wanandroid.model.bean;

public class ArticleItem
{
    private String chapterName;
    private String author;
    private String niceDate;
    private String title;
    private String link;
    private String shareUser;


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
}
