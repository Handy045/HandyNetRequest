package com.handy.netrequest.app

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.handy.netrequest.base.BaseResultListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val handler = Handler()
    private var count = 0

    private inner class Counter : Runnable {
        override fun run() {
            count++
            Log.i("NetRequest", "$count")
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnClick.setOnClickListener {
            handler.post(Counter())

            TestApi(this).setResultListener(object : BaseResultListener<Int>() {
                override fun onSuccess(data: Int) {
                    super.onSuccess(data)
                    Log.d("NetRequest", "onSuccess $data")
                    Log.d("NetRequest", "onSuccess " + Thread.currentThread().name)
                }

                override fun onFailed(throwable: Throwable) {
                    super.onFailed(throwable)
                    Log.d("NetRequest", "onFailed " + throwable.message)
                    Log.d("NetRequest", "onFailed " + Thread.currentThread().name)
                }
            }).initialize().connect()
        }
    }
}
