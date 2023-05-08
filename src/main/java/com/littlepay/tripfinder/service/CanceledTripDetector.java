package com.littlepay.tripfinder.service;

import com.littlepay.tripfinder.domain.Tap;
import com.littlepay.tripfinder.domain.TapType;
import com.littlepay.tripfinder.domain.Trip;
import com.littlepay.tripfinder.domain.TripStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Detect incomplete trips for 2 sequential taps. A trip is incomplete when a passenger
 * taps on and then taps off again at the same bus stop.
 */
@Component
public class CanceledTripDetector implements TripDetector {

    @Override
    public Trip detectTrip(Tap firstTap, Tap secondTap) {
        if (firstTap == null || secondTap == null) {
            return null;
        }

        if (firstTap.getTapType() == TapType.ON
                && secondTap.getTapType() == TapType.OFF
                && firstTap.getStopId().equals(secondTap.getStopId())
                && firstTap.getDateTime().isBefore(secondTap.getDateTime())) {

            return Trip.builder()
                    .startTime(firstTap.getDateTime())
                    .finishTime(secondTap.getDateTime())
                    .fromStopId(firstTap.getStopId())
                    .toStopId(secondTap.getStopId())
                    .chargeAmount(BigDecimal.ZERO)
                    .companyId(firstTap.getCompanyId())
                    .busId(firstTap.getBusId())
                    .pan(firstTap.getPan())
                    .status(TripStatus.CANCELLED)
                    .build();
        }

        return null;
    }
}
