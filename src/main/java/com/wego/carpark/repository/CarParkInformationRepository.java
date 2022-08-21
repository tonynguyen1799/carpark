package com.wego.carpark.repository;

import com.wego.carpark.model.CarParkInformation;
import com.wego.carpark.repository.dto.NearestCarParkDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarParkInformationRepository extends JpaRepository<CarParkInformation, String> {
    @Query(
        "select new com.wego.carpark.repository.dto.NearestCarParkDto ( " +
        "   cpi.address as address, cpi.latitude as latitude, cpi.longitude as longitude, " +
        "   cpa.totalLots as totalLots, cpa.availableLots as availableLots " +
        ") " +
        "from CarParkInformation cpi inner join CarParkAvailability cpa " +
        "   on cpi.carParkNo = cpa.carParkNo " +
        "order by ( " +
        "   acos(sin(:latitude) * sin(cpi.latitude) + cos(:latitude) * cos(cpi.latitude) * cos(cpi.longitude - (:longitude))) * 6371 " +
        ") "
    )
    List<NearestCarParkDto> getNearestCarParks(@Param("latitude") @NonNull Double latitude,
                                               @Param("longitude") @NonNull Double longitude,
                                               Pageable pageable);
}
