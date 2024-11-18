package com.vuceljic.elena.journal.application.services

import com.vuceljic.elena.journal.domain.model.Sorting
import com.vuceljic.elena.journal.domain.repository.JournalRepository
import com.vuceljic.elena.journal.presentation.dto.JournalEntryDto
import com.vuceljic.elena.journal.presentation.dto.toDto
import com.vuceljic.elena.journal.presentation.http.request.JournalEntryCreateUpdateRequest
import com.vuceljic.elena.journal.presentation.http.request.SortingOrderQueryParam
import com.vuceljic.elena.journal.presentation.http.request.toDomain
import com.vuceljic.elena.journal.presentation.http.respose.PaginatedResponse
import io.quarkus.security.identity.SecurityIdentity
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class JournalService {

    @Inject
    private lateinit var journalRepository: JournalRepository

    @Inject
    private lateinit var securityIdentity: SecurityIdentity

    fun getAll(
        page: Int,
        pageSize: Int,
        query: String?,
        byEntryDate: SortingOrderQueryParam?,
        byTitle: SortingOrderQueryParam?
    ): PaginatedResponse<JournalEntryDto> {
        val userId = securityIdentity.principal.name
        val sort = Sorting(byEntryDate?.toDomain(), byTitle?.toDomain())
        val entryDtoList = journalRepository.getAll(page, pageSize, query, sort, userId).map { it.toDto() }
        val totalItems = journalRepository.countAll(query, userId)
        val totalPages = totalItems.ceilDiv(pageSize)
        return PaginatedResponse(entryDtoList, page, totalPages, totalItems)
    }

    private fun Long.ceilDiv(divisor: Int): Int {
        return ((this + divisor - 1) / divisor).toInt()
    }

    fun findEntryById(id: Long): JournalEntryDto? {
        val userId = securityIdentity.principal.name
        return journalRepository.find(id, userId)?.toDto()
    }

    fun deleteEntryById(id: Long): Boolean {
        val userId = securityIdentity.principal.name
        return journalRepository.delete(id, userId)
    }

    fun updateEntry(id: Long, updateRequest: JournalEntryCreateUpdateRequest): JournalEntryDto? {
        val userId = securityIdentity.principal.name
        val updatedEntry = journalRepository.update(id, updateRequest.toDomain(userId))
        return updatedEntry?.toDto()
    }

    fun insertEntry(journalEntryDto: JournalEntryCreateUpdateRequest): Long? {
        val userId = securityIdentity.principal.name
        val newEntry = journalEntryDto.toDomain(userId)
        return journalRepository.insert(newEntry)
    }
}