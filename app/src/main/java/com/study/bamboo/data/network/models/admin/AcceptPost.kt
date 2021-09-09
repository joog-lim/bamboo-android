package com.study.bamboo.data.network.models.admin

import com.study.bamboo.utils.Admin

data class AcceptPost(
    val posts: List<Admin.Accept>,
    val totalPage: Int
)