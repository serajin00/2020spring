package com.example.infra.db

import com.example.domain.model.common.DateTime
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

internal typealias SqlDateTime = LocalDateTime

/**
 * domain time -> UTC local (mysql)
 */
internal fun DateTime.toSql(): SqlDateTime {
    return this.toOffsetDateTime().withOffsetSameInstant(ZoneOffset.UTC).toLocalDateTime()
}

/**
 * UTC local (mysql) -> domain time
 */
internal fun SqlDateTime.toDomain(): DateTime {
    return this.atOffset(ZoneOffset.UTC).atZoneSameInstant(ZoneId.systemDefault())
}
