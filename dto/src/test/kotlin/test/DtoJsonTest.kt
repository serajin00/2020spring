package com.example.dto.test

import com.example.dto.DtoTest
import com.example.dto.data.LocationData
import com.example.test.models.TestLocations
import com.example.test.models.TestReservations
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test

@DtoTest
class DtoJsonTest(
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