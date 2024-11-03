package com.vuceljic.elena

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.parsing.Parser
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test

@QuarkusTest
open class UserIntegrationTest {
    @Test
    fun testPostLogout() {
        RestAssured.registerParser("text/plain", Parser.TEXT)

        When {
            get("/post-logout")
        } Then {
            statusCode(200)
            body(equalTo("You were logged out"))
        }
    }
}