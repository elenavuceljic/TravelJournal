package com.vuceljic.elena

import com.vuceljic.elena.journal.presentation.rest.JournalResource
import io.quarkus.test.common.http.TestHTTPEndpoint
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.security.TestSecurity
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder


@QuarkusTest
@TestHTTPEndpoint(JournalResource::class)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestSecurity(authorizationEnabled = false)
open class JournalIntegrationTest {

    @Order(1)
    @Test
    fun testGetAllJournalEntries() {
        Given {
            queryParam("page", 2)
            queryParam("size", 1)
        } When {
            get("")
        } Then {
            statusCode(200)
            body("items[0].id", equalTo(3))
            body("currentPage", equalTo(2))
            body("totalPages", equalTo(3))
            body("totalItems", equalTo(3))
        }
    }

    @Order(1)
    @Test
    @TestSecurity(authorizationEnabled = true)
    fun testGetAllJournalEntriesWithoutAuth() {
        Given {
            queryParam("page", 2)
            queryParam("size", 1)
        } When {
            get("")
        } Then {
            statusCode(401)
        }
    }

    @Order(2)
    @Test
    fun testGetAllJournalEntriesDefaultParams() {
        When {
            get("")
        } Then {
            statusCode(200)
            body("items[0].id", equalTo(1))
            body("items[1].id", equalTo(2))
            body("items[2].id", equalTo(3))
            body("currentPage", equalTo(0))
            body("totalPages", equalTo(1))
            body("totalItems", equalTo(3))
        }
    }

    @Order(3)
    @Test
    fun testGetAllJournalEntriesSortByEntryDateAsc() {
        Given {
            queryParam("sortByEntryDate", "ASC")
        } When {
            get("")
        } Then {
            statusCode(200)
            body("items[0].id", equalTo(3))
            body("items[1].id", equalTo(2))
            body("items[2].id", equalTo(1))
            body("currentPage", equalTo(0))
            body("totalPages", equalTo(1))
            body("totalItems", equalTo(3))
        }
    }

    @Order(4)
    @Test
    fun testGetAllJournalEntriesSortByEntryDateDesc() {
        Given {
            queryParam("sortByEntryDate", "DESC")
        } When {
            get("")
        } Then {
            statusCode(200)
            body("items[0].id", equalTo(1))
            body("items[1].id", equalTo(2))
            body("items[2].id", equalTo(3))
            body("currentPage", equalTo(0))
            body("totalPages", equalTo(1))
            body("totalItems", equalTo(3))
        }
    }

    @Order(5)
    @Test
    fun testGetAllJournalEntriesSortByTitleDesc() {
        Given {
            queryParam("sortByTitle", "DESC")
        } When {
            get("")
        } Then {
            statusCode(200)
            body("items[0].id", equalTo(3))
            body("items[1].id", equalTo(2))
            body("items[2].id", equalTo(1))
            body("currentPage", equalTo(0))
            body("totalPages", equalTo(1))
            body("totalItems", equalTo(3))
        }
    }

    @Order(6)
    @Test
    fun testGetAllJournalEntriesSortByTitleAsc() {
        Given {
            queryParam("sortByTitle", "ASC")
        } When {
            get("")
        } Then {
            statusCode(200)
            body("items[0].id", equalTo(1))
            body("items[1].id", equalTo(2))
            body("items[2].id", equalTo(3))
            body("currentPage", equalTo(0))
            body("totalPages", equalTo(1))
            body("totalItems", equalTo(3))
        }
    }

    @Order(7)
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

    @Order(7)
    @Test
    @TestSecurity(authorizationEnabled = true)
    fun testGetJournalEntryWithoutAuth() {
        Given {
            pathParam("id", "1")
        } When {
            get("/{id}")
        } Then {
            statusCode(401)
        }
    }

    @Order(8)
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

    @Order(9)
    @Test
    fun testSearchAllJournalEntries() {
        Given {
            queryParam("query", "land")
        } When {
            get("")
        } Then {
            statusCode(200)
            body("items[0].id", equalTo(1))
            body("items[1].id", equalTo(2))
            body("currentPage", equalTo(0))
            body("totalPages", equalTo(1))
            body("totalItems", equalTo(2))
        }
    }

