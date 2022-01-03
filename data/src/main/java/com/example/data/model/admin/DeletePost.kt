package com.example.data.model.admin

import com.study.bamboo.utils.Admin

data class DeletePost(
    val totalPage: Int,
    val posts: List<Admin.Delete>
)