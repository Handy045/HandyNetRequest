package com.handy.netrequest.base

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import com.handy.netrequest.api.CreaterListener
import com.handy.netrequest.api.DialogListener
import com.handy.netrequest.api.LogListener
import com.handy.netrequest.api.ResultListener
import com.handy.netrequest.config.Config
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.io.Serializable

/**
 * @title: BaseApiCreater
 * @projectName HandyNetRequest
 * @description: TODO
 * @author LiuJie https://github.com/Handy045
 * @date Created in 2019-11-29 16:35
 */
abstract class BaseApiCreater<RESULT, TARGET>(var activity: AppCompatActivity) :
    CreaterListener<RESULT, TARGET>, Serializable {

    var serviceTag = ""
    var progressInfo = ""
    var config = Config()
    var baseLogPrinter: LogListener = BaseLogPrinter()

    lateinit var dialogListener: DialogListener
    lateinit var resultListener: ResultListener<TARGET>

    override fun create() {
        GlobalScope.async {

        }
//        try {
//            if (isConnected()) {
//                val result = callInterface()
//                if (result != null) {
//                    try {
//                        if (!activity.isFinishing) {
//                            checkResponse(result, resultListener)
//                        }
//                    } catch (e: Exception) {
//                        logListener.print(Log.ERROR, config.PROMPT_ERROR_ANALYSIS)
//                        if (!activity.isFinishing) {
//                            resultListener.onFailed(Throwable(config.PROMPT_ERROR_ANALYSIS))
//                        }
//                    }
//                } else {
//                    if (!activity.isFinishing) {
//                        resultListener.onFailed(Throwable(config.PROMPT_ERROR_RESPONSE))
//                    }
//                }
//            } else {
//                if (!activity.isFinishing) {
//                    resultListener.onFailed(Throwable(config.PROMPT_NULL_NETWORK))
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            logListener.print(Log.ERROR, e.message ?: "")
//            if (!activity.isFinishing) {
//                resultListener.onFailed(Throwable(config.PROMPT_ERROR_ANALYSIS))
//            }
//        }
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