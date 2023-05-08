package com.littlepay.tripfinder.service;

import com.littlepay.tripfinder.domain.Tap;
import com.littlepay.tripfinder.domain.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Detects trips by composing other trip detectors.
 */
@Component
@RequiredArgsConstructor
public class CompositeTripDetector {

    private final List<TripDetector> tripDetectors;

    public Trip detectTrip(Tap firstTap, Tap secondTap) {
        for (TripDetector tripDetector : tripDetectors) {
            Trip trip = tripDetector.detectTrip(firstTap, secondTap);
            if (trip != null) {
                return trip;
            }
        }

        return null;
    }
}
