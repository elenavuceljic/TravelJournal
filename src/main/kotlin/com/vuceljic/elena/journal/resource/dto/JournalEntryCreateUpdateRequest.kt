package com.vuceljic.elena.journal.resource.dto

import com.vuceljic.elena.journal.repository.model.JournalEntryEntity
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class JournalEntryCreateUpdateRequest(
    val title: String,
    val description: String,
    @Serializable(with = InstantSerializer::class)
    val entryDate: Instant,
)

fun JournalEntryCreateUpdateRequest.toEntity(): JournalEntryEntity = JournalEntryEntity().also {
    it.title = this.title
    it.description = this.description
    it.entryDate = this.entryDate
}
