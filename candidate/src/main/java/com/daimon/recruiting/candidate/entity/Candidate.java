package com.daimon.recruiting.candidate.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Candidate {

    @Id
    @GeneratedValue
    private long id;

    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;

    @ManyToOne
    private City city;

    @ManyToMany
    @JoinTable(
            name = "candidate_skill",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<Skill> skills;
}
