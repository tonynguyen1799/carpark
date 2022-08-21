package com.wego.carpark.controller.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class NearestCarParkResponse {
    @NonNull
    @JsonProperty(value = "address")
    private final String address;

    @NonNull
    @JsonProperty(value = "latitude")
    private final double latitude;

    @NonNull
    @JsonProperty(value = "longitude")
    private final double longitude;

    @NonNull
    @JsonProperty(value = "total_lots")
    private final int totalLots;

    @NonNull
    @JsonProperty(value = "available_lots")
    private final int availableLots;
}
