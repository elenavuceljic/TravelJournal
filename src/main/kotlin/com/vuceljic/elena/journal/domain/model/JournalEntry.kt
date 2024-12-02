package com.vuceljic.elena.journal.domain.model

import java.time.Instant

data class JournalEntry(
    val id: Long?,
    val title: String,
    val description: String,
    val entryDate: Instant,
    val userId: String,
)
