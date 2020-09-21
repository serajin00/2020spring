package com.example.dto.data

import com.example.domain.model.Reservation
import com.example.dto.base.DTO
import com.example.dto.data.common.DateTimeData

data class ReservationData(
    val username: String?,
    val startsAt: String?,
    val endsAt: String?,
    val memo: String?
) : DTO {
    fun toDomain(id: Long?, locationId: Long): Reservation {
        return Reservation(
            id = id,
            locationId = locationId,
            username = username!!,
            schedule = DateTimeData.parse(startsAt!!).toDomain() .. DateTimeData.parse(endsAt!!).toDomain(),
            memo = memo
        )
    }

    companion object {
        fun fromDomain(reservation: Reservation): ReservationData {
            return ReservationData(
                username = reservation.username,
                startsAt = DateTimeData.fromDomain(reservation.schedule.start).toString(),
                endsAt = DateTimeData.fromDomain(reservation.schedule.endInclusive).toString(),
                memo = reservation.memo
            )
        }
    }
}