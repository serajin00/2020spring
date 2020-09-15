package com.example.dto.test

import com.example.dto.data.LocationData
import com.example.test.models.TestLocations
import com.example.test.models.TestReservations
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

@SpringJUnitConfig
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
class JsonTest(
    val objectMapper: ObjectMapper
) {
    @Test
    fun `location json`() {
        val data = TestLocations.locationData

        val json = objectMapper.writeValueAsString(data)
        println(json)

        val data2 = objectMapper.readValue<LocationData>(json)
        println(data2)
    }

    @Test
    fun `reservation json`() {
        val data = TestReservations.reservationData

        val json = objectMapper.writeValueAsString(data)
        println(json)

        val data2 = objectMapper.readValue<LocationData>(json)
        println(data2)
    }
}