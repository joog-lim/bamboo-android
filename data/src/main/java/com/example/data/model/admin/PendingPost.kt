package com.example.data.model.admin

import com.study.bamboo.utils.Admin

data class PendingPost(
    val totalPage: Int,
    val posts: List<Admin.Pending>
)