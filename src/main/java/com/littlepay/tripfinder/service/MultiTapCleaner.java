package com.littlepay.tripfinder.service;

import com.littlepay.tripfinder.domain.Tap;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Cleans up multiple tap-ons and tap-offs that occur at the same station
 * by only keeping the first tap and removing the subsequent ones.
 * <p>
 * For taps [(Stop1, ON), (Stop1, ON), (Stop1, ON), (Stop2, OFF), (Stop2, OFF)]
 * it will return [(Stop1, ON), (Stop2, OFF)].
 */
@Component
public class MultiTapCleaner {
    public List<Tap> removeMultiTaps(List<Tap> taps) {
        if (taps == null || taps.size() == 0) return taps;

        List<Tap> filteredOutMultiTaps = new ArrayList<>();
        int start = 0;
        int end = 0;
        while (end < taps.size()) {
            Tap startTap = taps.get(start);
            Tap endTap = taps.get(end);
            if (start == end) {
                filteredOutMultiTaps.add(startTap);
                end++;
            } else if (startTap.isMultiTap(endTap)) {
                end++;
            } else {
                start = end;
            }
        }

        return filteredOutMultiTaps;
    }
}
