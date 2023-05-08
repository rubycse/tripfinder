package com.littlepay.tripfinder.service;

import com.littlepay.tripfinder.domain.Tap;
import com.littlepay.tripfinder.domain.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TripFinder {

    private final CompositeTripDetector tripDetector;
    private final MultiTapCleaner multiTapCleaner;

    public List<Trip> findTrips(List<Tap> taps) {

        List<Trip> trips = new ArrayList<>();

        taps.stream()
                .collect(Collectors.groupingBy(Tap::getCompanyBusAndPan))
                .values()
                .forEach(groupedTaps -> {
                    groupedTaps.sort(Comparator.comparing(Tap::getDateTime));
                    List<Tap> cleanTaps = multiTapCleaner.removeMultiTaps(groupedTaps);

                    for (int current = 0; current < cleanTaps.size(); current++) {
                        Tap currentTap = cleanTaps.get(current);
                        Tap nextTap = (current + 1) == cleanTaps.size() ? null : cleanTaps.get(current + 1);
                        trips.add(tripDetector.detectTrip(currentTap, nextTap));
                    }
                });

        return trips.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
