package com.vuceljic.elena.journal.domain.repository

import com.vuceljic.elena.journal.domain.model.JournalEntry

interface JournalRepository {
    fun getAll(page: Int, pageSize: Int): List<JournalEntry>
    fun find(id: Long): JournalEntry?
    fun delete(id: Long): Boolean
    fun update(id: Long, updatedEntry: JournalEntry): JournalEntry?
    fun insert(newEntry: JournalEntry): Long?
}