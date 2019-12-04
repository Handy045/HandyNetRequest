package com.handy.netrequest.base

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.TimeUtils
import com.handy.netrequest.api.CreaterListener
import com.handy.netrequest.api.DialogListener
import com.handy.netrequest.api.ResultListener
import com.handy.netrequest.config.NetRequestConfig
import kotlinx.coroutines.*
import java.io.Serializable

/**
 * @title: BaseApiCreater
 * @projectName HandyNetRequest
 * @description: TODO
 * @author LiuJie https://github.com/Handy045
 * @date Created in 2019-12-03 11:09
 */
abstract class BaseApiCreater<RESULT, TARGET>(var activity: AppCompatActivity) :
    CreaterListener<RESULT, TARGET>, Serializable {

    /**
     *  是否打印调用日志
     */
    var isPrintLog = false
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
    var config = NetRequestConfig()
    /**
     * 协程JOB对象
     */
    var deferred: Deferred<TARGET?>? = null
    /**
     * 结果回调接口
     */
    private var resultListener: ResultListener<TARGET>? = null

    override fun initialize(): BaseApiCreater<RESULT, TARGET> {
        if (isPrintLog) {
            LogUtils.d("method: 初始化协程\ntime: ${TimeUtils.getNowString()}\nthread: ${Thread.currentThread().name}")
        }
        deferred = GlobalScope.async(context = Dispatchers.Default, start = CoroutineStart.LAZY) {
            try {
                if (isConnected()) {
                    val result = call()
                    if (result != null) {
                        try {
                            analyze(result)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            errorMessage = serviceTag + config.PROMPT_ERROR_ANALYSIS
                            null
                        }
                    } else {
                        errorMessage = serviceTag + config.PROMPT_ERROR_RESPONSE
                        null
                    }
                } else {
                    errorMessage = serviceTag + config.PROMPT_NULL_NETWORK
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage = serviceTag + config.PROMPT_ERROR_SERVER
                null
            }
        }
        return this
    }

    override fun connect() {
        if (isPrintLog) {
            LogUtils.d("method: 协程执行准备\ntime: ${TimeUtils.getNowString()}\nthread: ${Thread.currentThread().name}")
        }
        val dialogListener = initDialog(activity)
        dialogListener?.showProgress(progressInfo)
        resultListener?.registerDialogListener(dialogListener)

        GlobalScope.launch(Dispatchers.Main) {
            if (isPrintLog) {
                LogUtils.d("method: 协程开始执行\ntime: ${TimeUtils.getNowString()}\nthread: ${Thread.currentThread().name}")
            }
            val target: TARGET? = deferred?.await()
            if (isPrintLog) {
                LogUtils.d("method: 协程执行结束，并返回结果信息\ntime: ${TimeUtils.getNowString()}\nthread: ${Thread.currentThread().name}\nresult: $target")
            }
            if (target != null) {
                resultListener?.onSuccess(target)
            } else {
                resultListener?.onFailed(Throwable(errorMessage))
            }
            resultListener?.onFinish()
        }
    }

    override fun initDialog(activity: AppCompatActivity): DialogListener? {
        return null
    }

    fun setResultListener(listener: ResultListener<TARGET>): BaseApiCreater<RESULT, TARGET> {
        this.resultListener = listener
        return this
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