package com.vuceljic.elena.journal.domain.repository

import com.vuceljic.elena.journal.domain.model.JournalEntry
import com.vuceljic.elena.journal.domain.model.Sorting

interface JournalRepository {
    fun getAll(page: Int, pageSize: Int, searchQuery: String?, sort: Sorting, userId: String): List<JournalEntry>
    fun find(id: Long, userId: String): JournalEntry?
    fun delete(id: Long, userId: String): Boolean
    fun update(id: Long, updatedEntry: JournalEntry): JournalEntry?
    fun insert(newEntry: JournalEntry): Long?
    fun countAll(query: String?, userId: String): Long
}