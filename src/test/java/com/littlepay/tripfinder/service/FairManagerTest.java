package com.littlepay.tripfinder.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FairManagerTest {

    private FairManager fairManager;
    private String stop1, stop2, stop3;
    private BigDecimal fair1, fair2, fair5;

    @BeforeEach
    void setUp() {
        fairManager = new FairManager();

        stop1 = "Stop1";
        stop2 = "Stop2";
        stop3 = "Stop3";

        fair1 = BigDecimal.valueOf(1);
        fair2 = BigDecimal.valueOf(2);
        fair5 = BigDecimal.valueOf(5);
    }

    @Test
    void test_fairWithoutInitializing() {
        assertEquals(null, fairManager.getFair(stop1, stop2));
    }

    @Test
    void test_fairAfterInit() {
        fairManager.addFair(stop1, stop2, fair1);

        assertEquals(fair1, fairManager.getFair(stop1, stop2));
        assertEquals(fair1, fairManager.getFair(stop2, stop1));
    }

    @Test
    void test_maxFairAfterInit() {
        fairManager.addFair(stop1, stop2, fair1);

        assertEquals(fair1, fairManager.getMaxFair(stop1));
        assertEquals(fair1, fairManager.getMaxFair(stop2));
    }

    @Test
    void test_testFairOfAnotherStop() {
        fairManager.addFair(stop1, stop2, fair1);

        assertEquals(null, fairManager.getFair(stop2, stop3));
    }

    @Test
    void test_testMaxFairOfAnotherStop() {
        fairManager.addFair(stop1, stop2, fair1);

        assertEquals(null, fairManager.getMaxFair(stop3));
    }

    @Test
    void test_testFairOf3Stops() {
        fairManager.addFair(stop1, stop2, fair1);
        fairManager.addFair(stop2, stop3, fair2);
        fairManager.addFair(stop1, stop3, fair5);

        assertEquals(fair1, fairManager.getFair(stop1, stop2));
        assertEquals(fair1, fairManager.getFair(stop2, stop1));

        assertEquals(fair2, fairManager.getFair(stop2, stop3));
        assertEquals(fair2, fairManager.getFair(stop3, stop2));

        assertEquals(fair5, fairManager.getFair(stop1, stop3));
        assertEquals(fair5, fairManager.getFair(stop3, stop1));
    }

    @Test
    void test_testMaxFairOf3Stops() {
        fairManager.addFair(stop1, stop2, fair1);
        fairManager.addFair(stop2, stop3, fair2);
        fairManager.addFair(stop1, stop3, fair5);

        assertEquals(fair5, fairManager.getMaxFair(stop1));
        assertEquals(fair2, fairManager.getMaxFair(stop2));
        assertEquals(fair5, fairManager.getMaxFair(stop3));
    }
}