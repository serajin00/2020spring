package com.example.api.model

import com.example.dto.data.LocationData
import com.example.dto.data.common.TimestampsData
import com.fasterxml.jackson.annotation.JsonUnwrapped
import java.net.URI

data class LocationResponse(
    val id: Long,
    @JsonUnwrapped
    val data: LocationData,
    @JsonUnwrapped
    val ts: TimestampsData?
) {
    val uri = URI("/api/v1/locations/$id")
}
