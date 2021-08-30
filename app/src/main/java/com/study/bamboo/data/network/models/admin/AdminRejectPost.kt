package com.study.bamboo.data.network.models.admin

import com.study.bamboo.utils.Admin

data class AdminRejectPost(
    val count: Int,
    val cursor: String,
    val hasNext: Boolean,
    val posts: List<Admin.Reject>
)