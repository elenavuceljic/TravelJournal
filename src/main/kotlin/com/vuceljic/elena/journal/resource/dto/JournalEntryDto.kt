package com.vuceljic.elena.journal.resource.dto

import com.vuceljic.elena.journal.repository.model.JournalEntryEntity
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class JournalEntryDto(
    val id: Long? = null,
    val title: String,
    val description: String,
    @Serializable(with = InstantSerializer::class)
    val entryDate: Instant,
)

fun JournalEntryEntity.toDto(): JournalEntryDto = JournalEntryDto(id, title, description, entryDate)
