package com.handy.netrequest.base

import com.handy.netrequest.api.DialogListener

/**
 * @title: BaseResultListener
 * @projectName HandyNetRequest
 * @description: TODO
 * @author LiuJie https://github.com/Handy045
 * @date Created in 2019-12-03 16:00
 */
abstract class BaseResultListener<TARGET>(var dialogListener: DialogListener? = null) {

    fun onSuccess(data: TARGET) {
        dialogListener?.dismiss()
    }

    fun onFailed(throwable: Throwable) {
        dialogListener?.dismiss()
    }

    fun onFinish() {
    }
}