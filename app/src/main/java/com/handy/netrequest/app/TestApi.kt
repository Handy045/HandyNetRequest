package com.handy.netrequest.app

import androidx.appcompat.app.AppCompatActivity
import com.handy.netrequest.base.BaseApiCreater
import com.handy.netrequest.base.BaseResultListener
import kotlinx.coroutines.delay

/**
 * @title: TestApi
 * @projectName HandyNetRequest
 * @description: TODO
 * @author LiuJie https://github.com/Handy045
 * @date Created in 2019-12-03 15:29
 */
class TestApi(
    activity: AppCompatActivity,
    tag: String = "TestApi",
    resultListener: BaseResultListener<String>? = null
) :
    BaseApiCreater<Int, String>(activity, tag, resultListener) {
    init {
        isDebug = true
    }

    override suspend fun call(): Int? {
        delay(4000L)
        return 1024
    }

    override suspend fun analyze(result: Int): String? {
        if (result == 1024) {
            return "Hello World"
        } else {
            errorMessage = "返回数据有误"
            return null
        }
    }
}