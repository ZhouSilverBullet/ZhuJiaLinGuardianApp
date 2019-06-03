package com.sdxxtop.zjlguardian.ui.notice.data

import android.text.SpannableString

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-28 18:18
 * Version: 1.0
 * Description:
 */

data class NoticeData(
        val notic_list: List<Notic>,
        val notic_num: Int
)

data class Notic(
        val content: String,
        val id: Int,
        val time: String,
        val title: String,
        val url: String,
        var spanString: SpannableString
)

//日期进行分类，然后一个item展示出来
data class NoticDateBean(
        val time: String,
        val notic_list: List<Notic>
)