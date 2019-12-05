package com.handy.netrequest.app

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.handy.netrequest.base.BaseResultListener
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
                    TestApi(activity, "a").initialize().await()
                }
                val b = async {
                    TestApi(activity, "b").initialize().await()
                }
                val c = async {
                    TestApi(activity, "c").initialize().await()
                }
                val d = async {
                    TestApi(activity, "d").initialize().await()
                }
                val e = async {
                    TestApi(activity, "e").initialize().await()
                }
                val f = async {
                    TestApi(activity, "f").initialize().await()
                }
                val g = async {
                    TestApi(activity, "g").initialize().await()
                }

                //协程内部是按照代码从上到下的顺序执行，下面的写法并不能达到并发的目的
//                Log.d("NetRequest", "a is ${a.await()}")
//                Log.d("NetRequest", "b is ${b.await()}")
//                Log.d("NetRequest", "c is ${c.await()}")
//                Log.d("NetRequest", "d is ${d.await()}")
//                Log.d("NetRequest", "e is ${e.await()}")
//                Log.d("NetRequest", "f is ${f.await()}")
//                Log.d("NetRequest", "g is ${g.await()}")

                //同行执行可实现并发效果
                Log.d(
                    "NetRequest",
                    "a is ${a.await()} b is ${b.await()} and c is ${c.await()} and d is ${d.await()} and e is ${e.await()} and f is ${f.await()} and g is ${g.await()}"
                )

                Log.d("NetRequest", "finish ${Thread.currentThread().name}")
            }

            // 单独调用
            TestApi(activity = this, resultListener = object : BaseResultListener<String>(null) {
                override fun onSuccess(data: String) {
                    super.onSuccess(data)
                    Log.d("NetRequest", "OnSuccess: ${data}")
                }

                override fun onFailed(throwable: Throwable) {
                    super.onFailed(throwable)
                    Log.e("NetRequest", "onFailed: ${throwable.message}")
                }
            }).initialize().connect()
        }
    }
}
