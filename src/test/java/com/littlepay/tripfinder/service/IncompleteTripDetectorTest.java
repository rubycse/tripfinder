package com.littlepay.tripfinder.service;

import com.littlepay.tripfinder.domain.Tap;
import com.littlepay.tripfinder.domain.TapType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static com.littlepay.tripfinder.service.TestData.getIncompleteTrip;
import static com.littlepay.tripfinder.service.TestData.getTap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IncompleteTripDetectorTest {

    @InjectMocks
    private IncompleteTripDetector tripDetector;

    @Mock
    private FairManager fairManager;

    @Test
    public void test_nullTaps() {
        Tap tap1 = getTap(1, TapType.ON, "Stop1");
        Tap tap2 = getTap(2, TapType.OFF, "Stop2");

        assertNull(tripDetector.detectTrip(null, null));
        assertNull(tripDetector.detectTrip(tap2, null));
        assertNull(tripDetector.detectTrip(null, tap1));
        assertNull(tripDetector.detectTrip(null, tap2));
    }

    @Test
    public void test_singleTapOn() {
        Tap tap1 = getTap(1, TapType.ON, "Stop1");
        when(fairManager.getMaxFair("Stop1"))
                .thenReturn(BigDecimal.valueOf(5.25));

        assertEquals(getIncompleteTrip(tap1, 5.25),
                tripDetector.detectTrip(tap1, null));
    }

    @Test
    public void test_singleTapOff() {
        Tap tap1 = getTap(1, TapType.OFF, "Stop1");

        assertNull(tripDetector.detectTrip(tap1, null));
    }

    @Test
    public void test_2TapOnsAtDifferentStop() {
        Tap tap1 = getTap(1, TapType.ON, "Stop1");
        Tap tap2 = getTap(2, TapType.ON, "Stop2");
        when(fairManager.getMaxFair("Stop1")).thenReturn(BigDecimal.valueOf(5.25));

        assertEquals(getIncompleteTrip(tap1, 5.25), tripDetector.detectTrip(tap1, tap2));
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

        assertNull(tripDetector.detectTrip(tap1, tap2));
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