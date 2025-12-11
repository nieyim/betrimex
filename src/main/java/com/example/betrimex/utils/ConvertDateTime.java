package com.example.betrimex.utils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class ConvertDateTime {

    public static String toIso8601Z(LocalDateTime localDateTime) {

        if (localDateTime == null) {
            return null;
        }

        return localDateTime.atOffset(ZoneOffset.UTC)
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public static final DateTimeFormatter FLEXIBLE_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
            .optionalStart()
            .appendFraction(ChronoField.NANO_OF_SECOND, 1, 9, true)
            .optionalEnd()
            .appendOffset("+HH:mm", "Z")
            .toFormatter();

    public static OffsetDateTime convertToOffsetDateTime(String datetimeStr) {
        try {
            return OffsetDateTime.parse(datetimeStr, FLEXIBLE_FORMATTER);
        } catch (Exception e) {
            System.err.println("⚠️ Không parse được thời gian: " + datetimeStr);
            return null;
        }
    }
}
