package com.mook.arc

import android.os.Bundle
import com.mook.arc.base.BaseActivity
import com.mook.arc.databinding.MainActivityBinding
import com.mook.arc.ui.main.MainFragment

class MainActivity : BaseActivity<MainActivityBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
        initView()
        initData()
    }

    fun initView() {
        //view的事假
        mBinding.run {

        }
    }

    fun initData() {
        //页面数据初始化
    }
}