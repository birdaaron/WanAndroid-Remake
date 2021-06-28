package com.birdaaron.wanandroid.view.main.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.birdaaron.wanandroid.R
import com.birdaaron.wanandroid.viewModel.WXViewModel

class MainWXFragment : Fragment() {

    companion object {
        fun newInstance() = MainWXFragment()
    }

    private lateinit var viewModel: WXViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.module_fragment_wx, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WXViewModel::class.java)
        // TODO: Use the ViewModel
    }

}