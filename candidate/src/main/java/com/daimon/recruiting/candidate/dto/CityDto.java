package com.daimon.recruiting.candidate.dto;

import com.daimon.recruiting.candidate.entity.City;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class CityDto implements Serializable {

    @NotNull
    private Long id;
    @Size(max = 100)
    private String name;

    public static CityDto from(City city) {
        var dto = new CityDto();
        dto.setId(city.getId());
        dto.setName(city.getName());
        return dto;
    }
}
