package com.vuceljic.elena.journal.presentation.rest

import com.vuceljic.elena.journal.application.services.JournalService
import com.vuceljic.elena.journal.presentation.dto.JournalEntryDto
import com.vuceljic.elena.journal.presentation.http.request.JournalEntryCreateUpdateRequest
import com.vuceljic.elena.journal.presentation.http.request.SortingOrderQueryParam
import com.vuceljic.elena.journal.presentation.http.respose.PaginatedResponse
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.validation.constraints.Min
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.net.URI

@Path("/journal")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class JournalResource {

    companion object {
        private const val DEFAULT_PAGE = 0
        private const val DEFAULT_PAGE_SIZE = 25
    }

    @Inject
    lateinit var journalService: JournalService

    @GET
    fun getAll(
        @QueryParam("page") @DefaultValue("$DEFAULT_PAGE") @Min(0) page: Int,
        @QueryParam("size") @DefaultValue("$DEFAULT_PAGE_SIZE") @Min(1) pageSize: Int,
        @QueryParam("query") query: String?,
        @QueryParam("sortByEntryDate") sortByEntryDate: SortingOrderQueryParam?,
        @QueryParam("sortByTitle") sortByTitle: SortingOrderQueryParam?
    ): PaginatedResponse<JournalEntryDto> {
        return journalService.getAll(page, pageSize, query, sortByEntryDate, sortByTitle)
    }

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
        return journalService.updateEntry(id, updatedJournalEntry)
            ?: throw NotFoundException("No journal entry with id $id")
    }

    @POST
    @Transactional
    fun create(newJournalEntry: JournalEntryCreateUpdateRequest): Response {
        val insertedEntityId = journalService.insertEntry(newJournalEntry)
        return Response.created(URI.create("/journal/$insertedEntityId")).build()
    }
}