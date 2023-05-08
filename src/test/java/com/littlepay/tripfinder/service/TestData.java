package com.littlepay.tripfinder.service;

import com.littlepay.tripfinder.domain.Tap;
import com.littlepay.tripfinder.domain.TapType;
import com.littlepay.tripfinder.domain.Trip;
import com.littlepay.tripfinder.domain.TripStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TestData {
    public static Tap getTap(int time, TapType tapType, String stop) {
        return Tap.builder()
                .id(1)
                .dateTime(getTestDateWithSec(time))
                .tapType(tapType)
                .stopId(stop)
                .companyId("Company1")
                .busId("Bus1")
                .pan("5500005555555559")
                .build();
    }

    public static Tap getTapWithAnotherBus(int time, TapType tapType, String stop) {
        return Tap.builder()
                .id(1)
                .dateTime(getTestDateWithSec(time))
                .tapType(tapType)
                .stopId(stop)
                .companyId("Company2")
                .busId("Bus2")
                .pan("4444333322221111")
                .build();
    }

    public static Trip getIncompleteTrip(Tap tap, double maxCost) {
        return Trip.builder()
                .startTime(tap.getDateTime())
                .fromStopId(tap.getStopId())
                .chargeAmount(BigDecimal.valueOf(maxCost))
                .companyId(tap.getCompanyId())
                .busId(tap.getBusId())
                .pan(tap.getPan())
                .status(TripStatus.INCOMPLETE)
                .build();
    }

    public static Trip getCancledTrip(Tap tap1, Tap tap2) {
        return Trip.builder()
                .startTime(tap1.getDateTime())
                .finishTime(tap2.getDateTime())
                .fromStopId(tap1.getStopId())
                .toStopId(tap2.getStopId())
                .chargeAmount(BigDecimal.ZERO)
                .companyId(tap1.getCompanyId())
                .busId(tap1.getBusId())
                .pan(tap1.getPan())
                .status(TripStatus.CANCELLED)
                .build();
    }

    public static Trip getCompletedTrip(Tap tap1, Tap tap2) {
        return Trip.builder()
                .startTime(tap1.getDateTime())
                .finishTime(tap2.getDateTime())
                .fromStopId(tap1.getStopId())
                .toStopId(tap2.getStopId())
                .chargeAmount(BigDecimal.valueOf(3.25))
                .companyId(tap1.getCompanyId())
                .busId(tap1.getBusId())
                .pan(tap1.getPan())
                .status(TripStatus.COMPLETED)
                .build();
    }

    private static LocalDateTime getTestDateWithSec(int second) {
        return LocalDateTime.of(2022, 1, 1, 10, 0, second);
    }
}
