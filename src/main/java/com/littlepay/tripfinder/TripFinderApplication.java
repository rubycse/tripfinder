package com.littlepay.tripfinder;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class TripFinderApplication {
    public static void main(String[] args) {
        SpringApplication.run(TripFinderApplication.class, args);
    }
}