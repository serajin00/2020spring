package com.example.domain.repository

import com.example.domain.model.Reservation

interface ReservationRepository {
    suspend fun save(entity: Reservation): Reservation
    suspend fun findByLocation(locationId: Long): List<Reservation>
    suspend fun findByUsername(username: String): List<Reservation>
}
