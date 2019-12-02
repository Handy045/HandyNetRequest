package com.handy.netrequest.api

import androidx.appcompat.app.AppCompatActivity

/**
 * @title: ResponderApi
 * @projectName HandyNetRequest
 * @description: 请求体接口
 * @author LiuJie https://github.com/Handy045
 * @date Created in 2019-11-29 16:12
 */
interface CreaterListener<RESULT, TARGET> {
    /**
     * 准备提示框控件
     */
    fun initDialog(activity: AppCompatActivity): DialogListener?

    /**
     * 调用接口
     */
    @Throws(Exception::class)
    fun call(): RESULT

    /**
     * 校验接口返回数据
     */
    @Throws(Exception::class)
    fun check(response: RESULT, resultListener: ResultListener<TARGET>)

    /**
     * 解析接口返回数据
     */
    @Throws(Exception::class)
    fun conversion(response: RESULT, resultListener: ResultListener<TARGET>): TARGET

    /**
     * 执行RxJava
     */
    fun create()
}