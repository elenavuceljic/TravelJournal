package com.vuceljic.elena

import com.vuceljic.elena.journal.resource.JournalResource
import io.quarkus.test.common.http.TestHTTPEndpoint
import io.quarkus.test.junit.QuarkusTest
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

@QuarkusTest
@TestHTTPEndpoint(JournalResource::class)
open class JournalEntryIntegrationTest {

    @Test
    fun testGetJournalEntry() {
        Given {
            pathParam("id", "1")
        } When {
            get("/{id}")
        } Then {
            statusCode(200)
            body("id", equalTo(1))
            body("title", equalTo("Iceland"))
            body("description", equalTo("Aurora Borealis hunting"))
            body("entryDate", equalTo("1995-09-12T00:00:00Z"))
        }
    }

    @Test
    fun testGetJournalEntryNotFound() {
        Given {
            pathParam("id", "5")
        } When {
            get("/{id}")
        } Then {
            statusCode(404)
        }
    }

    @Test
    fun testDeleteJournalEntry() {
        Given {
            pathParam("id", "3")
        } When {
            delete("/{id}")
        } Then {
            statusCode(204)
        }
    }

    @Test
    fun testDeleteJournalEntryNotFound() {
        Given {
            pathParam("id", "5")
        } When {
            delete("/{id}")
        } Then {
            statusCode(404)
        }
    }

    @Test
    fun testUpdateJournalEntry() {
        Given {
            pathParam("id", "2")
            contentType(ContentType.JSON)
            body(testJournalEntryString)
        } When {
            put("/{id}")
        } Then {
            statusCode(200)
            body("id", equalTo(2))
            body("title", equalTo("Tenerife"))
            body("description", equalTo("Festival circling"))
            body("entryDate", equalTo("1995-09-15T00:00:00Z"))
        }
    }

    @Test
    fun testUpdateJournalEntryNotFound() {
        Given {
            pathParam("id", "5")
            contentType(ContentType.JSON)
            body(testJournalEntryString)
        } When {
            put("/{id}")
        } Then {
            statusCode(404)
        }
    }

    @Test
    fun testCreateJournalEntry() {
        Given {
            contentType(ContentType.JSON)
            body(testJournalEntryString)
        } When {
            post("")
        } Then {
            statusCode(201)
            header("Location", "http://localhost:8083/journal/4")
        }
    }

    private val testJournalEntryString = """
        {
          "title": "Tenerife",
          "description": "Festival circling",
          "entryDate": "1995-09-15T00:00:00Z"
        }
    """.trimIndent()

}