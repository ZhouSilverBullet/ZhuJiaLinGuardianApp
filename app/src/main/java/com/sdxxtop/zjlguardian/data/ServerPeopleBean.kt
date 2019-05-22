package com.sdxxtop.zjlguardian.data

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-22 14:36
 * Version: 1.0
 * Description:
 */

data class ServerPeopleBean(
        val imgs: List<Img>,
        val notices: List<Notice>
)

data class Img(
        val id: Int,
        val image: String,
        val time: String
)

data class Notice(
        val id: Int,
        val time: String,
        val title: String
)