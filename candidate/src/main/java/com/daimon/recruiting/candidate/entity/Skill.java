package com.daimon.recruiting.candidate.entity;

import com.daimon.recruiting.candidate.dto.SkillDto;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "skill_id_seq")
    @SequenceGenerator(name = "skill_id_seq")
    private Long id;
    private String name;

    public static Skill from(SkillDto skillDto) {
        var skill = new Skill();
        skill.setId(skill.getId());
        skill.setName(skillDto.getName());
        return skill;
    }
}
