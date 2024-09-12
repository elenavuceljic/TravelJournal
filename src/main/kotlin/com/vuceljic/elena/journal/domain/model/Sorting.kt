package com.vuceljic.elena.journal.domain.model

import kotlinx.serialization.Serializable

data class Sorting(
    val byEntryDate: SortingOrder?,
    val byTitle: SortingOrder?,
) {

    @Serializable
    enum class SortingOrder {
        ASC, DESC
    }
}