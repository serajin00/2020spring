package com.example.test.models

import com.example.domain.model.Location
import com.example.dto.data.LocationData

object TestLocations {
    val location by lazy {
        Location(
            id = null,
            name = "test",
            address = "test address"
        )
    }
    val locationData by lazy {
        LocationData.fromDomain(location)
    }
}
