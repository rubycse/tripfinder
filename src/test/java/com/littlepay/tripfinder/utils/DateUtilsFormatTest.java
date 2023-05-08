package com.littlepay.tripfinder.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class DateUtilsFormatTest {

    private LocalDateTime dateTime;

    @BeforeEach
    void setUp() {
        dateTime = LocalDateTime.of(2022, 1, 1, 6, 40, 30);
    }

    @Test
    public void test_validDate() {
        Assertions.assertEquals("01-01-2022 06:40:30", DateUtils.toString(dateTime));
    }

    @Test
    public void test_nullDate() {
        Assertions.assertEquals("", DateUtils.toString(null));
    }
}