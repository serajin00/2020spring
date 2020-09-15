package com.example.domain.repository

import com.example.domain.model.Location
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface LocationRepository {
    suspend fun save(entity: Location): Location
    suspend fun findById(id: Long): Location?
    suspend fun findAll(pageable: Pageable): Page<Location>
}
