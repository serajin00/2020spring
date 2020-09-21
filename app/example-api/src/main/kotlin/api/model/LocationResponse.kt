package com.example.api.model

import com.example.dto.data.LocationData
import com.fasterxml.jackson.annotation.JsonUnwrapped
import java.net.URI

data class LocationResponse(
    val id: Long,
    @JsonUnwrapped
    val data: LocationData
) {
    val uri = URI("/api/v1/locations/$id")
}
