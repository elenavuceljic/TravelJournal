package com.vuceljic.elena.journal.infrastructure.persistence

import com.vuceljic.elena.journal.domain.model.JournalEntry
import com.vuceljic.elena.journal.domain.model.Sorting
import com.vuceljic.elena.journal.domain.model.Sorting.SortingOrder
import com.vuceljic.elena.journal.domain.repository.JournalRepository
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import io.quarkus.panache.common.Page
import io.quarkus.panache.common.Sort
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class JournalRepositoryImpl : PanacheRepository<JournalEntryEntity>, JournalRepository {

    companion object {
        private const val QUERY_SEARCH_TITLE_AND_DESCRIPTION = "title like ?1 or description like ?1 and userId = ?2"
        private const val QUERY_BY_USER_ID = "userId = ?1"
    }

    override fun getAll(
        page: Int,
        pageSize: Int,
        searchQuery: String?,
        sort: Sorting,
        userId: String
    ): List<JournalEntry> {
        val panacheSort = parseSort(sort)
        val query = if (searchQuery == null) {
            find(QUERY_BY_USER_ID, panacheSort, userId)
        } else {
            find(QUERY_SEARCH_TITLE_AND_DESCRIPTION, panacheSort, "%$searchQuery%", userId)
        }
        query.page(Page.of(page, pageSize))
        val list = query.list()
        return list.map { it.toDomain() }
    }

    private fun parseSort(sort: Sorting): Sort {
        return when {
            sort.byEntryDate == SortingOrder.ASC -> Sort.by("entryDate").ascending()
            sort.byEntryDate == SortingOrder.DESC -> Sort.by("entryDate").descending()
            sort.byTitle == SortingOrder.ASC -> Sort.by("title").ascending()
            sort.byTitle == SortingOrder.DESC -> Sort.by("title").descending()
            else -> Sort.by("entryDate").descending()
        }
    }

    override fun find(id: Long, userId: String): JournalEntry? {
        val entity = findById(id)
        if (entity?.userId != userId) return null
        return entity.toDomain()
    }

    override fun delete(id: Long, userId: String): Boolean {
        val entity = findById(id)
        if (entity?.userId != userId) return false
        return deleteById(id)
    }

    override fun update(id: Long, updatedEntry: JournalEntry): JournalEntry? {
        val entity = findById(id) ?: return null
        if (entity.userId != updatedEntry.userId) return null

        entity.title = updatedEntry.title
        entity.description = updatedEntry.description
        entity.entryDate = updatedEntry.entryDate

        persist(entity)
        return entity.toDomain()
    }

    override fun insert(newEntry: JournalEntry): Long? {
        val entity = newEntry.toEntity()
        persist(entity)
        return entity.id
    }

    override fun countAll(query: String?, userId: String): Long {
        return if (query != null) {
            count(QUERY_SEARCH_TITLE_AND_DESCRIPTION, "%$query%", userId)
        } else {
            count(QUERY_BY_USER_ID, userId)
        }
    }
}