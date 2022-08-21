package com.wego.carpark.repository;

import com.wego.carpark.repository.dto.NearestCarParkDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@DataJpaTest
@TestPropertySource(properties = {"spring.flyway.enabled=false"})
public class CarParkInformationRepositoryTest {
    @Autowired
    private CarParkInformationRepository carParkInformationRepository;

    @Test
    @Sql(scripts = {"classpath:data/carpark_information.sql", "classpath:data/carpark_availability.sql"})
    public void testGetNearestCarParks_whenFirstPage_thenReturnNearestCarPark() {
        List<NearestCarParkDto> nearestCarParkDtos = carParkInformationRepository.getNearestCarParks(
                1.37326, 103.897, PageRequest.of(0, 1)
        );
        Assertions.assertEquals(1, nearestCarParkDtos.size());

        NearestCarParkDto nearestCarParkDto = nearestCarParkDtos.get(0);
        Assertions.assertEquals("BLK 229/230 ANG MO KIO ST 22", nearestCarParkDto.getAddress());
        Assertions.assertEquals(1.274885277795654, nearestCarParkDto.getLatitude());
        Assertions.assertEquals(103.93169335730623, nearestCarParkDto.getLongitude());
        Assertions.assertEquals(115, nearestCarParkDto.getAvailableLots());
        Assertions.assertEquals(205, nearestCarParkDto.getTotalLots());
    }

    @Test
    @Sql(scripts = {"classpath:data/carpark_information.sql", "classpath:data/carpark_availability.sql"})
    public void testGetNearestCarParks_whenLastPage_thenReturnFurthestCarPark() {
        List<NearestCarParkDto> nearestCarParkDtos = carParkInformationRepository.getNearestCarParks(
                1.37326, 103.897, PageRequest.of(2, 1)
        );
        Assertions.assertEquals(1, nearestCarParkDtos.size());

        NearestCarParkDto nearestCarParkDto = nearestCarParkDtos.get(0);
        Assertions.assertEquals("BLK 232/233 ANG MO KIO ST 22", nearestCarParkDto.getAddress());
        Assertions.assertEquals(1.2730075669421534, nearestCarParkDto.getLatitude());
        Assertions.assertEquals(103.93118696205491, nearestCarParkDto.getLongitude());
        Assertions.assertEquals(49, nearestCarParkDto.getAvailableLots());
        Assertions.assertEquals(199, nearestCarParkDto.getTotalLots());
    }

    @Test
    @Sql(scripts = {"classpath:data/carpark_information.sql", "classpath:data/carpark_availability.sql"})
    public void testGetNearestCarParks_whenGetAll_thenReturnOnlyAvailableCarPark() {
        List<NearestCarParkDto> nearestCarParkDtos = carParkInformationRepository.getNearestCarParks(
                1.37326, 103.897, PageRequest.of(0, 10)
        );
        Assertions.assertEquals(3, nearestCarParkDtos.size());
    }
}
