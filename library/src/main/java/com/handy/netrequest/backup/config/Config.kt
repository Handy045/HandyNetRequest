package com.handy.netrequest.config

/**
 * @title: Config
 * @projectName HandyNetRequest
 * @description: TODO
 * @author LiuJie https://github.com/Handy045
 * @date Created in 2019-11-29 17:41
 */
class Config {
    var PROMPT_LOADING = "数据获取中，请稍候"
    var PROMPT_PREPARING = "数据准备中，请稍候"
    var PROMPT_SUBMITTING = "数据提交中，请稍候"

    var PROMPT_ERROR_TIMEOUT = "服务连接超时，请稍后再试"
    var PROMPT_ERROR_REQUEST = "请求数据错误，请联系管理员"
    var PROMPT_ERROR_ANALYSIS = "服务返回数据解析失败，请联系管理员"
    var PROMPT_ERROR_RESPONSE = "服务返回数据错误，请联系管理员"
    var PROMPT_ERROR_SERVER = "服务异常，请联系管理员"

    var PROMPT_EMPTY_RESPONSE = "返回数据为空"
    var PROMPT_EMPTY_RESPONSE_NOMORE = "暂无更多数据"

    var PROMPT_NULL_NETWORK = "网络连接不可用，请检查网络设置"
    var PROMPT_NULL_LOCATION = "未获取到当前位置信息，请稍后再试"
    var PROMPT_NULL_FAILEDMESSAGES = "接口调用失败原因为空，请联系管理员"
}