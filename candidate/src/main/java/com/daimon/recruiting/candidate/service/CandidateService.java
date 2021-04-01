package com.daimon.recruiting.candidate.service;

import com.daimon.recruiting.candidate.dto.CandidateDto;
import com.daimon.recruiting.candidate.repository.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CandidateService {

    private final CandidateRepository candidateRepository;

    @Transactional(readOnly = true)
    public CandidateDto findById(long id) {
        var candidate = candidateRepository.findById(id);
        return CandidateDto.from(candidate.orElseThrow());
    }

    @Transactional
    public CandidateDto createCandidate(CandidateDto candidateDto) {
        return null;
    }
}
