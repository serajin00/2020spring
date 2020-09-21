package com.example.infra.db.repository

import com.example.domain.model.Reservation
import com.example.domain.repository.ReservationRepository
import com.example.infra.db.SqlDateTime
import com.example.infra.db.toDomain
import com.example.infra.db.toSql
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import org.springframework.data.annotation.Id
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Service

@Service
internal class ReservationRepositoryImpl(
    val dao: ReservationDAO
) : ReservationRepository {
    override suspend fun save(entity: Reservation): Reservation {
        return dao.save(entity.toTable()).toDomain()
    }

    override suspend fun findByLocation(locationId: Long): List<Reservation> {
        return dao.findByLocationId(locationId).toList().map { it.toDomain() }
    }

    override suspend fun findByUsername(username: String): List<Reservation> {
        return dao.findByUsername(username).toList().map { it.toDomain() }
    }
}

@Table("reservations")
internal data class ReservationTable(
    @Id
    val id: Long?,
    val locationId: Long,
    val username: String,
    val startsAt: SqlDateTime,
    val endsAt: SqlDateTime,
    val memo: String?
)

internal fun Reservation.toTable(): ReservationTable {
    return ReservationTable(
        id = id,
        locationId = locationId,
        username = username,
        startsAt = schedule.start.toSql(),
        endsAt = schedule.endInclusive.toSql(),
        memo = memo
    )
}

internal fun ReservationTable.toDomain(): Reservation {
    return Reservation(
        id = id,
        locationId = locationId,
        username = username,
        schedule = startsAt.toDomain() .. endsAt.toDomain(),
        memo = memo
    )
}

internal interface ReservationDAO : CoroutineCrudRepository<ReservationTable, Long> {
    @Query("SELECT * FROM reservations WHERE location_id = :locationId")
    fun findByLocationId(locationId: Long): Flow<ReservationTable>

    @Query("SELECT * FROM reservations WHERE username = :username")
    fun findByUsername(username: String): Flow<ReservationTable>
}