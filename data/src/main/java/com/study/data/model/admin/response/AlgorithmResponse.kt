package com.study.data.model.admin.response


import com.google.gson.annotations.SerializedName

data class AlgorithmResponse(
    @SerializedName("posts")
    val posts: List<Post>?,
    @SerializedName("totalPage")
    val totalPage: Int?
)