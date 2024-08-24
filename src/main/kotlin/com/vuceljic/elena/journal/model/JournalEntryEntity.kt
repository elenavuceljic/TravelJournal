package com.vuceljic.elena.journal.model

import com.vuceljic.elena.journal.dto.InstantSerializer
import com.vuceljic.elena.journal.dto.LocalDateTimeSerializer
import jakarta.persistence.*
import kotlinx.serialization.Serializable
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.SourceType
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import java.time.LocalDateTime

@Serializable
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

    lateinit var title: String

    lateinit var description: String

    @Serializable(with = LocalDateTimeSerializer::class)
    lateinit var entryDate: LocalDateTime

    @field:CreationTimestamp(source = SourceType.DB)
    @Serializable(with = InstantSerializer::class)
    lateinit var createdAt: Instant

    @field:UpdateTimestamp(source = SourceType.DB)
    @Serializable(with = InstantSerializer::class)
    lateinit var updatedAt: Instant
}