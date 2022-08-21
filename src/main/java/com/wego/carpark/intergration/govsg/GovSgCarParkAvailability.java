package com.wego.carpark.intergration.govsg;

import lombok.*;

@Builder
@Getter
public class GovSgCarParkAvailability {
    private final String carParkNo;
    private final int totalLots;
    private final int availableLots;
}
