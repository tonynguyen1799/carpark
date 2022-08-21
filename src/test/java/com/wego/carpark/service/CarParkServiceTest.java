package com.wego.carpark.service;

import com.wego.carpark.controller.beans.NearestCarParkResponse;
import com.wego.carpark.repository.CarParkInformationRepository;
import com.wego.carpark.repository.dto.NearestCarParkDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;

@SpringBootTest
@ActiveProfiles("test")
public class CarParkServiceTest {
    @Mock
    private CarParkInformationRepository carParkInformationRepository;

    @InjectMocks
    private CarParkService carParkService;

    @BeforeEach
    public void setUp() {
        carParkService = new CarParkService(carParkInformationRepository);
    }

    @Test
    public void testGetNearestCarPark_thenReturn() {
        Mockito.when(carParkInformationRepository
                .getNearestCarParks(anyDouble(), anyDouble(), any(PageRequest.class)))
                .thenReturn(singletonList(
                        new NearestCarParkDto("BLK 229/230 ANG MO KIO ST 22",
                                1.2748852777956541,
                                103.931693357306231,
                                205,
                                115)
                ));

        List<NearestCarParkResponse> nearestCarParkResponses = carParkService.getNearestCarPark(
                1.37326, 103.897, 0, 10
        );

        assertEquals(1, nearestCarParkResponses.size());

        NearestCarParkResponse nearestCarParkResponse = nearestCarParkResponses.get(0);
        assertEquals("BLK 229/230 ANG MO KIO ST 22", nearestCarParkResponse.getAddress());
        assertEquals(1.2748852777956541, nearestCarParkResponse.getLatitude());
        assertEquals(103.931693357306231, nearestCarParkResponse.getLongitude());
        assertEquals(205, nearestCarParkResponse.getTotalLots());
        assertEquals(115, nearestCarParkResponse.getAvailableLots());
    }

}
