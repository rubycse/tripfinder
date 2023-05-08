package com.littlepay.tripfinder.service;

import com.littlepay.tripfinder.domain.Tap;
import com.littlepay.tripfinder.domain.TapType;
import com.littlepay.tripfinder.domain.Trip;
import com.littlepay.tripfinder.domain.TripStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Detect incomplete trips for 2 sequential taps. A trip is incomplete when a passenger taps on
 * at one stop but forgets to tap off at another stop.
 */
@Component
@RequiredArgsConstructor
public class IncompleteTripDetector implements TripDetector {

    private final FairManager fairManager;

    @Override
    public Trip detectTrip(Tap firstTap, Tap secondTap) {
        if (firstTap == null) {
            return null;
        }

        if (secondTap == null && firstTap.getTapType() == TapType.ON) {
            return createIncompleteTrip(firstTap);
        }

        if (firstTap.getTapType() == TapType.ON
                && secondTap.getTapType() == TapType.ON
                && firstTap.getDateTime().isBefore(secondTap.getDateTime())) {
            return createIncompleteTrip(firstTap);
        }

        return null;
    }

    private Trip createIncompleteTrip(Tap firstTap) {
        return Trip.builder()
                .startTime(firstTap.getDateTime())
                .fromStopId(firstTap.getStopId())
                .chargeAmount(fairManager.getMaxFair(firstTap.getStopId()))
                .companyId(firstTap.getCompanyId())
                .busId(firstTap.getBusId())
                .pan(firstTap.getPan())
                .status(TripStatus.INCOMPLETE)
                .build();
    }
}
