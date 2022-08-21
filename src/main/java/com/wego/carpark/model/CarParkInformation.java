package com.wego.carpark.model;

import lombok.*;

import javax.persistence.*;

@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CarParkInformation {
    @Id
    @Column(length = 7)
    private String carParkNo;
    private String address;
    private Double latitude;
    private Double longitude;

    @OneToOne(mappedBy = "carParkInformation", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private CarParkAvailability carParkAvailability;
}
