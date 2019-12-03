package com.handy.netrequest.base

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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

    override suspend fun create() {
        GlobalScope.launch(context = Dispatchers.Default) {
            try {
                if (isConnected()) {
                    val result = call()
                    if (result != null) {
                        try {
                            if (!activity.isFinishing) {
                                final(check(result), resultListener)
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            if (!activity.isFinishing) {
                                resultListener.onFailed(Throwable(config.PROMPT_ERROR_ANALYSIS))
                            }
                        }
                    } else {
                        if (!activity.isFinishing) {
                            resultListener.onFailed(Throwable(config.PROMPT_ERROR_RESPONSE))
                        }
                    }
                } else {
                    if (!activity.isFinishing) {
                        resultListener.onFailed(Throwable(config.PROMPT_NULL_NETWORK))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                if (!activity.isFinishing) {
                    resultListener.onFailed(Throwable(config.PROMPT_ERROR_ANALYSIS))
                }
            }
        }.join()
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