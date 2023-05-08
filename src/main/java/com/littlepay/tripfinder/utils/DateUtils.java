package com.littlepay.tripfinder.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;

@Component
public class DateUtils {
    private static final String DEFAULT_DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";

    public static LocalDateTime parseDate(String dateTime) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern(DEFAULT_DATE_FORMAT)
                .toFormatter();

        return LocalDateTime.parse(dateTime.trim(), formatter);
    }

    public static String toString(LocalDateTime dateTime) {
        if (dateTime == null) return "";

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern(DEFAULT_DATE_FORMAT)
                .toFormatter();

        return dateTime.format(formatter);
    }

    public static long getDurationInSec(LocalDateTime fromDate, LocalDateTime toDate) {
        if (fromDate == null || toDate == null) return 0L;
        return ChronoUnit.SECONDS.between(fromDate, toDate);
    }
}
