package com.handy.netrequest.api

/**
 * @title: ResponderApi
 * @projectName HandyNetRequest
 * @description: 请求体接口
 * @author LiuJie https://github.com/Handy045
 * @date Created in 2019-11-29 16:12
 */
interface CreaterListener<RESULT, TARGET> {

    /**
     * 调用接口
     */
    fun call(): RESULT?

    /**
     * 解析接口返回数据
     */
    fun analyze(result: RESULT): TARGET?

    /**
     * 初始化协程
     */
    fun initialize(): CreaterListener<RESULT, TARGET>

    /**
     * 执行协程
     */
    fun connect()
}