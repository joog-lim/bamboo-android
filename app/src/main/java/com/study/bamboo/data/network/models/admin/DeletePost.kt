package com.study.bamboo.data.network.models.admin

import com.study.bamboo.utils.Admin

data class DeletePost(
    val totalPage: Int,
    val posts: List<Admin.Delete>
)