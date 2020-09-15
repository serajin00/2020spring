package com.example.dto.data.common

import com.example.domain.model.common.DateTime
import com.example.dto.base.DTO
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import java.time.*

data class DateTimeData(
    val value: OffsetDateTime
) : DTO {
    @JsonValue
    override fun toString(): String {
        // ISO8601
        return value.toString()
    }

    fun toDomain(): DateTime {
        return value.toZonedDateTime()
    }

    companion object {
        fun fromDomain(datetime: DateTime): DateTimeData {
            return DateTimeData(value = datetime.toOffsetDateTime())
        }

        @JvmStatic
        @JsonCreator
        fun parse(text: String): DateTimeData {
            fun LocalDateTime.toOffsetDateTime() = atZone(ZoneId.systemDefault()).toOffsetDateTime()

            val value = runCatching { OffsetDateTime.parse(text) }
                .recoverCatching { LocalDateTime.parse(text).toOffsetDateTime() }
                .recoverCatching { LocalDate.parse(text).atStartOfDay().toOffsetDateTime() }
                .getOrThrow()

            return DateTimeData(value)
        }
    }
}