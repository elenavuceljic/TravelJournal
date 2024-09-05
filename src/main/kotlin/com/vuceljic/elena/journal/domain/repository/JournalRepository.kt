package com.vuceljic.elena.journal.domain.repository

import com.vuceljic.elena.journal.domain.model.JournalEntry
import com.vuceljic.elena.journal.domain.model.Sorting

interface JournalRepository {
    fun getAll(page: Int, pageSize: Int, sort: Sorting): List<JournalEntry>
    fun find(id: Long): JournalEntry?
    fun delete(id: Long): Boolean
    fun update(id: Long, updatedEntry: JournalEntry): JournalEntry?
    fun insert(newEntry: JournalEntry): Long?
    fun countAll(): Long
}