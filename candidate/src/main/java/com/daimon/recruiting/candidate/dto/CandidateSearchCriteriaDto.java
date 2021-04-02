package com.daimon.recruiting.candidate.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class CandidateSearchCriteriaDto implements Serializable {

    private static final long serialVersionUID = 8535720001422855868L;

    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    private Long cityId;
    private String cityName;
    private Set<String> skills;

}
