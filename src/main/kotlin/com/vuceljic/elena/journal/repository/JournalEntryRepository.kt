package com.vuceljic.elena.journal.repository

import com.vuceljic.elena.journal.model.JournalEntryEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class JournalEntryRepository: PanacheRepository<JournalEntryEntity>