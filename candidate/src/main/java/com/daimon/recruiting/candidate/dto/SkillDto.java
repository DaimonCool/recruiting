package com.daimon.recruiting.candidate.dto;

import com.daimon.recruiting.candidate.entity.Skill;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class SkillDto {

    private Long id;
    @Size(max = 100)
    private String name;

    public static SkillDto from(Skill skill) {
        var dto = new SkillDto();
        dto.setId(skill.getId());
        dto.setName(skill.getName());
        return dto;
    }
}
