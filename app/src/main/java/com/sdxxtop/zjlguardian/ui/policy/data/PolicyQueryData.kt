package com.sdxxtop.zjlguardian.ui.policy.data

import java.io.Serializable

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-27 14:56
 * Version: 1.0
 * Description:
 */
data class PolicyQueryBean(
        val id: Int,
        val name: String,
        val pid: Int,
        val two_cate: List<TwoCate>
) : Serializable

data class TwoCate(
        val id: Int,
        val name: String,
        val pid: Int,
        val three_cate: List<ThreeCate>
) : Serializable

data class ThreeCate(
        val id: Int,
        val name: String,
        val pid: Int
) : Serializable


data class PolicyBean(
    val policy: List<Policy>,
    val policy_num: Int,
    val title: String
)

data class Policy(
    val add_time: String,
    val id: Int,
    val title: String
)


