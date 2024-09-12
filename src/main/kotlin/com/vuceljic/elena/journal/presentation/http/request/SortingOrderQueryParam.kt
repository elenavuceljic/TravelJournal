package com.vuceljic.elena.journal.presentation.http.request

import kotlinx.serialization.Serializable

@Serializable
enum class SortingOrderQueryParam {
    DESC, ASC
}

fun SortingOrderQueryParam.toDomain(): com.vuceljic.elena.journal.domain.model.Sorting.SortingOrder {
    return when (this) {
        SortingOrderQueryParam.DESC -> com.vuceljic.elena.journal.domain.model.Sorting.SortingOrder.DESC
        SortingOrderQueryParam.ASC -> com.vuceljic.elena.journal.domain.model.Sorting.SortingOrder.ASC
    }
}