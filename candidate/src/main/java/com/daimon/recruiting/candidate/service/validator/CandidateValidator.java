package com.daimon.recruiting.candidate.service.validator;

import com.daimon.recruiting.candidate.repository.CityRepository;
import com.daimon.recruiting.candidate.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class CandidateValidator {

    private static final String CITY_NOT_FOUND = "City with id %d not found";
    private static final String SKILL_NOT_FOUND = "Skill with id %d not found";

    private final CityRepository cityRepository;
    private final SkillRepository skillRepository;

    @Transactional(readOnly = true)
    public void validateIfCityIsPresent(long cityId) {
        if (!cityRepository.existsById(cityId)) {
            throw new NoSuchElementException(String.format(CITY_NOT_FOUND, cityId));
        }
    }
    @Transactional(readOnly = true)
    public void validateIfSkillIsPresent(long skillId) {
        if (!skillRepository.existsById(skillId)) {
            throw new NoSuchElementException(String.format(SKILL_NOT_FOUND, skillId));
        }
    }

}
