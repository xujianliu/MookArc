package com.mook.arc.model

/**
 *
 *@author xujian
 *@date 2022/3/4
 */
sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}