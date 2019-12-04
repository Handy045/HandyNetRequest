package com.handy.netrequest.config

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.Deferred

/**
 * @title: LifecycleListener
 * @projectName HandyNetRequest
 * @description: 生命周期管理
 * @author LiuJie https://github.com/Handy045
 * @date Created in 2019-12-04 17:20
 */
class LifecycleListener(private val defferred: Deferred<*>) : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun cancelCoroutine() {
        if (!defferred.isCancelled) {
            defferred.cancel()
        }
    }
}