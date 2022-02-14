package com.study.data.model.common.algorithm

import com.google.gson.annotations.SerializedName


data class Data(
    @SerializedName("data")
    val result: List<Result>,
    val status: String
)