package com.handy.netrequest

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
        return "null"
    }

    override fun analyze(response: String): Int? {
        if (response != "null") {
            return response.length
        } else {
            errorMessage = "response is null"
            return null
        }
    }
}