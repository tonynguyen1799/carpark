package com.wego.carpark.repository;

import com.wego.carpark.model.CarParkAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarParkAvailabilityRepository extends JpaRepository<CarParkAvailability, String> {
}
