package com.wego.carpark.intergration.govsg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonMap;

@RequiredArgsConstructor
@Slf4j
@Service
public class GovSgServiceImpl implements GovSgService {
    private static final String DATE_TIME_PARAM = "date_time";
    private static  final String ITEMS_FIELD = "items";
    private static  final String CODE_FIELD = "code";
    private static  final String CAR_PARK_DATA_FIELD = "carpark_data";
    private static  final String CAR_PARK_NUMBER_FIELD = "carpark_number";
    private static  final String CAR_PARK_INFO_FIELD = "carpark_info";
    private static  final String TOTAL_LOTS_FIELD = "total_lots";
    private static  final String LOTS_AVAILABLE_FIELD = "lots_available";

    private final RestTemplate govSgRestTemplate;
    private final GovSgConfig govSgConfig;

    @Override
    public List<GovSgCarParkAvailability> getCarParkAvailability() {
        try {
            log.info("Sending request to {}", govSgConfig.getGetCarParkAvailabilityUrl());
            String json = govSgRestTemplate.getForObject(govSgConfig.getGetCarParkAvailabilityUrl(),
                    String.class, singletonMap(DATE_TIME_PARAM, Instant.now()));
            return parseCarParkAvailabilities(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private List<GovSgCarParkAvailability> parseCarParkAvailabilities(String json) throws JsonProcessingException {
        List<GovSgCarParkAvailability> carParkAvailabilityResponses = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
        if (!isSuccess(rootNode)) {
            return emptyList();
        }

        JsonNode itemNode = rootNode.get(ITEMS_FIELD).get(0);
        if (itemNode.isNull()) {
            return emptyList();
        }

        ArrayNode carParkDataNodes = (ArrayNode) itemNode.get(CAR_PARK_DATA_FIELD);
        carParkDataNodes.elements().forEachRemaining((carParkDataNode -> {
            JsonNode carParkInfoNode = carParkDataNode.get(CAR_PARK_INFO_FIELD).get(0);
            carParkAvailabilityResponses.add(
                    GovSgCarParkAvailability.builder()
                            .carParkNo(carParkDataNode.get(CAR_PARK_NUMBER_FIELD).asText())
                            .totalLots(carParkInfoNode.get(TOTAL_LOTS_FIELD).asInt())
                            .availableLots(carParkInfoNode.get(LOTS_AVAILABLE_FIELD).asInt())
                            .build()
            );
        }));

        return carParkAvailabilityResponses;
    }

    private boolean isSuccess(JsonNode root) {
        if (root.hasNonNull(CODE_FIELD)) {
            return root.get(CODE_FIELD).isBoolean();
        }
        return true;
    }
}
