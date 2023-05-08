package com.littlepay.tripfinder.service;

import com.littlepay.tripfinder.domain.Tap;
import com.littlepay.tripfinder.domain.TapType;
import com.littlepay.tripfinder.domain.Trip;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.littlepay.tripfinder.service.TestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class TripFinderIntegrationTest {

    @Autowired
    private TripFinder tripFinder;

    @Test
    public void test_incompleteTrip() {
        Tap tap = getTap(0, TapType.ON, "Stop1");
        Trip trip = getIncompleteTrip(tap, 7.3);

        List<Trip> trips = tripFinder.findTrips(List.of(tap));

        Assertions.assertEquals(1, trips.size());
        Assertions.assertEquals(trip, trips.get(0));
    }

    @Test
    public void test_completedTrip() {
        Tap tap1 = getTap(0, TapType.ON, "Stop1");
        Tap tap2 = getTap(1, TapType.OFF, "Stop2");
        Trip trip = getCompletedTrip(tap1, tap2);

        List<Trip> trips = tripFinder.findTrips(List.of(tap1, tap2));

        Assertions.assertEquals(1, trips.size());
        Assertions.assertEquals(trip, trips.get(0));
    }

    @Test
    public void test_canceledTrip() {
        Tap tap1 = getTap(0, TapType.ON, "Stop1");
        Tap tap2 = getTap(1, TapType.OFF, "Stop1");
        Trip trip = getCancledTrip(tap1, tap2);

        List<Trip> trips = tripFinder.findTrips(List.of(tap1, tap2));

        Assertions.assertEquals(1, trips.size());
        Assertions.assertEquals(trip, trips.get(0));
    }

    @Test
    public void test_multipleTapOn() {
        Tap tap1 = getTap(0, TapType.ON, "Stop1");
        Tap tap2 = getTap(1, TapType.ON, "Stop1");
        Tap tap3 = getTap(2, TapType.ON, "Stop1");

        Trip trip = getIncompleteTrip(tap1, 7.3);

        List<Trip> trips = tripFinder.findTrips(List.of(tap1, tap2, tap3));

        Assertions.assertEquals(1, trips.size());
        Assertions.assertEquals(trip, trips.get(0));
    }

    @Test
    public void test_multipleTapOnAndThenTapOff() {
        Tap tap1 = getTap(0, TapType.ON, "Stop1");
        Tap tap2 = getTap(1, TapType.ON, "Stop1");
        Tap tap3 = getTap(2, TapType.ON, "Stop1");

        Tap tap4 = getTap(4, TapType.OFF, "Stop2");

        Trip trip = getCompletedTrip(tap1, tap4);

        List<Trip> trips = tripFinder.findTrips(List.of(tap1, tap2, tap3, tap4));

        Assertions.assertEquals(1, trips.size());
        Assertions.assertEquals(trip, trips.get(0));
    }

    @Test
    public void test_multipleTapOnAndThenMultipleTapOff() {
        Tap tap1 = getTap(0, TapType.ON, "Stop1");
        Tap tap2 = getTap(1, TapType.ON, "Stop1");
        Tap tap3 = getTap(2, TapType.ON, "Stop1");

        Tap tap4 = getTap(4, TapType.OFF, "Stop2");
        Tap tap5 = getTap(5, TapType.OFF, "Stop2");
        Tap tap6 = getTap(6, TapType.OFF, "Stop2");

        Trip trip = getCompletedTrip(tap1, tap4);

        List<Trip> trips = tripFinder.findTrips(List.of(tap1, tap2, tap3, tap4, tap5, tap6));

        Assertions.assertEquals(1, trips.size());
        Assertions.assertEquals(trip, trips.get(0));
    }

    @Test
    public void test_tapOnInDifferentStops() {
        Tap tap1 = getTap(0, TapType.ON, "Stop1");
        Tap tap2 = getTap(1, TapType.ON, "Stop2");

        Trip trip1 = getIncompleteTrip(tap1, 7.3);
        Trip trip2 = getIncompleteTrip(tap2, 5.5);

        List<Trip> trips = tripFinder.findTrips(List.of(tap1, tap2));

        List<Trip> expected = List.of(trip1, trip2);
        assertEqualsIgnoringOrder(expected, trips);
    }

    @Test
    public void test_completeAndThenIncompleteTripWithMultiTap() {
        Tap tap1 = getTap(0, TapType.ON, "Stop1");
        Tap tap2 = getTap(1, TapType.ON, "Stop1");

        Tap tap3 = getTap(2, TapType.OFF, "Stop2");
        Tap tap4 = getTap(3, TapType.OFF, "Stop2");

        Tap tap5 = getTap(4, TapType.ON, "Stop2");
        Tap tap6 = getTap(5, TapType.ON, "Stop2");

        Trip trip1 = getCompletedTrip(tap1, tap3);
        Trip trip2 = getIncompleteTrip(tap5, 5.5);

        List<Trip> trips = tripFinder.findTrips(List.of(tap1, tap2, tap3, tap4, tap5, tap6));

        List<Trip> expected = List.of(trip1, trip2);
        assertEqualsIgnoringOrder(expected, trips);
    }

    @Test
    public void test_completeThenIncompleteAndThenCompleteTripWithMultiTap() {
        Tap tap1 = getTap(0, TapType.ON, "Stop1");
        Tap tap2 = getTap(1, TapType.ON, "Stop1");

        Tap tap3 = getTap(2, TapType.OFF, "Stop2");
        Tap tap4 = getTap(3, TapType.OFF, "Stop2");

        Tap tap5 = getTap(4, TapType.ON, "Stop2");

        Tap tap6 = getTap(5, TapType.ON, "Stop1");

        Tap tap7 = getTap(6, TapType.OFF, "Stop2");
        Tap tap8 = getTap(7, TapType.OFF, "Stop2");

        Trip trip1 = getCompletedTrip(tap1, tap3);
        Trip trip2 = getIncompleteTrip(tap5, 5.5);
        Trip trip3 = getCompletedTrip(tap6, tap7);

        List<Trip> trips = tripFinder.findTrips(List.of(tap1, tap2, tap3, tap4, tap5, tap6, tap7, tap8));

        List<Trip> expected = List.of(trip1, trip2, trip3);
        assertEqualsIgnoringOrder(expected, trips);
    }

    @Test
    public void test_completeThenIncompleteAndThenCanceledTripWithMultiTap() {
        Tap tap1 = getTap(0, TapType.ON, "Stop1");
        Tap tap2 = getTap(1, TapType.ON, "Stop1");

        Tap tap3 = getTap(2, TapType.OFF, "Stop2");
        Tap tap4 = getTap(3, TapType.OFF, "Stop2");

        Tap tap5 = getTap(4, TapType.ON, "Stop2");

        Tap tap6 = getTap(5, TapType.ON, "Stop1");

        Tap tap7 = getTap(6, TapType.OFF, "Stop1");
        Tap tap8 = getTap(7, TapType.OFF, "Stop1");

        Trip trip1 = getCompletedTrip(tap1, tap3);
        Trip trip2 = getIncompleteTrip(tap5, 5.5);
        Trip trip3 = getCancledTrip(tap6, tap7);

        List<Trip> trips = tripFinder.findTrips(List.of(tap1, tap2, tap3, tap4, tap5, tap6, tap7, tap8));

        List<Trip> expected = List.of(trip1, trip2, trip3);
        assertEqualsIgnoringOrder(expected, trips);
    }

    @Test
    public void test_completeThenIncompleteAndThenCanceledTripOutOfSequence() {
        Tap tap1 = getTap(0, TapType.ON, "Stop1");
        Tap tap2 = getTap(1, TapType.ON, "Stop1");

        Tap tap3 = getTap(2, TapType.OFF, "Stop2");
        Tap tap4 = getTap(3, TapType.OFF, "Stop2");

        Tap tap5 = getTap(4, TapType.ON, "Stop2");

        Tap tap6 = getTap(5, TapType.ON, "Stop1");

        Tap tap7 = getTap(6, TapType.OFF, "Stop1");
        Tap tap8 = getTap(7, TapType.OFF, "Stop1");

        Trip trip1 = getCompletedTrip(tap1, tap3);
        Trip trip2 = getIncompleteTrip(tap5, 5.5);
        Trip trip3 = getCancledTrip(tap6, tap7);

        List<Trip> trips = tripFinder.findTrips(List.of(tap2, tap4, tap6, tap8, tap1, tap3, tap5, tap7));

        List<Trip> expected = List.of(trip1, trip2, trip3);
        assertEqualsIgnoringOrder(expected, trips);
    }

    @Test
    public void test_tripsWithMultiPassenger() {
        Tap tap1 = getTap(1, TapType.ON, "Stop1");
        Tap tap3 = getTap(3, TapType.OFF, "Stop2");
        Tap tap5 = getTap(5, TapType.ON, "Stop2");
        Tap tap7 = getTap(7, TapType.OFF, "Stop1");

        Tap tap2 = getTapWithAnotherBus(2, TapType.ON, "Stop1");
        Tap tap4 = getTapWithAnotherBus(4, TapType.OFF, "Stop2");
        Tap tap6 = getTapWithAnotherBus(6, TapType.ON, "Stop1");

        Trip trip1 = getCompletedTrip(tap1, tap3);
        Trip trip2 = getCompletedTrip(tap5, tap7);
        Trip trip3 = getCompletedTrip(tap2, tap4);
        Trip trip4 = getIncompleteTrip(tap6, 7.30);

        List<Trip> trips = tripFinder.findTrips(List.of(tap1, tap2, tap3, tap4, tap5, tap6, tap7));

        List<Trip> expected = List.of(trip1, trip2, trip3, trip4);
        assertEqualsIgnoringOrder(expected, trips);
    }

    private void assertEqualsIgnoringOrder(List<Trip> first, List<Trip> second) {
        if (first == null || second == null) {
            assertEquals(first, second);
            return;
        }

        assertTrue(first.size() == second.size()
                && first.containsAll(second) && second.containsAll(first));
    }
}
