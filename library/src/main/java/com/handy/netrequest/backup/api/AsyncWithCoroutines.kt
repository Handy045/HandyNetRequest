package com.handy.netrequest.api

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.*

/**
 * @title: AsyncWithCoroutines
 * @projectName HandyNetRequest
 * @description: TODO
 * @author LiuJie https://github.com/Handy045
 * @date Created in 2019-12-02 16:02
 */

internal class CoroutineLifecycleListener(private val deferred: Deferred<*>) : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun cancelCoroutine() {
        if (!deferred.isCancelled) {
            deferred.cancel()
        }
    }
}

fun <T> load(activity: AppCompatActivity, loader: suspend () -> T): Deferred<T> {
    val deferred = GlobalScope.async(context = Dispatchers.Default, start = CoroutineStart.LAZY) {
        loader()
    }
    activity.lifecycle.addObserver(CoroutineLifecycleListener(deferred))
    return deferred
}

infix fun <T> Deferred<T>.then(block: suspend (T) -> Unit): Job {
    return GlobalScope.launch(context = Dispatchers.Main) {
        try {
            block(this@then.await())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}