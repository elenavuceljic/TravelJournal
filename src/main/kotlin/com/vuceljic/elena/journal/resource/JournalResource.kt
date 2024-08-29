package com.vuceljic.elena.journal.resource

import com.vuceljic.elena.journal.resource.dto.JournalEntryCreateUpdateRequest
import com.vuceljic.elena.journal.resource.dto.JournalEntryDto
import com.vuceljic.elena.journal.service.JournalService
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.net.URI

@Path("/journal")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class JournalResource {

    @Inject
    lateinit var journalService: JournalService

    @GET
    @Path("/{id}")
    fun get(id: Long): JournalEntryDto {
        return journalService.findEntryById(id) ?: throw NotFoundException("No journal entry with id $id")
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    fun delete(id: Long) {
        val deleted = journalService.deleteEntryById(id)
        if (!deleted) {
            throw NotFoundException("No journal entry with id $id")
        }
    }

    @PUT
    @Path("/{id}")
    @Transactional
    fun update(id: Long, updatedJournalEntry: JournalEntryCreateUpdateRequest): JournalEntryDto {
        return journalService.updateEntry(id, updatedJournalEntry) ?: throw NotFoundException("No journal entry with id $id")
    }

    @POST
    @Transactional
    fun create(newJournalEntry: JournalEntryCreateUpdateRequest): Response {
        val insertedEntityId = journalService.insertEntry(newJournalEntry)
        return Response.created(URI.create("/journal/$insertedEntityId")).build()
    }
}
