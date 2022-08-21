package com.wego.carpark.model;

import lombok.*;

import javax.persistence.*;

@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CarParkAvailability {
    @Id
    private String carParkNo;
    private Integer totalLots = 0;
    private Integer availableLots = 0;

    @OneToOne
    @MapsId
    @JoinColumn(name = "car_park_no")
    private CarParkInformation carParkInformation;
}
