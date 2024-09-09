package com.vuceljic.elena.journal.presentation.dto

import com.vuceljic.elena.journal.domain.model.JournalEntry
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class JournalEntryDto(
    val id: Long,
    val title: String,
    val description: String,
    @Serializable(with = InstantSerializer::class)
    val entryDate: Instant,
)

fun JournalEntry.toDto(): JournalEntryDto = JournalEntryDto(id ?: 0, title, description, entryDate)
