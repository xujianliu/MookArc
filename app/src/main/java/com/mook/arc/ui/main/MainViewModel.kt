package com.mook.arc.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mook.arc.model.Result
import com.mook.arc.model.repository.UserRepository
import com.mook.arc.model.request.LoginRequest
import kotlinx.coroutines.launch

class MainViewModel(private val mRepository: UserRepository) : ViewModel() {
    private val TAG = "MainViewModel"
    fun login(request: LoginRequest) {
        viewModelScope.launch {
            val result = try {
                mRepository.makeLoginRequest(request)
            } catch (e: Exception) {
                Result.Error(Exception("Network request failed"))//用不应展示服务器错误，只应该展示用户看得懂的错误页面
            }
            when (result) {
                is Result.Success -> {
                    // Happy path
                    Log.i(TAG, "login:${result.data.toString()} ")

                }
                is Result.Error -> {
                    // Show error in UI
                    Log.e(TAG, "login:${result.exception.message} ")
                }
            }
        }
    }


}


object MainViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(UserRepository()) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}