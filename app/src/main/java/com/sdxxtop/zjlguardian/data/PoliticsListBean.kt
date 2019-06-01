package com.sdxxtop.zjlguardian.data

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-24 17:32
 * Version: 1.0
 * Description:
 */
data class PoliticsListBean(
    val politics: List<Politic>,
    val politics_num: Int
)

data class Politic(
    val id: Int,
    val part_id: Int,
    val part_name: String,
    val state: String,
    val time: String,
    val title: String,
    val type: String,
    val user_name: String,
    val userid: Int,
    val status: Int,
    val url: String
)