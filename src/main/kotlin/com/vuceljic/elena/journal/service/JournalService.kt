package com.vuceljic.elena.journal.service

import com.vuceljic.elena.journal.repository.JournalRepository
import com.vuceljic.elena.journal.resource.dto.JournalEntryCreateUpdateRequest
import com.vuceljic.elena.journal.resource.dto.JournalEntryDto
import com.vuceljic.elena.journal.resource.dto.toDto
import com.vuceljic.elena.journal.resource.dto.toEntity
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class JournalService {

    @Inject
    lateinit var journalRepository: JournalRepository

    fun findEntryById(id: Long): JournalEntryDto? {
        return journalRepository.findById(id)?.toDto()
    }

    fun deleteEntryById(id: Long): Boolean {
        return journalRepository.deleteById(id)
    }

    fun updateEntry(id: Long, updateRequest: JournalEntryCreateUpdateRequest): JournalEntryDto? {
        val entity = journalRepository.findById(id)
        entity ?: return null

        entity.title = updateRequest.title
        entity.description = updateRequest.description
        entity.entryDate = updateRequest.entryDate

        journalRepository.persist(entity)

        return entity.toDto()
    }

    fun insertEntry(journalEntryDto: JournalEntryCreateUpdateRequest): Long? {
        val newEntity = journalEntryDto.toEntity()
        journalRepository.persist(newEntity)
        return newEntity.id
    }
}