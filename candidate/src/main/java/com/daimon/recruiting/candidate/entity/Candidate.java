package com.daimon.recruiting.candidate.entity;

import com.daimon.recruiting.candidate.dto.CandidateDto;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidate_id_seq")
    @SequenceGenerator(name = "candidate_id_seq")
    private Long id;

    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;

    @ManyToOne(cascade = CascadeType.ALL)
    private City city;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "candidate_skill",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<Skill> skills;

    public static Candidate from(CandidateDto candidateDto) {
        var candidate = new Candidate();
        candidate.setId(candidateDto.getId());
        candidate.setFirstName(candidateDto.getFirstName());
        candidate.setLastName(candidateDto.getLastName());
        candidate.setMiddleName(candidateDto.getMiddleName());
        candidate.setPhone(candidateDto.getPhone());
        candidate.setCity(City.from(candidateDto.getCity()));
        var skills = candidateDto.getSkills().stream()
                .map(Skill::from)
                .collect(Collectors.toSet());
        candidate.setSkills(skills);
        return candidate;
    }
}
