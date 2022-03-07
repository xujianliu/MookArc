package com.mook.arc.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 *
 *@author xujian
 *@date 2021/11/12
 */
open class BaseFragment<_ViewBinding : ViewBinding> : Fragment() {


    protected val TAG: String by lazy {
        this.javaClass.simpleName
    }

    //    protected val sharedPreferences: SharedPreferences by lazy {
//        getSharedPreferences(Constants.sharedPreference_name, Context.MODE_PRIVATE)
//    }
    private var _mBinding: _ViewBinding? = null


    fun getBinding(): _ViewBinding {
        return _mBinding!!
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val superclass = javaClass.genericSuperclass
        val bindingClass = (superclass as ParameterizedType?)!!.actualTypeArguments[0] as Class<*>
        try {
            val method = bindingClass.getDeclaredMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.javaPrimitiveType
            )
            _mBinding = method.invoke(null, inflater, container, false) as _ViewBinding

            viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    super.onDestroy(owner)
                    //fragment 销毁时要销毁binding
                    _mBinding = null
                }
            })

            return _mBinding?.root
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


}
