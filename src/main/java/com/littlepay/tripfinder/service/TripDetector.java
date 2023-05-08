package com.littlepay.tripfinder.service;

import com.littlepay.tripfinder.domain.Tap;
import com.littlepay.tripfinder.domain.Trip;

public interface TripDetector {
    Trip detectTrip(Tap firstTap, Tap secondTap);
}
