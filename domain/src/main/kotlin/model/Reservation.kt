package com.example.domain.model

import com.example.domain.model.common.Interval

data class Reservation(
    val id: Long?,
    val locationId: Long,
    val username: String,
    val schedule: Interval
)
