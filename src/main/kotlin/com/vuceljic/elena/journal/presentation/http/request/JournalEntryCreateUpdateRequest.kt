package com.vuceljic.elena.journal.presentation.http.request

import com.vuceljic.elena.journal.presentation.dto.InstantSerializer
import com.vuceljic.elena.journal.domain.model.JournalEntry
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class JournalEntryCreateUpdateRequest(
    val title: String,
    val description: String,
    @Serializable(with = InstantSerializer::class)
    val entryDate: Instant,
)

fun JournalEntryCreateUpdateRequest.toDomain(userId: String): JournalEntry = JournalEntry(
    id = null,
    title = this.title,
    description = this.description,
    entryDate = this.entryDate,
    userId = userId
)
