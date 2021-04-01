package com.daimon.recruiting.candidate.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class City {

    @Id
    @GeneratedValue
    private long id;

    private String name;
}
