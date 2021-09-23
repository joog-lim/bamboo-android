package com.study.bamboo.data.network.models.user.report

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReportRequest(
    val reason : String
) : Parcelable