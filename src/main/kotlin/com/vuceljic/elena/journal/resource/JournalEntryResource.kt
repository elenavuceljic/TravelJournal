package com.vuceljic.elena.journal.resource

import com.vuceljic.elena.journal.dto.JournalEntryDto
import com.vuceljic.elena.journal.dto.toDto
import com.vuceljic.elena.journal.dto.toEntity
import com.vuceljic.elena.journal.repository.JournalEntryRepository
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.net.URI

@Path("/journal")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class JournalEntryResource {

    @Inject
    lateinit var journalEntryRepository: JournalEntryRepository

    @GET
    @Path("/{id}")
    fun get(id: Long): JournalEntryDto {
        return journalEntryRepository.findById(id)?.toDto() ?: throw NotFoundException("No journal entry with id $id")
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    fun delete(id: Long) {
        val deleted = journalEntryRepository.deleteById(id)
        if (!deleted) {
            throw NotFoundException("No journal entry with id $id")
        }
    }

    @PUT
    @Path("/{id}")
    @Transactional
    fun update(id: Long, updatedJournalEntry: JournalEntryDto): JournalEntryDto {
        val entry = journalEntryRepository.findById(id)
        entry ?: throw NotFoundException("No journal entry with id $id")

        entry.title = updatedJournalEntry.title
        entry.description = updatedJournalEntry.description
        entry.entryDate = updatedJournalEntry.entryDate

        return entry.toDto()
    }

    @POST
    @Transactional
    fun create(journalEntry: JournalEntryDto): Response {
        val entity = journalEntry.toEntity()
        journalEntryRepository.persistAndFlush(entity)
        return Response.created(URI.create("/journal/" + entity.id)).build()
    }

}