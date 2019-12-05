package com.handy.netrequest.base

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.handy.netrequest.api.CreaterListener
import com.handy.netrequest.config.LifecycleListener
import com.handy.netrequest.config.PromptConfig
import kotlinx.coroutines.*
import java.io.Serializable
import java.util.*

/**
 * @title: BaseApiCreater
 * @projectName HandyNetRequest
 * @description: TODO
 * @author LiuJie https://github.com/Handy045
 * @date Created in 2019-12-03 11:09
 */
abstract class BaseApiCreater<RESULT, TARGET>(
    var activity: AppCompatActivity,
    var logTag: String = "HandyNetRequest",
    var resultListener: BaseResultListener<TARGET>? = null
) :
    CreaterListener<RESULT, TARGET>, Serializable {

    /**
     *  是否打印调用日志
     */
    var isDebug = false
    /**
     * XX服务
     */
    var serviceTag = ""
    /**
     * 进度框提示内容
     */
    var progressInfo = ""
    /**
     * 调用错误信息
     */
    var errorMessage = ""
    /**
     * 提示内容配置类
     */
    var promptConfig = PromptConfig()
    /**
     * 协程JOB对象
     */
    var deferred: Deferred<TARGET?>? = null

    override fun initialize(): BaseApiCreater<RESULT, TARGET> {
        if (isDebug) {
            Log.d(
                logTag,
                "method: 初始化协程\ntime: ${Date().time}\nthread: ${Thread.currentThread().name}"
            )
        }
        deferred = GlobalScope.async(context = Dispatchers.IO, start = CoroutineStart.LAZY) {
            try {
                if (isConnected()) {
                    if (isDebug) {
                        Log.d(
                            logTag,
                            "method: 开始调用接口\ntime: ${Date().time}\nthread: ${Thread.currentThread().name}"
                        )
                    }
                    val result = call()
                    if (result != null) {
                        try {
                            if (isDebug) {
                                Log.d(
                                    logTag,
                                    "method: 开始解析数据\ntime: ${Date().time}\nthread: ${Thread.currentThread().name}"
                                )
                            }
                            analyze(result)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            errorMessage = serviceTag + promptConfig.PROMPT_ERROR_ANALYSIS
                            null
                        }
                    } else {
                        errorMessage = serviceTag + promptConfig.PROMPT_ERROR_RESPONSE
                        null
                    }
                } else {
                    errorMessage = promptConfig.PROMPT_NULL_NETWORK
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage = serviceTag + promptConfig.PROMPT_ERROR_SERVER
                null
            }
        }
        activity.lifecycle.addObserver(LifecycleListener(deferred!!))
        return this
    }

    override fun connect(): Job? {
        if (isDebug) {
            Log.d(
                logTag,
                "method: 协程准备执行\ntime: ${Date().time}\nthread: ${Thread.currentThread().name}"
            )
        }
        if (deferred == null) {
            Log.e(logTag, "警告：请先执行initialize()方法，初始化协程")
        } else {
            if (resultListener == null) {
                Log.w(logTag, "警告：结果回调接口是NULL")
            } else if (resultListener?.dialogListener == null) {
                Log.w(logTag, "警告：结果回调接口的提示框接口是NULL")
            }

            resultListener?.dialogListener?.showProgress(progressInfo)

            return GlobalScope.launch(Dispatchers.Main) {
                if (isDebug) {
                    Log.d(
                        logTag,
                        "method: 协程开始执行\ntime: ${Date().time}\nthread: ${Thread.currentThread().name}"
                    )
                }
                val target: TARGET? = deferred!!.await()
                if (isDebug) {
                    Log.d(
                        logTag,
                        "method: 协程执行结束，并返回结果信息\ntime: ${Date().time}\nthread: ${Thread.currentThread().name}\nresult: $target"
                    )
                }
                if (target != null) {
                    resultListener?.onSuccess(target)
                } else {
                    resultListener?.onFailed(Throwable(if (errorMessage.isEmpty()) promptConfig.PROMPT_FAILED else errorMessage))
                }
                resultListener?.onFinish()
            }
        }
        return null
    }

    override suspend fun await(): TARGET? {
        if (isDebug) {
            Log.d(
                logTag,
                "method: 协程准备执行\ntime: ${Date().time}\nthread: ${Thread.currentThread().name}"
            )
        }
        if (deferred == null) {
            Log.e(logTag, "警告：请先执行initialize()方法，初始化协程")
        } else {
            if (isDebug) {
                Log.d(
                    logTag,
                    "method: 协程开始执行\ntime: ${Date().time}\nthread: ${Thread.currentThread().name}"
                )
            }
            return deferred!!.await()
        }
        return null
    }

    private fun isConnected(): Boolean {
        val cm = activity.getSystemService(Context.CONNECTIVITY_SERVICE)
        if (cm is ConnectivityManager) {
            val info = cm.activeNetworkInfo
            if (info != null && info.isConnected) {
                return info.isAvailable
            } else {
                return false
            }
        } else {
            return false
        }
    }
}