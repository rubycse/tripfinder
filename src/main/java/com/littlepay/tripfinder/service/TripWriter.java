package com.littlepay.tripfinder.service;

import com.littlepay.tripfinder.domain.Trip;
import com.littlepay.tripfinder.utils.DateUtils;
import com.opencsv.CSVWriter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class TripWriter {

    @Value("${trip.file.path}")
    private String tripFilePath;

    @SneakyThrows
    public void writeAllTrips(List<Trip> trips) {
        Path path = Paths.get(tripFilePath);
        try (CSVWriter writer = new CSVWriter(new FileWriter(path.toString()),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.NO_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END)) {

            writer.writeNext(getHeader());
            trips.forEach(trip -> writer.writeNext(convertToArray(trip)));
        }
    }

    private String[] getHeader() {
        return new String[]{"Started", "Finished", "DurationSecs", "FromStopId", "ToStopId",
                "ChargeAmount", "CompanyId", "BusID", "PAN", "Status"};
    }

    private String[] convertToArray(Trip trip) {
        return new String[]{
                DateUtils.toString(trip.getStartTime()),
                DateUtils.toString(trip.getFinishTime()),
                String.valueOf(trip.getDurationInSecs()),
                trip.getFromStopId(),
                trip.getToStopId(),
                "$" + trip.getChargeAmount().toString(),
                trip.getCompanyId(),
                trip.getBusId(),
                trip.getPan(),
                trip.getStatus().toString()
        };
    }
}
