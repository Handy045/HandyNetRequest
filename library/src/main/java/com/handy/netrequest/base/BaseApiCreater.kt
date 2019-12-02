package com.handy.netrequest.base

import android.content.Context
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.handy.netrequest.api.CreaterListener
import com.handy.netrequest.api.DialogListener
import com.handy.netrequest.api.LogListener
import com.handy.netrequest.api.ResultListener
import com.handy.netrequest.config.Config
import java.io.Serializable

/**
 * @title: BaseApiCreater
 * @projectName HandyNetRequest
 * @description: TODO
 * @author LiuJie https://github.com/Handy045
 * @date Created in 2019-11-29 16:35
 */
abstract class BaseApiCreater<RESULT, TARGET>(var activity: AppCompatActivity) :
    AsyncTask<Void, Void, Void>(), CreaterListener<RESULT, TARGET>, Serializable {

    var serviceTag = ""
    var progressInfo = ""
    var config = Config()
    var logListener: LogListener = BaseLog()
    lateinit var resultListener: ResultListener<TARGET>
    var dialogListener: DialogListener? = this.initDialogBuilder(activity)

    override fun initDialogBuilder(activity: AppCompatActivity?): DialogListener? {
        return null
    }

    override fun build() {
        if (resultListener == null) {
            logListener.print(Log.ERROR, "please set the resultListener first.")
        } else {
            this.resultListener.registerDialogListener(this.dialogListener)
            execute()
        }
    }

    override fun onPreExecute() {
        super.onPreExecute()
        if (dialogListener != null && progressInfo.isNotEmpty()) {
            dialogListener?.showProgress(progressInfo)
        }
    }

    override fun doInBackground(vararg params: Void?): Void {
        try {
            if (isConnected()) {
                val result = callInterface()
                if (result != null) {
                    try {
                        if (!activity.isFinishing) {
                            checkResponse(result, resultListener)
                        }
                    } catch (e: Exception) {
                        logListener.print(Log.ERROR, config.PROMPT_ERROR_ANALYSIS)
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
            logListener.print(Log.ERROR, e.message ?: "")
            if (!activity.isFinishing) {
                resultListener.onFailed(Throwable(config.PROMPT_ERROR_ANALYSIS))
            }
        }
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