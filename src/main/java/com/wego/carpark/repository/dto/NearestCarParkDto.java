package com.wego.carpark.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NearestCarParkDto {
    private final String address;
    private final Double latitude;
    private final Double longitude;
    private final Integer totalLots;
    private final Integer availableLots;
}
