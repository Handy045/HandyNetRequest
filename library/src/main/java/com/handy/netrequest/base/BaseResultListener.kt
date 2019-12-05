package com.handy.netrequest.base

import com.handy.netrequest.api.DialogListener

/**
 * @title: BaseResultListener
 * @projectName HandyNetRequest
 * @description: TODO
 * @author LiuJie https://github.com/Handy045
 * @date Created in 2019-12-03 16:00
 */
abstract class BaseResultListener<TARGET> {
    private var dialogListener: DialogListener? = null

    fun setDialogListener(listener: DialogListener? = null) {
        this.dialogListener = listener
    }

    open fun onSuccess(data: TARGET) {
        dialogListener?.dismiss()
    }

    open fun onFailed(throwable: Throwable) {
        dialogListener?.dismiss()
    }

    open fun onFinish() {
    }
}