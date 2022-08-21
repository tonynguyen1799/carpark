package com.wego.carpark.service;

import com.wego.carpark.controller.beans.NearestCarParkResponse;
import com.wego.carpark.repository.CarParkInformationRepository;
import com.wego.carpark.repository.dto.NearestCarParkDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class CarParkService {
    private final CarParkInformationRepository carParkInformationRepository;

    public List<NearestCarParkResponse> getNearestCarPark(double latitude, double longitude, int page, int rows) {
        return carParkInformationRepository.getNearestCarParks(latitude, longitude, PageRequest.of(page, rows))
                .stream().map(this::toNearestCarParkResponse)
                .collect(toList());
    }

    private NearestCarParkResponse toNearestCarParkResponse(NearestCarParkDto nearestCarParkDto) {
        return NearestCarParkResponse.builder()
                .address(nearestCarParkDto.getAddress())
                .latitude(nearestCarParkDto.getLatitude())
                .longitude(nearestCarParkDto.getLongitude())
                .totalLots(nearestCarParkDto.getTotalLots())
                .availableLots(nearestCarParkDto.getAvailableLots())
                .build();
    }
}
