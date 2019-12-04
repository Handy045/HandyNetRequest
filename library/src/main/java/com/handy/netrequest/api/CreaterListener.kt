package com.handy.netrequest.api

import kotlinx.coroutines.Job

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
    suspend fun call(): RESULT?

    /**
     * 解析接口返回数据
     */
    suspend fun analyze(result: RESULT): TARGET?

    /**
     * 初始化协程
     */
    fun initialize(): CreaterListener<RESULT, TARGET>

    /**
     * 执行协程，执行结果会通过resultListener回调，并支持dialogListener.showProgress()
     */
    fun connect(): Job?

    /**
     * 并发执行协程，直接返回执行结果，且与resultListener和dialogListener无关
     */
    suspend fun await(): TARGET?
}