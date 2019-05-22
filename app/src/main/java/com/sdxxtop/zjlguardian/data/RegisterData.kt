package com.sdxxtop.zjlguardian.data

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-21 17:37
 * Version: 1.0
 * Description:
 */
data class RegisterBean (
    val auto_token: String,
    val expire_time: Int,
    val userid: String
)