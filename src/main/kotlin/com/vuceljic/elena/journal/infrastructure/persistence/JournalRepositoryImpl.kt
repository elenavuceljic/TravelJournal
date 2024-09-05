package com.vuceljic.elena.journal.infrastructure.persistence

import com.vuceljic.elena.journal.domain.model.JournalEntry
import com.vuceljic.elena.journal.domain.repository.JournalRepository
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import io.quarkus.panache.common.Page
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class JournalRepositoryImpl : PanacheRepository<JournalEntryEntity>, JournalRepository {
    override fun getAll(page: Int, pageSize: Int): List<JournalEntry> {
        val query = findAll()
        query.page(Page.of(page, pageSize))
        return query.list().map { it.toDomain() }
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