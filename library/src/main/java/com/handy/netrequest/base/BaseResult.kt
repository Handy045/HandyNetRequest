package com.handy.netrequest.base

import com.handy.netrequest.api.DialogListener

/**
 * @title: BaseResult
 * @projectName HandyNetRequest
 * @description: TODO
 * @author LiuJie https://github.com/Handy045
 * @date Created in 2019-12-03 16:00
 */
abstract class BaseResult<TARGET> {

    var dialog: DialogListener? = null

    open fun onSuccess(data: TARGET) {
        dialog?.dismiss()
    }

    open fun onFailed(throwable: Throwable) {
        dialog?.dismiss()
        dialog?.showError(throwable.message ?: "")
    }

    open fun onFinish() {
    }
}