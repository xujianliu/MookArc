package com.mook.arc.model.repository

import android.util.Log
import com.mook.arc.model.Result
import com.mook.arc.model.request.LoginRequest
import com.mook.arc.model.response.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * Kotlin 使用堆栈帧管理要运行哪个函数以及所有局部变量。挂起协程时，系统会复制并保存当前的堆栈帧以供稍后使用。
 * 恢复时，会将堆栈帧从其保存位置复制回来，然后函数再次开始运行。即使代码可能看起来像普通的顺序阻塞请求，协程也能确保网络请求避免阻塞主线程。
 *
 * repository 调用抛出的任何意外异常都应处理为界面错误。
 *
 * Repository的数据可以是来自本地，也可以来自网络
 *@author xujian
 *@date 2022/3/4
 */
class UserRepository {



    suspend fun makeLoginRequest(request: LoginRequest): Result<UserInfo> {
        return withContext(Dispatchers.IO) {//在子线程中处理耗时操作,确保主线程安全
            Log.i("makeLoginRequest", "request=$request ")
            delay(3000)//模拟网络请求或者请求数据库

            when (System.currentTimeMillis() % 2) {
                1L -> {//成功
                    val user = UserInfo("bruceliu", "13312345678", "brucexxxx@163.com", 20, "bruce")
                    Result.Success(user)
                }
                else -> {//失败
                    Result.Error(Exception("500 service error"))//服务器真实的错误异常
                }
            }

        }
    }
}