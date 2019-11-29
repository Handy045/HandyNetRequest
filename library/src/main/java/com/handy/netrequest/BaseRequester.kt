package com.handy.netrequest

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager

/**
 * @title: Requester
 * @projectName HandyNetRequest
 * @description: TODO
 * @author LiuJie https://github.com/Handy045
 * @date Created in 2019-11-28 10:00
 */
abstract class BaseRequester<RESPONSE,RESULT>(var activity: Activity) {

    private fun isConnected(): Boolean {
        val cm = activity.getSystemService(Context.CONNECTIVITY_SERVICE)
        if (cm is ConnectivityManager) {
            val info = cm.activeNetworkInfo
            if (info != null && info.isConnected) {
                return info.isAvailable
            } else {
                return false
            }
        } else {
            return false
        }
    }
}