package com.daimon.recruiting.candidate.service;

import com.daimon.recruiting.candidate.config.CacheConfig;
import com.daimon.recruiting.candidate.dto.CandidateDto;
import com.daimon.recruiting.candidate.dto.CandidateSearchCriteriaDto;
import com.daimon.recruiting.candidate.entity.Candidate;
import com.daimon.recruiting.candidate.entity.City;
import com.daimon.recruiting.candidate.entity.Skill;
import com.daimon.recruiting.candidate.enums.FilterOperation;
import com.daimon.recruiting.candidate.repository.CandidateRepository;
import com.daimon.recruiting.candidate.repository.SkillRepository;
import com.daimon.recruiting.candidate.repository.specification.CandidateSpecificationBuilder;
import com.daimon.recruiting.candidate.service.validator.CandidateValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidateService {

    private static final String CANDIDATE_NOT_FOUND = "Candidate with id %d not found";

    private final CandidateRepository candidateRepository;
    private final SkillRepository skillRepository;
    private final CandidateValidator candidateValidator;

    @Transactional(readOnly = true)
    @Cacheable(CacheConfig.CANDIDATES_CACHE)
    public Page<CandidateDto> findCandidates(Pageable pageable, CandidateSearchCriteriaDto searchCriteriaDto) {
        var specificationBuilder = new CandidateSpecificationBuilder();
        specificationBuilder.with("firstName", FilterOperation.EQUAL, searchCriteriaDto.getFirstName());
        specificationBuilder.with("lastName", FilterOperation.EQUAL, searchCriteriaDto.getLastName());
        specificationBuilder.with("middleName", FilterOperation.EQUAL, searchCriteriaDto.getMiddleName());
        specificationBuilder.with("phone", FilterOperation.LIKE, searchCriteriaDto.getPhone());
        specificationBuilder.with("city", "name", FilterOperation.LIKE, searchCriteriaDto.getCityName());
        specificationBuilder.with("city", "id", FilterOperation.EQUAL, searchCriteriaDto.getCityId());
        specificationBuilder.with("skills", "name", FilterOperation.EQUAL_LIST, searchCriteriaDto.getSkills());
        var specification = specificationBuilder.build();
        if (specification.isPresent()) {
            return candidateRepository.findAll(specification.get(), pageable).map(CandidateDto::from);
        } else {
            return candidateRepository.findAll(pageable).map(CandidateDto::from);
        }
    }

    @Transactional(readOnly = true)
    public CandidateDto findById(long id) {
        var candidateOptional = candidateRepository.findById(id);
        var candidate = candidateOptional
                .orElseThrow(() -> new NoSuchElementException(String.format(CANDIDATE_NOT_FOUND, id)));
        return CandidateDto.from(candidate);
    }

    @Transactional
    public CandidateDto createCandidate(CandidateDto candidateDto) {
        candidateValidator.validateIfCityIsPresent(candidateDto.getCity().getId());
        var candidate = Candidate.from(candidateDto);
        updateSkills(candidate.getSkills());
        var createdCandidate = candidateRepository.save(candidate);
        return CandidateDto.from(createdCandidate);
    }

    @Transactional
    public CandidateDto updateCandidate(long id, CandidateDto candidateDto) {
        var candidateOptional = candidateRepository.findById(id);
        var candidate = candidateOptional
                .orElseThrow(() -> new NoSuchElementException(String.format(CANDIDATE_NOT_FOUND, id)));
        candidateValidator.validateIfCityIsPresent(candidateDto.getCity().getId());
        candidate.setFirstName(candidateDto.getFirstName());
        candidate.setLastName(candidateDto.getLastName());
        candidate.setMiddleName(candidateDto.getMiddleName());
        candidate.setPhone(candidateDto.getPhone());
        candidate.setCity(City.from(candidateDto.getCity()));
        var skills = candidateDto.getSkills().stream()
                .map(Skill::from)
                .collect(Collectors.toSet());
        candidate.setSkills(skills);
        updateSkills(candidate.getSkills());
        return CandidateDto.from(candidate);
    }

    private void updateSkills(Set<Skill> skills) {
        skills.forEach(skill -> {
            if (skill.getId() == null) {
                var foundedSkill = skillRepository.findByNameIgnoreCase(skill.getName());
                if (foundedSkill.isPresent()) {
                    skill.setId(foundedSkill.get().getId());
                } else {
                    skillRepository.save(skill);
                }
            } else {
                candidateValidator.validateIfSkillIsPresent(skill.getId());
            }
        });
    }

    @Transactional
    public void deleteCandidate(long id) {
        var candidateOptional = candidateRepository.findById(id);
        if (candidateOptional.isPresent()) {
            candidateRepository.delete(candidateOptional.get());
        } else {
            throw new NoSuchElementException(String.format(CANDIDATE_NOT_FOUND, id));
        }

    }

}
