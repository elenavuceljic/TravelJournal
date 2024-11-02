package com.vuceljic.elena.user.presentation.rest

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("")
class UserResource {

    @GET
    @Path("post-logout")
    @Produces(MediaType.TEXT_PLAIN)
    fun postLogout(): String {
        return "You were logged out"
    }
}