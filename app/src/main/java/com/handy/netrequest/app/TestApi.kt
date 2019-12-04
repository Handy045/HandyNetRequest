package com.handy.netrequest.app

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.handy.netrequest.base.BaseApiCreater

/**
 * @title: TestApi
 * @projectName HandyNetRequest
 * @description: TODO
 * @author LiuJie https://github.com/Handy045
 * @date Created in 2019-12-03 15:29
 */
class TestApi(activity: AppCompatActivity) : BaseApiCreater<String, Int>(activity) {
    override fun call(): String? {
        Log.d("NetRequest", "call " + Thread.currentThread().name)
        return "aaaaaaaa"
    }

    override fun analyze(response: String): Int? {
        Log.d("NetRequest", "analyze " + Thread.currentThread().name)
        if (response != "null") {
            return response.length
        } else {
            errorMessage = "response is null"
            return null
        }
    }
}