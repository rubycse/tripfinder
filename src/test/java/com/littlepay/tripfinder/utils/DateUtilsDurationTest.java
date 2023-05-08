package com.littlepay.tripfinder.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class DateUtilsDurationTest {

    private LocalDateTime fromDate;
    private LocalDateTime toDate;

    @BeforeEach
    void setUp() {
        fromDate = LocalDateTime.of(2022, 1, 1, 6, 40, 40);
        toDate = LocalDateTime.of(2022, 1, 1, 6, 40, 45);
    }

    @Test
    public void test_durationWithSameDate() {
        Assertions.assertEquals(0, DateUtils.getDurationInSec(fromDate, fromDate));
    }

    @Test
    public void test_durationWithEarlierDateFirst() {
        Assertions.assertEquals(5, DateUtils.getDurationInSec(fromDate, toDate));
    }

    @Test
    public void test_durationWithLaterDateFirst() {
        Assertions.assertEquals(-5, DateUtils.getDurationInSec(toDate, fromDate));
    }

    @Test
    public void test_durationWithNullDate() {
        Assertions.assertEquals(0, DateUtils.getDurationInSec(null, null));
        Assertions.assertEquals(0, DateUtils.getDurationInSec(fromDate, null));
        Assertions.assertEquals(0, DateUtils.getDurationInSec(null, toDate));
    }
}