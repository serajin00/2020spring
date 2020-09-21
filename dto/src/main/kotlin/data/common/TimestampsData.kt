package com.example.dto.data.common

import com.example.dto.base.DTO

data class TimestampsData(
    val createdAt: DateTimeData?,
    val updatedAt: DateTimeData?
) : DTO