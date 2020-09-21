package com.example.api

import com.example.ApiTest
import com.example.infra.test.support.RollbackTestSupport
import com.fasterxml.jackson.databind.JsonNode
import kotlinx.coroutines.reactive.awaitFirst
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult

@ApiTest
class LocationApiTest(
    val webClient: WebTestClient
) : RollbackTestSupport() {

    @Test
    fun list() = withRollback {
        webClient.get().uri("/api/v1/locations")
            .exchange()
            .expectStatus().isOk
            .returnResult<JsonNode>().also(::println)
    }

    @Test
    fun head() = withRollback {
        webClient.head().uri("/api/v1/locations/1")
            .exchange()
            .expectStatus().isOk
    }

    @Test
    fun get() = withRollback {
        webClient.get().uri("/api/v1/locations/1")
            .exchange()
            .expectStatus().isOk
            .returnResult<JsonNode>().responseBody.awaitFirst().also(::println)
    }

    @Test
    fun create() = withRollback {
        val body = mapOf(
            "name" to "test",
            "address" to "test addr"
        )

        val res = webClient.post().uri("/api/v1/locations")
            .bodyValue(body)
            .exchange()
            .expectStatus().isCreated
            .returnResult<JsonNode>().responseBody.awaitFirst().also(::println)

        val id = res["id"].asLong()
        val uri = res["uri"].asText()

        webClient.head().uri("/api/v1/locations/$id")
            .exchange()
            .expectStatus().isOk

        webClient.head().uri(uri)
            .exchange()
            .expectStatus().isOk
    }

    @Test
    fun update() = withRollback {
        val uri = "/api/v1/locations/1"
        val body = mapOf(
            "name" to "test",
            "address" to "test addr"
        )

        webClient.put().uri(uri)
            .bodyValue(body)
            .exchange()
            .expectStatus().isOk

        webClient.get().uri(uri)
            .exchange()
            .expectBody()
            .jsonPath("name").isEqualTo("test")
    }

    @Test
    fun patch() = withRollback {
        val uri = "/api/v1/locations/1"
        val body = mapOf(
            "name" to "test"
        )

        webClient.patch().uri(uri)
            .bodyValue(body)
            .exchange()
            .expectStatus().isOk

        webClient.get().uri(uri)
            .exchange()
            .expectBody()
            .jsonPath("name").isEqualTo("test")
    }
}