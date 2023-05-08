package com.littlepay.tripfinder;

import com.littlepay.tripfinder.domain.Tap;
import com.littlepay.tripfinder.domain.Trip;
import com.littlepay.tripfinder.service.TapReader;
import com.littlepay.tripfinder.service.TripFinder;
import com.littlepay.tripfinder.service.TripWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Profile("!test")
public class TapToTripCsvGenerator implements CommandLineRunner {
    private final TapReader tapReader;
    private final TripFinder tripFinder;
    private final TripWriter tripWriter;

    @Override
    public void run(String... args) throws Exception {
        List<Tap> taps = tapReader.readAllTaps();
        List<Trip> trips = tripFinder.findTrips(taps);
        tripWriter.writeAllTrips(trips);
    }
}
