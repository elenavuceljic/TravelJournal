package com.vuceljic.elena.journal.infrastructure.persistence

import com.vuceljic.elena.journal.domain.model.JournalEntry
import jakarta.persistence.*
import jakarta.validation.constraints.Size
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.SourceType
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Entity(name = "journal_entry")
open class JournalEntryEntity {
    @Id
    @SequenceGenerator(
        name = "journal_entry_seq",
        sequenceName = "journal_entry_id_seq",
        allocationSize = 1,
        initialValue = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "journal_entry_seq")
    var id: Long? = null

    @Column(length = 40)
    @field:Size(min = 1, max = 40, message = "Title must be between 1 and 40 characters")
    lateinit var title: String

    lateinit var description: String

    lateinit var entryDate: Instant

    @field:CreationTimestamp(source = SourceType.DB)
    lateinit var createdAt: Instant

    @field:UpdateTimestamp(source = SourceType.DB)
    lateinit var updatedAt: Instant

    lateinit var userId: String
}

fun JournalEntryEntity.toDomain(): JournalEntry = JournalEntry(
    id = id!!,
    title = title,
    description = description,
    entryDate = entryDate,
    userId = userId
)

fun JournalEntry.toEntity(): JournalEntryEntity = JournalEntryEntity().also {
    it.id = id
    it.title = title
    it.description = description
    it.entryDate = entryDate
}

