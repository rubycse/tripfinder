package com.littlepay.tripfinder.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

class DateUtilsParsingTest {

    @Test
    public void test_validDate() {
        LocalDateTime localDateTime = DateUtils.parseDate("22-01-2018 13:00:00");
        Assertions.assertEquals(22, localDateTime.getDayOfMonth());
        Assertions.assertEquals(1, localDateTime.getMonthValue());
        Assertions.assertEquals(2018, localDateTime.getYear());
    }

    @Test
    public void test_validDateWithLeadingSpace() {
        LocalDateTime localDateTime = DateUtils.parseDate(" 22-01-2018 13:00:00");
        Assertions.assertEquals(22, localDateTime.getDayOfMonth());
        Assertions.assertEquals(1, localDateTime.getMonthValue());
        Assertions.assertEquals(2018, localDateTime.getYear());
    }

    @Test
    public void test_validDateWithTailingSpace() {
        LocalDateTime localDateTime = DateUtils.parseDate("22-01-2018 13:00:00 ");
        Assertions.assertEquals(22, localDateTime.getDayOfMonth());
        Assertions.assertEquals(1, localDateTime.getMonthValue());
        Assertions.assertEquals(2018, localDateTime.getYear());
    }

    @Test
    public void test_invalidFormat() {
        Assertions.assertThrows(DateTimeParseException.class,
                () -> DateUtils.parseDate("22/01/2018 13:00:00"));
    }

    @Test
    public void test_invalidMonthInDate() {
        Assertions.assertThrows(DateTimeParseException.class,
                () -> DateUtils.parseDate("22-22-2018 13:00:00"));
    }
}