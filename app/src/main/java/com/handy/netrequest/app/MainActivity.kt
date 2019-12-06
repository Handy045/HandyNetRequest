package com.handy.netrequest.app

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    var activity = this

    private val handler = Handler()
    private var count = 0

    private inner class Counter : Runnable {
        override fun run() {
            count++
            Log.i("NetRequest", "$count")
            if (count < 10) {
                handler.postDelayed(this, 1000)
            } else {
                count = 0
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnClick.setOnClickListener {
            handler.post(Counter())

            // 并发调用
            GlobalScope.launch(Dispatchers.Main) {
                val a = async {
                    TestApi(activity, "A").initialize().await()
                }
                val b = async {
                    TestApi(activity, "B").initialize().await()
                }

                //协程内部是按照代码从上到下的顺序执行，下面的写法并不能达到并发的目的
//                Log.d("NetRequest", "a is ${a.await()}")
//                Log.d("NetRequest", "b is ${b.await()}")
//                Log.d("NetRequest", "c is ${c.await()}")

                //同行执行可实现并发效果
                Log.d(
                    "NetRequest",
                    "a is ${a.await()} b is ${b.await()}"
                )

                Log.d("NetRequest", "finish ${Thread.currentThread().name}")
            }

            // 单独调用
//            val testApi=  TestApi(activity = this, resultListener = object : BaseResultListener<String>() {
//                override fun onSuccess(data: String) {
//                    super.onSuccess(data)
//                    Log.d("NetRequest", "OnSuccess: ${data}")
//                }
//
//                override fun onFailed(throwable: Throwable) {
//                    super.onFailed(throwable)
//                    Log.e("NetRequest", "onFailed: ${throwable.message}")
//                }
//            })
//            testApi.promptConfig.PROMPT_EMPTY_RESPONSE="服务返回数据为空啦"
//            testApi.initialize().connect()
        }
    }
}
