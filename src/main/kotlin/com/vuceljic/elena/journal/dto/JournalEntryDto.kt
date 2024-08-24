package com.vuceljic.elena.journal.dto

import com.vuceljic.elena.journal.model.JournalEntryEntity
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class JournalEntryDto(
    val id: Long? = null,
    val title: String,
    val description: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val entryDate: LocalDateTime
)

fun JournalEntryDto.toEntity(): JournalEntryEntity = JournalEntryEntity().also {
    it.title = this.title
    it.description = this.description
    it.entryDate = this.entryDate
}

fun JournalEntryEntity.toDto(): JournalEntryDto = JournalEntryDto(id, title, description, entryDate)

