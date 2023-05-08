package com.littlepay.tripfinder.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class Tap {
    private long id;
    private LocalDateTime dateTime;
    private TapType tapType;
    private String stopId;
    private String companyId;
    private String busId;
    private String pan;

    public String getCompanyBusAndPan() {
        return String.join(":", companyId, busId, pan);
    }

    public boolean isMultiTap(Tap tap) {
        return this.getTapType() == tap.getTapType()
                && this.getStopId().equals(tap.getStopId())
                && this.getCompanyId().equals(tap.getCompanyId())
                && this.getBusId().equals(tap.getBusId())
                && this.getPan().equals(tap.getPan());
    }
}
