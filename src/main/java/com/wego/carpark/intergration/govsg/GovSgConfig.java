package com.wego.carpark.intergration.govsg;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "gov-sg")
@Getter
@Setter
public class GovSgConfig {
    private String getCarParkAvailabilityUrl;
}
