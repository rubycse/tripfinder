package com.littlepay.tripfinder.service;

import com.littlepay.tripfinder.domain.Tap;
import com.littlepay.tripfinder.domain.TapType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.littlepay.tripfinder.service.TestData.getCancledTrip;
import static com.littlepay.tripfinder.service.TestData.getTap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CanceledTripDetectorTest {

    private CanceledTripDetector tripDetector;

    @BeforeEach
    void setUp() {
        tripDetector = new CanceledTripDetector();
    }

    @Test
    public void test_nullTaps() {
        Tap tap1 = getTap(1, TapType.ON, "Stop1");
        Tap tap2 = getTap(2, TapType.OFF, "Stop2");

        assertNull(tripDetector.detectTrip(null, null));
        assertNull(tripDetector.detectTrip(tap1, null));
        assertNull(tripDetector.detectTrip(tap2, null));
        assertNull(tripDetector.detectTrip(null, tap1));
        assertNull(tripDetector.detectTrip(null, tap2));

    }

    @Test
    public void test_2TapOnsAtDifferentStop() {
        Tap tap1 = getTap(1, TapType.ON, "Stop1");
        Tap tap2 = getTap(2, TapType.ON, "Stop2");

        assertNull(tripDetector.detectTrip(tap1, tap2));
    }

    @Test
    public void test_tapOnTapOffAtDifferentStop() {
        Tap tap1 = getTap(1, TapType.ON, "Stop1");
        Tap tap2 = getTap(2, TapType.OFF, "Stop2");

        assertNull(tripDetector.detectTrip(tap1, tap2));
    }

    @Test
    public void test_tapOnTapOffAtSameStop() {
        Tap tap1 = getTap(1, TapType.ON, "Stop1");
        Tap tap2 = getTap(2, TapType.OFF, "Stop1");

        assertEquals(getCancledTrip(tap1, tap2),
                tripDetector.detectTrip(tap1, tap2));
    }

    @Test
    public void test_tapOffTapOnAtDifferentStop() {
        Tap tap1 = getTap(1, TapType.OFF, "Stop1");
        Tap tap2 = getTap(2, TapType.ON, "Stop2");

        assertNull(tripDetector.detectTrip(tap1, tap2));
    }

    @Test
    public void test_tapOffTapOnAtSameStop() {
        Tap tap1 = getTap(1, TapType.OFF, "Stop1");
        Tap tap2 = getTap(2, TapType.ON, "Stop1");

        assertNull(tripDetector.detectTrip(tap1, tap2));
    }
}