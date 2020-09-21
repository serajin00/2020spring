package com.example.api

import com.example.ApiTest
import com.example.infra.test.support.RollbackTestSupport
import com.fasterxml.jackson.databind.JsonNode
import kotlinx.coroutines.reactive.awaitFirst
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
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
            .expectBody<JsonNode>()
            .consumeWith { println(it) }
    }

    @Test
    fun head() = withRollback {
        webClient.head().uri("/api/v1/locations/1")
            .exchange()
            .expectStatus().isOk
    }

    @Test
    fun get() = withRollback {
        val uri = "/api/v1/locations/1"
        webClient.get().uri(uri)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("id").isEqualTo(1)
            .jsonPath("uri").isEqualTo(uri)
            .jsonPath("name").exists()
            .jsonPath("address").exists()
            .jsonPath("createdAt").exists()
            .jsonPath("updatedAt").exists()
            .consumeWith { println(String(it.responseBody!!)) }
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
            .expectBody<JsonNode>()
            .consumeWith { println(it) }
            .returnResult()
            .responseBody!!

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
            .expectBody<JsonNode>()
            .consumeWith { println(it) }

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
            .expectBody<JsonNode>()
            .consumeWith { println(it) }

        webClient.get().uri(uri)
            .exchange()
            .expectBody()
            .jsonPath("name").isEqualTo("test")
    }
}