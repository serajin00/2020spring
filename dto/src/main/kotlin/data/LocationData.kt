package com.example.dto.data

import com.example.domain.model.Location
import com.example.dto.base.DTO

data class LocationData(
    val name: String?,
    val address: String?
) : DTO {
    fun toDomain(id: Long?): Location {
        return Location(
            id = id,
            name = name!!,
            address = address!!
        )
    }

    companion object {
        fun fromDomain(location: Location): LocationData {
            return LocationData(
                name = location.name,
                address = location.address
            )
        }
    }
}