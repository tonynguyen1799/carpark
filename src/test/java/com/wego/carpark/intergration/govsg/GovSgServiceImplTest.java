package com.wego.carpark.intergration.govsg;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class GovSgServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Autowired
    private GovSgConfig govSgConfig;

    @InjectMocks
    private GovSgServiceImpl govSgService;

    @BeforeEach
    public void setUp() {
        govSgService = new GovSgServiceImpl(restTemplate, govSgConfig);
    }

    @Test
    public void testGetCarParkAvailability_whenSuccess_thenReturn() throws IOException {
        try (InputStream json =
                     new ClassPathResource("govsg/getCarAvailability_success.json").getInputStream()) {
            String getCarAvailabilityJson = IOUtils.toString(json, Charset.defaultCharset());
            when(restTemplate.getForObject(anyString(), any(), anyMap()))
                    .thenReturn(getCarAvailabilityJson);
            List<GovSgCarParkAvailability> carParkAvailabilities = govSgService.getCarParkAvailability();
            Assertions.assertEquals(2, carParkAvailabilities.size());
        }
    }

    @Test
    public void testGetCarParkAvailability_whenFailed_thenReturnEmpty() throws IOException {
        try (InputStream json =
                     new ClassPathResource("govsg/getCarAvailability_failed.json").getInputStream()) {
            String getCarAvailabilityJson = IOUtils.toString(json, Charset.defaultCharset());
            when(restTemplate.getForObject(anyString(), any(), anyMap()))
                    .thenReturn(getCarAvailabilityJson);
            List<GovSgCarParkAvailability> carParkAvailabilities = govSgService.getCarParkAvailability();
            Assertions.assertTrue(carParkAvailabilities.isEmpty());
        }
    }
}
