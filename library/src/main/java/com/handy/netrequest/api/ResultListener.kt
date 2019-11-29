package com.handy.netrequest.api

/**
 * @title: ResponderListener
 * @projectName HandyNetRequest
 * @description: 响应体接口
 * @author LiuJie https://github.com/Handy045
 * @date Created in 2019-11-29 16:17
 */
interface ResultListener<TARGET> {

    fun registerDialogListener(listener: DialogListener?)

    /**
     * 正常处理
     */
    fun onSuccess(data: TARGET)

    /**
     * 异常处理
     */
    fun onFailed(throwable: Throwable)

    /**
     * 处理结束
     */
    fun onFinish()
}