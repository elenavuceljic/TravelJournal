package com.vuceljic.elena.journal.presentation.http.respose

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(val errors: List<ConstraintViolationError>)

@Serializable
data class ConstraintViolationError(
    val property: String,
    val message: String,
    val invalidValue: String
)