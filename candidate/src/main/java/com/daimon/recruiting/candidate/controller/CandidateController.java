package com.daimon.recruiting.candidate.controller;

import com.daimon.recruiting.candidate.dto.CandidateDto;
import com.daimon.recruiting.candidate.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/candidates")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    @GetMapping("/{id}")
    public CandidateDto getCandidateById(@PathVariable long id) {
        return candidateService.findById(id);
    }

    @PostMapping
    public CandidateDto createCandidate(CandidateDto candidateDto) {
        return candidateService.createCandidate(candidateDto);
    }
}
