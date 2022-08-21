package com.wego.carpark.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wego.carpark.controller.beans.NearestCarParkResponse;
import com.wego.carpark.service.CarParkService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarParkController.class)
public class CarParkControllerTest {

    @MockBean
    private CarParkService carParkService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetNearestCarParks_thenReturn() throws Exception {
        Mockito.when(carParkService.getNearestCarPark(anyDouble(), anyDouble(), anyInt(), anyInt())).thenReturn(
                asList(
                        NearestCarParkResponse.builder()
                                .address("BLK 223/226/226A-226D ANG MO KIO ST 22")
                                .latitude(1.274387933662743)
                                .longitude(103.93022854711126)
                                .totalLots(368)
                                .availableLots(163)
                                .build(),
                        NearestCarParkResponse.builder()
                                .address("BLK 229/230 ANG MO KIO ST 22")
                                .latitude(1.274885277795654)
                                .longitude(103.93169335730623)
                                .totalLots(205)
                                .availableLots(115)
                                .build()
                )
        );

        mockMvc.perform(get("/carparks/nearest")
                .param("latitude", "1.37326")
                .param("longitude", "103.897")
                .param("page", "1")
                .param("rows", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].address", is("BLK 223/226/226A-226D ANG MO KIO ST 22")))
                .andExpect(jsonPath("$.[0].latitude", is(1.274387933662743)))
                .andExpect(jsonPath("$.[0].longitude", is(103.93022854711126)))
                .andExpect(jsonPath("$.[0].total_lots", is(368)))
                .andExpect(jsonPath("$.[0].available_lots", is(163)));
    }

    @Test
    public void testGetNearestCarParks_whenNoCarPark_thenReturnEmpty() throws Exception {
        Mockito.when(carParkService.getNearestCarPark(anyDouble(), anyDouble(), anyInt(), anyInt()))
                .thenReturn(emptyList());

        mockMvc.perform(get("/carparks/nearest")
                .param("latitude", "1.37326")
                .param("longitude", "103.897")
                .param("page", "1")
                .param("rows", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void testGetNearestCarParks_whenMissingLatitude_thenReturnBadRequest() throws Exception {
        Mockito.when(carParkService.getNearestCarPark(anyDouble(), anyDouble(), anyInt(), anyInt()))
                .thenReturn(emptyList());

        mockMvc.perform(get("/carparks/nearest")
                .param("longitude", "103.897")
                .param("page", "1")
                .param("rows", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetNearestCarParks_whenMissingLongitude_thenReturnBadRequest() throws Exception {
        Mockito.when(carParkService.getNearestCarPark(anyDouble(), anyDouble(), anyInt(), anyInt()))
                .thenReturn(emptyList());

        mockMvc.perform(get("/carparks/nearest")
                .param("latitude", "1.37326")
                .param("page", "1")
                .param("rows", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetNearestCarParks_whenMissingPageAndRows_thenReturnDefault() throws Exception {
        Mockito.when(carParkService.getNearestCarPark(anyDouble(), anyDouble(), anyInt(), anyInt()))
                .thenReturn(emptyList());

        mockMvc.perform(get("/carparks/nearest")
                .param("latitude", "1.37326")
                .param("longitude", "103.897")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(carParkService, times(1))
                .getNearestCarPark(1.37326, 103.897, 0, 10);
    }
}
