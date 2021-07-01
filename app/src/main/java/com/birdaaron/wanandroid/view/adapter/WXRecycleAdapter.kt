package com.birdaaron.wanandroid.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.birdaaron.wanandroid.R
import com.birdaaron.wanandroid.databinding.ModuleItemWxBinding
import com.birdaaron.wanandroid.model.bean.WXItem

class WXRecycleAdapter(mContext : Context,mData: MutableList<WXItem>) : RecyclerView.Adapter<WXRecycleAdapter.ViewHolder>()
{
    class ViewHolder(mBinding: ModuleItemWxBinding) : RecyclerView.ViewHolder(mBinding.root)
    {
         var mBinding : ModuleItemWxBinding?=null
        init
        {
            this.mBinding = mBinding
        }
    }

    private var mContext:Context?=null
    private var mData:MutableList<WXItem>?=null
    init
    {
        this.mContext = mContext
        this.mData = mData
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val mBinding =
                DataBindingUtil.inflate<ModuleItemWxBinding>(
                        LayoutInflater.from(mContext), R.layout.module_item_wx,parent,false)
        return ViewHolder(mBinding)
    }

    override fun getItemCount(): Int
    {
        return mData!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.mBinding!!.tvItemWxName.setText(mData!![position].name)
    }
}