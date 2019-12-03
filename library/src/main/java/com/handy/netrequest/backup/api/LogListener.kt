package com.handy.netrequest.api

/**
 * @title: LogListener
 * @projectName HandyNetRequest
 * @description: 日志打印接口
 * @author LiuJie https://github.com/Handy045
 * @date Created in 2019-11-29 17:16
 */
interface LogListener {
    fun print(type: Int, msg: String)
}