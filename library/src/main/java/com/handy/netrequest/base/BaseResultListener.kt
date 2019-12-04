package com.handy.netrequest.base

import com.handy.netrequest.api.DialogListener
import com.handy.netrequest.api.ResultListener

/**
 * @title: BaseResultListener
 * @projectName HandyNetRequest
 * @description: TODO
 * @author LiuJie https://github.com/Handy045
 * @date Created in 2019-12-03 16:00
 */
abstract class BaseResultListener<TARGET>(override var dialogListener: DialogListener?) :
    ResultListener<TARGET> {

    override fun onSuccess(data: TARGET) {
        dialogListener?.dismiss()
    }

    override fun onFailed(throwable: Throwable) {
        dialogListener?.dismiss()
    }

    override fun onFinish() {
    }
}