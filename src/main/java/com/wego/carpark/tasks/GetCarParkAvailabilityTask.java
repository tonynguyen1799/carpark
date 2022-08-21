package com.wego.carpark.tasks;

import com.wego.carpark.intergration.govsg.GovSgService;
import com.wego.carpark.model.CarParkAvailability;
import com.wego.carpark.repository.CarParkAvailabilityRepository;
import com.wego.carpark.repository.CarParkInformationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetCarParkAvailabilityTask {
    private final GovSgService govSgService;

    private final CarParkInformationRepository carParkInformationRepository;
    private final CarParkAvailabilityRepository carParkAvailabilityRepository;

    @Scheduled(fixedDelay = 60000, initialDelay = 10000)
    @Transactional
    public void getCarParkAvailability() {
        log.info("Getting available car parks.");

        carParkAvailabilityRepository.deleteAllInBatch();

        govSgService.getCarParkAvailability()
                .forEach(govSgCarParkAvailability ->
                        carParkInformationRepository
                                .findById(govSgCarParkAvailability.getCarParkNo())
                                .ifPresent(carParkInformation -> {
                                    carParkInformation.setCarParkAvailability(new CarParkAvailability(
                                            govSgCarParkAvailability.getCarParkNo(),
                                            govSgCarParkAvailability.getTotalLots(),
                                            govSgCarParkAvailability.getAvailableLots(),
                                            carParkInformation
                                    ));
                                    carParkInformationRepository.save(carParkInformation);
                                }));
    }
}
