package com.handy.netrequest.api

/**
 * @title: DialogApi
 * @projectName HandyNetRequest
 * @description: 提示框接口
 * @author LiuJie https://github.com/Handy045
 * @date Created in 2019-11-29 16:14
 */
interface DialogListener {
    /**
     * 显示进度提示框
     */
    fun showProgress(progressInfo: String)

    /**
     * 显示成功提示框
     */
    fun showSuccess(successInfo: String)

    /**
     * 显示错误提示框
     */
    fun showError(errorInfo: String)

    /**
     * 销毁提示框
     */
    fun dismiss()
}