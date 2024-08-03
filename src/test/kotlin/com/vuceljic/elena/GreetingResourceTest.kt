package com.vuceljic.elena

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test

@QuarkusTest
open class GreetingResourceTest {

    @Test
    fun testHelloEndpoint() {
        given()
          .`when`().get("/hello/Elena")
          .then()
             .statusCode(200)
             .body(`is`("Elena, Hello from Quarkus REST"))
    }

}