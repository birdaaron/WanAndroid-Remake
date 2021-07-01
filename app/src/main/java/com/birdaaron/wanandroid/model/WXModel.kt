package com.birdaaron.wanandroid.model

import com.birdaaron.wanandroid.model.bean.WXItem

class WXModel
{
    fun getWXList():MutableList<WXItem>
    {
        val wxList = mutableListOf<WXItem>()
        val hongyang = WXItem("鸿洋",408)
        val guolin = WXItem("郭霖",409)
        val ygs = WXItem("玉刚说",410)
        val cxmy = WXItem("承香墨影",411)
        val qyz = WXItem("Android群英传",413)
        val codeXS = WXItem("code小生",414)
        val ggkfz = WXItem("谷歌开发者",415)
        val qizhuo = WXItem("奇卓社",416)
        val mtjs = WXItem("美团技术团队",417)
        val gscSloop = WXItem("GcsSloop",420)
        val hlwzc = WXItem("互联网侦察",421)
        val susion = WXItem("susion随心",427)
        val cxyfy = WXItem("程序亦非猿",428)
        val gitYuan = WXItem("Gityuan",434)
        wxList.add(hongyang)
        wxList.add(guolin)
        wxList.add(ygs)
        wxList.add(cxmy)
        wxList.add(qyz)
        wxList.add(codeXS)
        wxList.add(ggkfz)
        wxList.add(qizhuo)
        wxList.add(mtjs)
        wxList.add(gscSloop)
        wxList.add(hlwzc)
        wxList.add(susion)
        wxList.add(cxyfy)
        wxList.add(gitYuan)
        return mutableListOf()
    }
}