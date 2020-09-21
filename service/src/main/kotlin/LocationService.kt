package com.example.service

import com.example.domain.model.Location
import com.example.domain.repository.LocationRepository
import com.example.dto.data.LocationData
import com.example.infra.tx.Tx
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service

interface LocationService {
    suspend fun create(data: LocationData): Location
    suspend fun update(id: Long, data: LocationData): Location?
    suspend fun patch(id: Long, patch: JsonNode): Location?
}

@Service
class LocationServiceImpl(
    val repository: LocationRepository,
    val tx: Tx,
    val objectMapper: ObjectMapper
) : LocationService {
    override suspend fun create(data: LocationData): Location = tx {
        repository.save(data.toDomain(id = null))
    }

    override suspend fun update(id: Long, data: LocationData): Location? = tx {
        repository.findById(id)?.let {
            repository.save(data.toDomain(id = id))
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun patch(id: Long, patch: JsonNode): Location? = tx {
        repository.findById(id)?.let { old ->
            var data = LocationData.fromDomain(old)
            data = objectMapper.readerForUpdating(data).readValue(patch)
            repository.save(data.toDomain(id = id))
        }
    }
}