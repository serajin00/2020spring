package com.example.infra.repository

import com.example.domain.repository.LocationRepository
import com.example.infra.InfraTest
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageRequest

@InfraTest
class LocationRepositoryTest(
    val repository: LocationRepository,
    val objectMapper: ObjectMapper
) {
    @Test
    fun findAll() = runBlocking<Unit> {
        val page = repository.findAll(pageable = PageRequest.of(0, 10))

        page.content.forEach {
            println(it)
        }

        println(objectMapper.writeValueAsString(page))
    }

}