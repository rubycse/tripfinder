package com.littlepay.tripfinder;

import com.littlepay.tripfinder.service.FairManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class TripFinderConfiguration {
    @Bean
    public FairManager getFairManager() {
        FairManager fairManager = new FairManager();
        initializeFairManager(fairManager);
        return fairManager;
    }

    private void initializeFairManager(FairManager fairManager) {
        fairManager.addFair("Stop1", "Stop2", BigDecimal.valueOf(3.25));
        fairManager.addFair("Stop2", "Stop3", BigDecimal.valueOf(5.50));
        fairManager.addFair("Stop1", "Stop3", BigDecimal.valueOf(7.30));
    }
}
