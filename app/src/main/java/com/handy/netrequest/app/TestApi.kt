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
) : BaseApiCreater<Map<String, String>, String>(activity, tag, resultListener) {

    override suspend fun call(): Map<String, String>? {
        //todo 网络请求
        //模拟请求阻塞
        delay(3000L)
        //返回请求结果
        return mutableMapOf("flag" to "success", "errorInfo" to "", "data" to "Hello World")
    }

    override suspend fun analyze(result: Map<String, String>): String? {
        if (result["flag"] == "success") {
            return result["data"]
        } else {
            errorMessage = result["errorInfo"] ?: "Connect ERROR!"
            return null
        }
    }
}