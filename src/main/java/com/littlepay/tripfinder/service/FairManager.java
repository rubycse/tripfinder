package com.littlepay.tripfinder.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides a faster access for finding cost between any two stoppages.
 */

public class FairManager {
    private static Map<String, BigDecimal> STOPPAGE_FAIR_MAP;
    private static Map<String, BigDecimal> MAX_FAIR_MAP;

    public FairManager() {
        STOPPAGE_FAIR_MAP = new HashMap<>();
        MAX_FAIR_MAP = new HashMap<>();
    }

    public void addFair(String fromStoppage, String toStoppage, BigDecimal cost) {
        String key = createKey(fromStoppage, toStoppage);
        String reverseKey = createKey(toStoppage, fromStoppage);
        STOPPAGE_FAIR_MAP.put(key, cost);
        STOPPAGE_FAIR_MAP.put(reverseKey, cost);
        MAX_FAIR_MAP.put(fromStoppage, currentMaxCost(fromStoppage).max(cost));
        MAX_FAIR_MAP.put(toStoppage, currentMaxCost(toStoppage).max(cost));
    }

    public BigDecimal getFair(String from, String to) {
        return STOPPAGE_FAIR_MAP.get(createKey(from, to));
    }

    public BigDecimal getMaxFair(String stoppage) {
        return MAX_FAIR_MAP.get(stoppage);
    }

    private String createKey(String fromStoppage, String toStoppage) {
        return fromStoppage + ":" + toStoppage;
    }

    private BigDecimal currentMaxCost(String stoppage) {
        return MAX_FAIR_MAP.getOrDefault(stoppage, BigDecimal.ZERO);
    }
}
