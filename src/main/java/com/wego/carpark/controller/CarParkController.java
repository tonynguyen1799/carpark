package com.wego.carpark.controller;

import com.wego.carpark.controller.beans.NearestCarParkResponse;
import com.wego.carpark.service.CarParkService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Validated
public class CarParkController {
    private final CarParkService carParkService;

    @GetMapping("/carparks/nearest")
    public List<NearestCarParkResponse> getNearestCarParks(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam(value = "page", required = false, defaultValue = "1") @Min(1) int page,
            @RequestParam(value = "per_page", required = false, defaultValue = "10") @Min(1) int rows) {
        return carParkService.getNearestCarPark(latitude, longitude, page - 1, rows);
    }

}
