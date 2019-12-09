
> 特别说明：当前版本仅支持androidx依赖包

# 什么是协程

协程也叫微线程，是一种无优先级的子程序调度组件、一种新的异步执行方式、一种新的多任务并发的操作手段。协程的概念早在1963年被提出，但在今年才开始兴起，目前应用的语言有go、goLand、kotlin、python。

* 特征：协程是运行在单线程种的并发程序
* 优点：省去了传统 Thread 多线程并发机制中切换线程时带来的线程上下文切换、线程状态切换、Thread 初始化上的性能损耗，能大幅度的提高并发性能 

# 协程与线程、进程的关系和区别

一般程序会有一个主进程，主进程中可能包含多个线程，而线程可以包含多个协程，并且协程与协程之间是可以嵌套的。当我们要执行一个耗时操作时，为了防止阻塞，一般情况下会开启一个子线程来处理。如果阻塞任务过多就需要开启多个子线程。

我们知道，多线程在执行的时候看上去时同时执行的，是因为线程的执行是通过cpu来进行调度的，cpu通过在每个线程之间快速切换使得其看上去时同时执行。当线程池中的多个线程被阻塞时，cpu就会将该线程挂起执行别的线程，这就会在切换线程时消耗大量的资源，当资源有限时多线程的并不优于单线程。

以下是协程的官方定义：

> 协程是一个无优先级的子程序调度组件，允许子程序在特定的地方挂起恢复。线程包含于进程，协程包含于线程。只要内存足够，一个线程中可以有任意多个协程，但某一时刻只能有一个协程在运行，多个协程分享该线程分配到的计算机资源。

# HandyNetRequest介绍

HandyNetRequest是一个基于Kotlin协程特性的Android网络请求模型，支持单个请求或多个请求并发执行，以及Lifecycle的生命周期管理。模型中并不限制网络请求的方式和框架，因此不会涉及或限制okhttp、retrofit等请求方式。提升了应用范围以及可扩展性。

考虑到需要兼顾Java调用，暂不支持扩展函数特性支持，后续会考虑进一步优化。

# 使用方式

BaseApiCreater是请求基类，需要在其子类的call()方法中执行http请求，在analyze()方法中解析并转换请求返回数据。理论上，考虑到请求的复用性，一个子类就是一个接口或一类请求的对象。

* 请求基类
![](http://cos.handy045.com/blog/2019-12-05-HandyNetRequest_BaseApiCreater.jpg)

* 请求子类
![](http://cos.handy045.com/blog/2019-12-05-HandyNetRequest_TestApi.jpg)

* 执行请求（协程）
![](http://cos.handy045.com/blog/2019-12-05-HandyNetRequest_connect.jpg)


## 单个请求执行

* 执行请求
![](http://cos.handy045.com/blog/2019-12-05-HandyNetRequest_connect.png)

* 执行过程
![](http://cos.handy045.com/blog/2019-12-05-HandyNetRequest_connect_result.jpg)

## 多请求并发执行

* 执行请求
![](http://cos.handy045.com/blog/2019-12-05-HandyNetRequest_async.jpg)

* 执行过程
![](http://cos.handy045.com/blog/2019-12-05-HandyNetRequest_async_result.jpg)

## 疑问

1. 是否支持协程执行过程的监控呢？

    在子类构造方法中设置isDebug=true即可。Log日志默认的Tag是"HandyNetRequest"，可在子类构造方法中设置logTag变量，或通过构造的入参进行设置。

    kotlin：
    ```kotlin
    class TestApi(
        activity: AppCompatActivity,
        tag: String = "TestApi",
        resultListener: BaseResultListener<String>? = null
    ) : BaseApiCreater<Map<String, String>, String>(activity, tag, resultListener) {
        init {
            isDebug = true
        }
        ......
    }
    ```

    java：
    ```java
    public class TestApi2 extends BaseApiCreater<Map<String, String>, String> {
        {
            setDebug(true);
        }
    
        public TestApi2(@NotNull AppCompatActivity activity, @Nullable BaseResultListener<String> resultListener) {
            super(activity, "TestApi", resultListener);
        }
        ......
    }
    ```

2. 我想更改下接口提示信息，该如何操作呢？

    可直接修改BaseApiCreater中promptConfig变量的内容
    
    kotlin：
    ```kotlin
    val testApi=  TestApi(activity = this, resultListener = object : BaseResultListener<String>() {
    
        })
    testApi.promptConfig.PROMPT_EMPTY_RESPONSE="服务返回数据为空啦"
    testApi.initialize().connect()
    ```

3. 接口调用或返回数据解析失败了，我自定义的错误描述该如何传给UI呢？

    在接口子类的analyze()方法中，先设置errorMessage错误信息，然后返回null即可。
    
    kotlin：
    ```kotlin
    override suspend fun analyze(result: Map<String, String>): String? {
        errorMessage = "链接失败啦～！"
        return null
    }
    ```

4. 我需要呈现进度框、接口成功或失败时的提示框是否支持呢？

    在子类中重写initDialogListener()方法即可

5. 网络请求时报错：W/System.err: java.io.IOException: Cleartext HTTP traffic to **** not permitted，该怎么处理呢？

    Android 9.0 上的所有应用程序默认都使用https。使用Http请求会出现异常。有以下三种解决方案：
    
    * APP改用https请求（需要服务器支持）。
    * targetSdkVersion 降到27以下（包含27）。
    * 根据 Android的网络安全性配置 自定义其网络安全设置。
    
    具体参考[Android 9.0 http请求解决方案](https://www.jianshu.com/p/1d32c8f0202b)


# 接入方式

1、 在工程的build.gradle增加maven地址

```xml
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

2、 在library的build.gradle增加依赖

```xml
dependencies {
        implementation 'com.github.Handy045:HandyNetRequest:1.1.2'
}
```

最新版本：[![](https://jitpack.io/v/Handy045/HandyNetRequest.svg)](https://jitpack.io/#Handy045/HandyNetRequest)

# 相关链接

[kotlin - Coroutine 协程](https://www.jianshu.com/p/76d2f47b900d)

[Kotlin Coroutines(协程) 完全解析系列](https://www.jianshu.com/p/2659bbe0df16)

[Kotlin协程](https://www.jianshu.com/p/6e6835573a9c)

[Kotlin扩展函数和扩展属性](https://www.jianshu.com/p/7291c9a1ec1e)

[Android 9.0 http请求解决方案](https://www.jianshu.com/p/1d32c8f0202b)