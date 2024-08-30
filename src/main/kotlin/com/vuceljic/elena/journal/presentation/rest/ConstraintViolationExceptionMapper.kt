package com.vuceljic.elena.journal.presentation.rest

import com.vuceljic.elena.journal.presentation.http.respose.ConstraintViolationError
import com.vuceljic.elena.journal.presentation.http.respose.ErrorResponse
import jakarta.validation.ConstraintViolationException
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class ConstraintViolationExceptionMapper : ExceptionMapper<ConstraintViolationException> {

    override fun toResponse(exception: ConstraintViolationException): Response {
        val errors = exception.constraintViolations.map { violation ->
            ConstraintViolationError(
                property = violation.propertyPath.toString(),
                message = violation.message,
                invalidValue = violation.invalidValue.toString()
            )
        }
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(ErrorResponse(errors))
            .build()
    }
}
