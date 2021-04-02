package com.daimon.recruiting.candidate.dto;

import com.daimon.recruiting.candidate.entity.Candidate;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class CandidateDto implements Serializable {

    private static final long serialVersionUID = -7545508808257562682L;

    private Long id;
    @NotBlank
    @Size(max = 100)
    private String firstName;
    @NotBlank
    @Size(max = 100)
    private String lastName;
    @Size(max = 100)
    private String middleName;
    @Size(max = 20)
    private String phone;
    @NotNull
    @Valid
    private CityDto city;
    @NotNull
    private Set<SkillDto> skills;

    public static CandidateDto from(Candidate candidate) {
        var dto = new CandidateDto();
        dto.setId(candidate.getId());
        dto.setFirstName(candidate.getFirstName());
        dto.setLastName(candidate.getLastName());
        dto.setMiddleName(candidate.getMiddleName());
        dto.setPhone(candidate.getPhone());
        dto.setCity(CityDto.from(candidate.getCity()));
        var skills = candidate.getSkills()
                .stream()
                .map(SkillDto::from)
                .collect(Collectors.toSet());
        dto.setSkills(skills);
        return dto;
    }
}
