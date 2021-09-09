package com.study.bamboo.data.network.models.admin

import com.study.bamboo.utils.Admin

data class AdminRejectPost(
    val totalPage: Int,
    val posts: List<Admin.Reject>
)