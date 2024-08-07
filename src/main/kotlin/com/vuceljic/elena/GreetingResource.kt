package com.vuceljic.elena

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/")
class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/hello/{name}")
    fun hello(name: String) = "$name, Hello from Quarkus REST"
}