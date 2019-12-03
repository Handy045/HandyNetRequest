package com.handy.netrequest.base

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
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
     * XX服务
     */
    var serviceTag = ""
    /**
     * 进度框提示内容
     */
    var progressInfo = ""
    /**
     * 结果回调接口
     */
    var resultListener: ResultListener<TARGET>? = null
    /**
     * 提示框初始化接口
     */
    val dialogListener: DialogListener? = this.initDialog(activity)

    /**
     * 提示内容配置类
     */
    var config = NetRequestConfig()
    var errorMessage = ""
    var deferred: Deferred<TARGET?>? = null

    override fun initialize(): BaseApiCreater<RESULT, TARGET> {
        deferred = GlobalScope.async {
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
        MainScope().launch {
            val target: TARGET? = deferred?.await()
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