package com.vuceljic.elena.journal.application.services

import com.vuceljic.elena.journal.domain.model.Sorting
import com.vuceljic.elena.journal.domain.repository.JournalRepository
import com.vuceljic.elena.journal.presentation.dto.JournalEntryDto
import com.vuceljic.elena.journal.presentation.dto.toDto
import com.vuceljic.elena.journal.presentation.http.request.JournalEntryCreateUpdateRequest
import com.vuceljic.elena.journal.presentation.http.request.SortingOrderQueryParam
import com.vuceljic.elena.journal.presentation.http.request.toDomain
import com.vuceljic.elena.journal.presentation.http.respose.PaginatedResponse
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class JournalService {

    @Inject
    lateinit var journalRepository: JournalRepository

    fun getAll(
        page: Int,
        pageSize: Int,
        byEntryDate: SortingOrderQueryParam?,
        byTitle: SortingOrderQueryParam?
    ): PaginatedResponse<JournalEntryDto> {
        val sort = Sorting(byEntryDate?.toDomain(), byTitle?.toDomain())
        val entryDtoList = journalRepository.getAll(page, pageSize, sort).map { it.toDto() }
        val totalItems = journalRepository.countAll()
        val totalPages = totalItems.ceilDiv(pageSize)
        return PaginatedResponse(entryDtoList, page, totalPages, totalItems)
    }

    private fun Long.ceilDiv(divisor: Int): Int {
        return ((this + divisor - 1) / divisor).toInt()
    }

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