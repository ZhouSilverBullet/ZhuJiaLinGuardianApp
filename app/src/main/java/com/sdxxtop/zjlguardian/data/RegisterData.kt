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

data class PartBean(
    val part_id: Int,
    val part_name: String,
    var isCheck:Boolean
)

data class PushDataBean(
    val politics_id: Int,
    val url:String
)