package com.toren.producthub.utils

import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.Locale


class TimeFormatter {
    companion object {
        fun formatDateTime(input: String?): String {
            if (input.isNullOrBlank()) return ""
            return DateTimeFormatter.ofPattern(
                "d MMM yyyy",
                Locale.getDefault()
            )
                .format(ZonedDateTime.parse(input, DateTimeFormatter.ISO_ZONED_DATE_TIME))
        }
    }
}