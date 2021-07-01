package com.birdaaron.wanandroid.view.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout.HORIZONTAL
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.birdaaron.wanandroid.R
import com.birdaaron.wanandroid.databinding.ModuleFragmentWxBinding
import com.birdaaron.wanandroid.model.bean.WXItem
import com.birdaaron.wanandroid.view.adapter.WXRecycleAdapter
import com.birdaaron.wanandroid.viewModel.WXViewModel

class MainWXFragment : Fragment()
{

    companion object
    {
        fun newInstance() = MainWXFragment()
    }

    private lateinit var viewModel: WXViewModel
    private var mBinding: ModuleFragmentWxBinding?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        val mRoot = LayoutInflater.from(context).inflate(R.layout.module_fragment_wx, container, false)
        mBinding = DataBindingUtil.bind(mRoot)
        mBinding?.rvMainWxContainer?.layoutManager =
                GridLayoutManager(context,2,RecyclerView.VERTICAL,false)
        val data : MutableList<WXItem> = mutableListOf()
        val wx1 = WXItem("鸿洋",0)
        val wx2 = WXItem("鸿洋2",1)
        val wx3 = WXItem("鸿洋3",1)
        val wx4 = WXItem("鸿洋4",1)
        data.add(wx1)
        data.add(wx2)
        data.add(wx3)
        data.add(wx4)
        mBinding?.rvMainWxContainer?.adapter=WXRecycleAdapter(context!!,data)
        return mRoot
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WXViewModel::class.java)

    }

}