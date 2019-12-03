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
    fun call(): RESULT?

    /**
     * 解析接口返回数据
     */
    fun analyze(response: RESULT): TARGET?

    /**
     * 获取协程Deferred对象
     */
    fun initialize(): CreaterListener<RESULT, TARGET>

    /**
     * 执行协程
     */
    fun connect()
}