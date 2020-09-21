package com.example.test.models

import com.example.domain.model.Reservation
import com.example.dto.data.ReservationData
import com.example.test.models.util.interval

object TestReservations {
    val reservation by lazy {
        Reservation(
            id = null,
            locationId = 1,
            username = "user",
            schedule = interval("2020-01-01".."2020-12-31"),
            memo = "memo"
        )
    }
    val reservationData by lazy {
        ReservationData.fromDomain(reservation)
    }
}