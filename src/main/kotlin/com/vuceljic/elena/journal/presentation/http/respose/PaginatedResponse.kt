package com.vuceljic.elena.journal.presentation.http.respose

import kotlinx.serialization.Serializable

@Serializable
data class PaginatedResponse<T>(
    val items: List<T>,
    val currentPage: Int,
    val totalPages: Int,
    val totalItems: Long
)