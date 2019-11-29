package com.handy.netrequest

/**
 * @title: Responder
 * @projectName HandyNetRequest
 * @description: TODO
 * @author LiuJie https://github.com/Handy045
 * @date Created in 2019-11-28 10:00
 */
abstract class BaseResponder<O> {
    abstract fun onSuccess(o: O)
    abstract fun onError(error: Throwable)

}