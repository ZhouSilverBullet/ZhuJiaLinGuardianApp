package com.sdxxtop.zjlguardian.ui.feedback.data

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-28 10:41
 * Version: 1.0
 * Description:
 */

data class ProposalBean(
        val proposal_id: Int
)


data class FeedbackData(
    val proposal: List<Proposal>,
    val proposal_num: Int
)

data class Proposal(
    val id: Int,
    val part_id: Int,
    val part_name: String,
    val state: String,
    val status: String,
    val time: String,
    val title: String,
    val type: String,
    val user_name: String,
    val userid: Int
)