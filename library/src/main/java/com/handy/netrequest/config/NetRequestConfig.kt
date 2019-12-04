package com.handy.netrequest.config

/**
 * @title: Config
 * @projectName HandyNetRequest
 * @description: TODO
 * @author LiuJie https://github.com/Handy045
 * @date Created in 2019-12-03 15:32
 */
class NetRequestConfig {
    val PROMPT_LOADING = "数据获取中，请稍候"
    val PROMPT_PREPARING = "数据准备中，请稍候"
    val PROMPT_SUBMITTING = "数据提交中，请稍候"

    val PROMPT_FAILED = "服务连接失败，请联系管理员"
    val PROMPT_ERROR_SERVER = "服务异常，请联系管理员"
    val PROMPT_ERROR_TIMEOUT = "服务连接超时，请稍后再试"
    val PROMPT_ERROR_RESPONSE = "服务返回数据错误，请联系管理员"
    val PROMPT_ERROR_ANALYSIS = "服务返回数据解析失败，请联系管理员"
    val PROMPT_EMPTY_RESPONSE = "服务返回数据为空"
    val PROMPT_NULL_FAILEDMESSAGES = "服务接口调用失败原因为空，请联系管理员"

    val PROMPT_NULL_NETWORK = "网络连接不可用，请检查网络设置"
    val PROMPT_EMPTY_RESPONSE_NOMORE = "暂无更多数据"
}