package com.birdaaron.wanandroid.model.bean;

public class User
{
    private String username;
    private int[] collectIds;
    private int coinCount;

    public int getCoinCount()
    {
        return coinCount;
    }

    public void setCoinCount(int coinCount)
    {
        this.coinCount = coinCount;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public int[] getCollectIds()
    {
        return collectIds;
    }

    public void setCollectIds(int[] collectIds)
    {
        this.collectIds = collectIds;
    }
}
