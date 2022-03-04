package com.mook.arc.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mook.arc.base.BaseFragment
import com.mook.arc.databinding.MainFragmentBinding
import com.mook.arc.model.request.LoginRequest

class MainFragment : BaseFragment<MainFragmentBinding>() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val mViewModel: MainViewModel by viewModels { MainViewModelFactory }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getBinding().run {
            btnA.setOnClickListener {
                mViewModel.login(LoginRequest("bruce", "123456", "brucexxx@163.com"))
            }
        }
    }
}