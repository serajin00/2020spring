package com.example.service

import com.example.domain.model.Reservation
import com.example.domain.repository.ReservationRepository
import com.example.dto.data.ReservationData
import com.example.infra.tx.Tx
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service

interface ReservationService {
    suspend fun create(locationId: Long, data: ReservationData): Reservation
    suspend fun update(id: Long, data: ReservationData): Reservation?
    suspend fun patch(id: Long, patch: JsonNode): Reservation?
}

@Service
class ReservationServiceImpl(
    val repository: ReservationRepository,
    val tx: Tx,
    val objectMapper: ObjectMapper
) : ReservationService {

    override suspend fun create(locationId: Long, data: ReservationData): Reservation = tx {
        repository.save(data.toDomain(id = null, locationId = locationId))
    }

    override suspend fun update(id: Long, data: ReservationData): Reservation? = tx {
        repository.findById(id)?.let { old ->
            repository.save(data.toDomain(id = id, locationId = old.locationId))
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun patch(id: Long, patch: JsonNode): Reservation? = tx {
        repository.findById(id)?.let { old ->
            var data = ReservationData.fromDomain(old)
            data = objectMapper.readerForUpdating(data).readValue(patch)
            repository.save(data.toDomain(id = id, locationId = old.locationId))
        }
    }

}