package com.vuceljic.elena.journal.repository

import com.vuceljic.elena.journal.repository.model.JournalEntryEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class JournalRepository: PanacheRepository<JournalEntryEntity>