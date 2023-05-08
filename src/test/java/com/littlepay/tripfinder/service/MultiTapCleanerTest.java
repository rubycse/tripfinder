package com.littlepay.tripfinder.service;

import com.littlepay.tripfinder.domain.Tap;
import com.littlepay.tripfinder.domain.TapType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.littlepay.tripfinder.service.TestData.getTap;

class MultiTapCleanerTest {

    private MultiTapCleaner multiTapCleaner;

    @BeforeEach
    void setUp() {
        multiTapCleaner = new MultiTapCleaner();
    }

    @Test
    public void test_noTap() {
        Assertions.assertEquals(null, multiTapCleaner.removeMultiTaps(null));
    }

    @Test
    public void test_singleTap() {
        Tap tap = getTap(0, TapType.ON, "Stop1");

        List<Tap> cleanedTaps = multiTapCleaner.removeMultiTaps(List.of(tap));

        Assertions.assertEquals(1, cleanedTaps.size());
        Assertions.assertEquals(tap, cleanedTaps.get(0));
    }

    @Test
    public void test_multiTaps() {
        Tap tap1 = getTap(0, TapType.ON, "Stop1");
        Tap tap2 = getTap(1, TapType.ON, "Stop1");
        Tap tap3 = getTap(2, TapType.ON, "Stop1");

        List<Tap> cleanedTaps = multiTapCleaner.removeMultiTaps(List.of(tap1, tap2, tap3));

        Assertions.assertEquals(1, cleanedTaps.size());
        Assertions.assertEquals(tap1, cleanedTaps.get(0));
    }

    @Test
    public void test_multiTapsOnsAnThenTapOff() {
        Tap tap1 = getTap(0, TapType.ON, "Stop1");
        Tap tap2 = getTap(1, TapType.ON, "Stop1");
        Tap tap3 = getTap(2, TapType.ON, "Stop1");
        Tap tap4 = getTap(2, TapType.OFF, "Stop1");

        List<Tap> cleanedTaps = multiTapCleaner.removeMultiTaps(List.of(tap1, tap2, tap3, tap4));

        Assertions.assertEquals(2, cleanedTaps.size());
        Assertions.assertEquals(tap1, cleanedTaps.get(0));
        Assertions.assertEquals(tap4, cleanedTaps.get(1));
    }

    @Test
    public void test_multiTapsOnsAnThenTapOffAtOtherStop() {
        Tap tap1 = getTap(0, TapType.ON, "Stop1");
        Tap tap2 = getTap(1, TapType.ON, "Stop1");
        Tap tap3 = getTap(2, TapType.ON, "Stop1");
        Tap tap4 = getTap(2, TapType.OFF, "Stop2");

        List<Tap> cleanedTaps = multiTapCleaner.removeMultiTaps(List.of(tap1, tap2, tap3, tap4));

        Assertions.assertEquals(2, cleanedTaps.size());
        Assertions.assertEquals(tap1, cleanedTaps.get(0));
        Assertions.assertEquals(tap4, cleanedTaps.get(1));
    }

    @Test
    public void test_multiTapsOnsAnThenMultipleTapOffs() {
        Tap tap1 = getTap(0, TapType.ON, "Stop1");
        Tap tap2 = getTap(1, TapType.ON, "Stop1");
        Tap tap3 = getTap(2, TapType.ON, "Stop1");
        Tap tap4 = getTap(3, TapType.OFF, "Stop2");
        Tap tap5 = getTap(4, TapType.OFF, "Stop2");
        Tap tap6 = getTap(5, TapType.OFF, "Stop2");

        List<Tap> cleanedTaps = multiTapCleaner.removeMultiTaps(List.of(tap1, tap2, tap3, tap4, tap5, tap6));

        Assertions.assertEquals(2, cleanedTaps.size());
        Assertions.assertEquals(tap1, cleanedTaps.get(0));
        Assertions.assertEquals(tap4, cleanedTaps.get(1));
    }

    @Test
    public void test_tapOnThenTapOffThenTapOn() {
        Tap tap1 = getTap(0, TapType.ON, "Stop1");
        Tap tap2 = getTap(1, TapType.OFF, "Stop1");
        Tap tap3 = getTap(2, TapType.ON, "Stop1");

        List<Tap> cleanedTaps = multiTapCleaner.removeMultiTaps(List.of(tap1, tap2, tap3));

        Assertions.assertEquals(3, cleanedTaps.size());
        Assertions.assertEquals(tap1, cleanedTaps.get(0));
        Assertions.assertEquals(tap2, cleanedTaps.get(1));
        Assertions.assertEquals(tap3, cleanedTaps.get(2));
    }

    @Test
    public void test_multiTapOnInDifferentStops() {
        Tap tap1 = getTap(0, TapType.ON, "Stop1");
        Tap tap2 = getTap(1, TapType.ON, "Stop1");
        Tap tap3 = getTap(2, TapType.ON, "Stop2");
        Tap tap4 = getTap(3, TapType.ON, "Stop2");
        Tap tap5 = getTap(4, TapType.ON, "Stop3");
        Tap tap6 = getTap(5, TapType.ON, "Stop3");

        List<Tap> cleanedTaps = multiTapCleaner.removeMultiTaps(List.of(tap1, tap2, tap3, tap4, tap5, tap6));

        Assertions.assertEquals(3, cleanedTaps.size());
        Assertions.assertEquals(tap1, cleanedTaps.get(0));
        Assertions.assertEquals(tap3, cleanedTaps.get(1));
        Assertions.assertEquals(tap5, cleanedTaps.get(2));
    }
}