package com.tripbook.libs.network.model.request

data class ReportRequest (
    val articleId: Long,
    val content: String
)