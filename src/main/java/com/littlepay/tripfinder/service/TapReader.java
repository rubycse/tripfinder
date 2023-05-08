package com.littlepay.tripfinder.service;

import com.littlepay.tripfinder.domain.Tap;
import com.littlepay.tripfinder.domain.TapType;
import com.littlepay.tripfinder.utils.DateUtils;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TapReader {

    @Value("${tap.file.path}")
    private String tapFilePath;

    @SneakyThrows
    public List<Tap> readAllTaps() {
        List<Tap> list = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(tapFilePath))) {
            try (CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()) {
                String[] line;
                while ((line = csvReader.readNext()) != null) {
                    list.add(extractTap(line));
                }
            }
        }

        return list;
    }

    private Tap extractTap(String[] line) {
        Arrays.setAll(line, i -> line[i].trim());
        int index = 0;
        return Tap.builder()
                .id(Long.parseLong(line[index++]))
                .dateTime(DateUtils.parseDate(line[index++]))
                .tapType(TapType.valueOf(line[index++]))
                .stopId(line[index++])
                .companyId(line[index++])
                .busId(line[index++])
                .pan(line[index])
                .build();
    }
}
