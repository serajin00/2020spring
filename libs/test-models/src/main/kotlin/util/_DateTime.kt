package com.example.test.models.util

import com.example.domain.model.common.DateTime
import com.example.domain.model.common.Interval
import com.example.dto.data.common.DateTimeData
import java.time.ZoneId

internal fun String.toDateTime(): DateTime {
    return DateTimeData.parse(this).value.atZoneSameInstant(ZoneId.systemDefault())
}

internal fun interval(range: ClosedRange<String>): Interval {
    return range.start.toDateTime() .. range.endInclusive.toDateTime()
}
