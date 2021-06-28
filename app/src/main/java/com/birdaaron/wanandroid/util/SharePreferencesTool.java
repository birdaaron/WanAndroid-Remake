package com.birdaaron.wanandroid.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.view.ViewGroup;


import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static android.content.Context.MODE_PRIVATE;

public class SharePreferencesTool
{
    private final Context context;

    public SharePreferencesTool(Context context)
    {
        this.context = context;
    }
    public void addString(String name,String key,String value)
    {
        SharedPreferences sp  = context.getSharedPreferences(name,MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,value);
        editor.apply();
    }
    public String getString(String name,String key,String defaultValue)
    {
        SharedPreferences sp  = context.getSharedPreferences(name,MODE_PRIVATE);
        return sp.getString(key,defaultValue);
    }
    public void clear(String name)
    {
        SharedPreferences sp  = context.getSharedPreferences(name,MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().apply();

    }

}
