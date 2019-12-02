package com.handy.netrequest.base

import android.util.Log
import com.handy.netrequest.api.LogListener

/**
 * @title: BaseLog
 * @projectName HandyNetRequest
 * @description: TODO
 * @author LiuJie https://github.com/Handy045
 * @date Created in 2019-11-29 17:17
 */
class BaseLogPrinter : LogListener {
    override fun print(type: Int, msg: String) {
        when (type) {
            Log.VERBOSE, Log.DEBUG, Log.INFO, Log.WARN, Log.ERROR, Log.ASSERT -> Log.println(
                type,
                "HandyNetRequest",
                msg
            )
            else -> Log.println(
                Log.ERROR,
                "HandyNetRequest",
                "日志类型有误"
            )
        }
    }
}