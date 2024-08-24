package com.vuceljic.elena

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

@QuarkusTest
open class JournalEntryEntityResourceTest {

    @Test
    fun testGetJournalEntry() {
        given()
            .pathParam("id", "1")
            .`when`().get("/journal/{id}")
            .then()
            .statusCode(200)
            .body("id", equalTo(1))
            .body("title", equalTo("Iceland"))
            .body("description", equalTo("Aurora Borealis hunting"))
            .body("entryDate", equalTo("1995-09-12T00:00:00"))
    }

    @Test
    fun testGetJournalEntryNotFound() {
        given()
            .pathParam("id", "5")
            .`when`().get("/journal/{id}")
            .then()
            .statusCode(404)
    }

    @Test
    fun testDeleteJournalEntry() {
        given()
            .pathParam("id", "3")
            .`when`().delete("/journal/{id}")
            .then()
            .statusCode(204)
    }

    @Test
    fun testDeleteJournalEntryNotFound() {
        given()
            .pathParam("id", "5")
            .`when`().delete("/journal/{id}")
            .then()
            .statusCode(404)
    }

    @Test
    fun testUpdateJournalEntry() {
        given()
            .pathParam("id", "2")
            .contentType(ContentType.JSON)
            .body(testJournalEntryString)
            .`when`().put("/journal/{id}")
            .then()
            .statusCode(200)
            .body("id", equalTo(2))
            .body("title", equalTo("Tenerife"))
            .body("description", equalTo("Festival circling"))
            .body("entryDate", equalTo("1995-09-15T00:00:00"))
    }

    @Test
    fun testUpdateJournalEntryNotFound() {
        given()
            .pathParam("id", "5")
            .contentType(ContentType.JSON)
            .body(testJournalEntryString)
            .`when`().put("/journal/{id}")
            .then()
            .statusCode(404)
    }

    @Test
    fun testCreateJournalEntry() {
        given()
            .contentType(ContentType.JSON)
            .body(testJournalEntryString)
            .`when`().post("/journal")
            .then()
            .statusCode(201)
            .header("Location", "http://localhost:8081/journal/4")
    }

    private val testJournalEntryString = """
        {
          "title": "Tenerife",
          "description": "Festival circling",
          "entryDate": "1995-09-15T00:00:00"
        }
    """.trimIndent()

}