package com.littlepay.tripfinder.domain;

import com.littlepay.tripfinder.utils.DateUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@EqualsAndHashCode
public class Trip {
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private String fromStopId;
    private String toStopId;
    private BigDecimal chargeAmount;
    private String companyId;
    private String busId;
    private String pan;
    private TripStatus status;

    public long getDurationInSecs() {
        return DateUtils.getDurationInSec(startTime, finishTime);
    }
}
