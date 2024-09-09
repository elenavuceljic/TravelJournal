package com.vuceljic.elena.journal.application.services

import com.vuceljic.elena.journal.presentation.dto.JournalEntryDto
import com.vuceljic.elena.journal.presentation.dto.toDto
import com.vuceljic.elena.journal.presentation.http.request.JournalEntryCreateUpdateRequest
import com.vuceljic.elena.journal.presentation.http.request.toDomain
import com.vuceljic.elena.journal.domain.repository.JournalRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class JournalService {

    @Inject
    private lateinit var journalRepository: JournalRepository

    fun findEntryById(id: Long): JournalEntryDto? {
        return journalRepository.find(id)?.toDto()
    }

    fun deleteEntryById(id: Long): Boolean {
        return journalRepository.delete(id)
    }

    fun updateEntry(id: Long, updateRequest: JournalEntryCreateUpdateRequest): JournalEntryDto? {
        val updatedEntry = journalRepository.update(id, updateRequest.toDomain())
        return updatedEntry?.toDto()
    }

    fun insertEntry(journalEntryDto: JournalEntryCreateUpdateRequest): Long? {
        val newEntry = journalEntryDto.toDomain()
        return journalRepository.insert(newEntry)
    }
}