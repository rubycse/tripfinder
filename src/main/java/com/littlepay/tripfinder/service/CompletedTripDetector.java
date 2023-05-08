package com.littlepay.tripfinder.service;

import com.littlepay.tripfinder.domain.Tap;
import com.littlepay.tripfinder.domain.TapType;
import com.littlepay.tripfinder.domain.Trip;
import com.littlepay.tripfinder.domain.TripStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Detect completed trips for 2 sequential taps. A trip is completed when a passenger taps on
 * at one stop and taps off at another stop.
 */
@Component
@RequiredArgsConstructor
public class CompletedTripDetector implements TripDetector {

    private final FairManager fairManager;

    @Override
    public Trip detectTrip(Tap firstTap, Tap secondTap) {
        if (firstTap == null || secondTap == null) return null;

        if (firstTap.getTapType() == TapType.ON
                && secondTap.getTapType() == TapType.OFF
                && !Objects.equals(firstTap.getStopId(), secondTap.getStopId())
                && firstTap.getDateTime().isBefore(secondTap.getDateTime())) {

            return Trip.builder()
                    .startTime(firstTap.getDateTime())
                    .finishTime(secondTap.getDateTime())
                    .fromStopId(firstTap.getStopId())
                    .toStopId(secondTap.getStopId())
                    .chargeAmount(fairManager.getFair(firstTap.getStopId(), secondTap.getStopId()))
                    .companyId(firstTap.getCompanyId())
                    .busId(firstTap.getBusId())
                    .pan(firstTap.getPan())
                    .status(TripStatus.COMPLETED)
                    .build();
        }

        return null;
    }
}
