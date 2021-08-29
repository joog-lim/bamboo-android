package com.study.bamboo.model.dto.admin.get

import com.study.bamboo.utils.Admin

data class DeletePost(
    val count: Int,
    val cursor: String,
    val hasNext: Boolean,
    val posts: List<Admin.Delete>
)