    @Order(9)
    @Test
    fun testSearchAllJournalEntriesPagination() {
        Given {
            queryParam("query", "land")
            queryParam("page", 1)
            queryParam("size", 1)
        } When {
            get("")
        } Then {
            statusCode(200)
            body("items[0].id", equalTo(2))
            body("currentPage", equalTo(1))
            body("totalPages", equalTo(2))
            body("totalItems", equalTo(2))
        }
    }

    @Order(9)
    @Test
    fun testSearchAllJournalEntriesNoResultFound() {
        Given {
            queryParam("query", "xyz")
        } When {
            get("")
        } Then {
            statusCode(200)
            body("currentPage", equalTo(0))
            body("totalPages", equalTo(0))
            body("totalItems", equalTo(0))
        }
    }

    @Order(9)
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

    @Order(9)
    @Test
    @TestSecurity(authorizationEnabled = true)
    fun testDeleteJournalEntryWithoutAuth() {
        Given {
            pathParam("id", "3")
        } When {
            delete("/{id}")
        } Then {
            statusCode(401)
        }
    }

    @Order(10)
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

    @Order(11)
    @Test
    fun testUpdateJournalEntry() {
        Given {
            pathParam("id", "2")
            contentType(ContentType.JSON)
            body(testJournalEntry)
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

    @Order(11)
    @Test
    @TestSecurity(authorizationEnabled = true)
    fun testUpdateJournalEntryWithoutAuth() {
        Given {
            pathParam("id", "2")
            contentType(ContentType.JSON)
            body(testJournalEntry)
        } When {
            put("/{id}")
        } Then {
            statusCode(401)
        }
    }

    @Order(12)
    @Test
    fun testUpdateJournalEntryWithTitleTooLong() {
        Given {
            pathParam("id", "2")
            contentType(ContentType.JSON)
            body(testJournalEntryWithTitleTooLong)
        } When {
            put("/{id}")
        } Then {
            statusCode(400)
            body("errors[0].property", equalTo("title"))
            body("errors[0].message", equalTo("Title must be between 1 and 40 characters"))
            body(
                "errors[0].invalidValue",
                equalTo("Tenerife Tenerife Tenerife Tenerife Tenerife Tenerife Tenerife Tenerife Tenerife Tenerife Tenerife")
            )
        }
    }

    @Order(13)
    @Test
    fun testUpdateJournalEntryNotFound() {
        Given {
            pathParam("id", "5")
            contentType(ContentType.JSON)
            body(testJournalEntry)
        } When {
            put("/{id}")
        } Then {
            statusCode(404)
        }
    }

    @Order(14)
    @Test
    fun testCreateJournalEntry() {
        Given {
            contentType(ContentType.JSON)
            body(testJournalEntry)
        } When {
            post("")
        } Then {
            statusCode(201)
            header("Location", "http://localhost:8083/journal/4")
        }
    }

    @Order(15)
    @Test
    fun testCreateJournalEntryWithTitleTooLong() {
        Given {
            contentType(ContentType.JSON)
            body(testJournalEntryWithTitleTooLong)
        } When {
            post("")
        } Then {
            statusCode(400)
            body("errors[0].property", equalTo("title"))
            body("errors[0].message", equalTo("Title must be between 1 and 40 characters"))
            body(
                "errors[0].invalidValue",
                equalTo("Tenerife Tenerife Tenerife Tenerife Tenerife Tenerife Tenerife Tenerife Tenerife Tenerife Tenerife")
            )
        }
    }

    @Order(15)
    @Test
    @TestSecurity(authorizationEnabled = true)
    fun testCreateJournalEntryWithoutAuth() {
        Given {
            contentType(ContentType.JSON)
            body(testJournalEntry)
        } When {
            post("")
        } Then {
            statusCode(401)
        }
    }

    private val testJournalEntry = """
        {
          "title": "Tenerife",
          "description": "Festival circling",
          "entryDate": "1995-09-15T00:00:00Z"
        }
    """.trimIndent()

    private val testJournalEntryWithTitleTooLong = """
        {
          "title": "Tenerife Tenerife Tenerife Tenerife Tenerife Tenerife Tenerife Tenerife Tenerife Tenerife Tenerife",
          "description": "Festival circling",
          "entryDate": "1995-09-15T00:00:00Z"
        }
    """.trimIndent()
}