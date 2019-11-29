package com.handy.netrequest.api

import androidx.appcompat.app.AppCompatActivity

/**
 * @title: ResponderApi
 * @projectName HandyNetRequest
 * @description: 请求体接口
 * @author LiuJie https://github.com/Handy045
 * @date Created in 2019-11-29 16:12
 */
interface RequesterListener<RESPONSE, RESULT> {
    /**
     * 准备提示框控件
     */
    fun initDialogBuilder(activity: AppCompatActivity?): DialogListener?

    /**
     * 调用接口
     */
    @Throws(Exception::class)
    fun callInterface(): RESPONSE

    /**
     * 校验接口返回数据
     */
    @Throws(Exception::class)
    fun checkResponse(response: RESPONSE)

    /**
     * 解析接口返回数据
     */
    @Throws(Exception::class)
    fun analyzeResponse(response: RESPONSE): RESULT

    /**
     * 执行RxJava
     */
    fun execute()
}