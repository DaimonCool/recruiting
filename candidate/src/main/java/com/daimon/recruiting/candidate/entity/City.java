package com.daimon.recruiting.candidate.entity;

import com.daimon.recruiting.candidate.dto.CityDto;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public static City from(CityDto cityDto) {
        var city = new City();
        city.setId(cityDto.getId());
        city.setName(cityDto.getName());
        return city;
    }
}
