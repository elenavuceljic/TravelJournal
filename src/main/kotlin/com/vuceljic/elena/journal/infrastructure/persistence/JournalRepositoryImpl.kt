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
    override fun getAll(page: Int, pageSize: Int, sort: Sorting): List<JournalEntry> {
        val query = findAll(parseSort(sort))
        query.page(Page.of(page, pageSize))
        return query.list().map { it.toDomain() }
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

    override fun find(id: Long): JournalEntry? = findById(id)?.toDomain()

    override fun delete(id: Long): Boolean = deleteById(id)

    override fun update(id: Long, updatedEntry: JournalEntry): JournalEntry? {
        val entity = findById(id) ?: return null

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

    override fun countAll(): Long = count()
}