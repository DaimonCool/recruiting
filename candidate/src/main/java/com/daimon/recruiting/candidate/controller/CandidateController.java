package com.daimon.recruiting.candidate.controller;

import com.daimon.recruiting.candidate.dto.CandidateDto;
import com.daimon.recruiting.candidate.dto.CandidateSearchCriteriaDto;
import com.daimon.recruiting.candidate.service.CandidateService;
import com.daimon.recruiting.candidate.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/candidates")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;
    private final ResumeService resumeService;

    @GetMapping
    public Page<CandidateDto> getCandidates(Pageable pageable, CandidateSearchCriteriaDto searchCriteriaDto) {
        return candidateService.findCandidates(pageable, searchCriteriaDto);
    }

    @GetMapping("/{id}")
    public CandidateDto getCandidateById(@PathVariable long id) {
        return candidateService.findById(id);
    }

    @PostMapping
    public CandidateDto createCandidate(@Valid @RequestBody CandidateDto candidateDto) {
        return candidateService.createCandidate(candidateDto);
    }

    @PutMapping("/{id}")
    public CandidateDto updateCandidate(@PathVariable long id,
                                        @Valid @RequestBody CandidateDto candidateDto) {
        return candidateService.updateCandidate(id, candidateDto);
    }

    @DeleteMapping("/{id}")
    public void updateCandidate(@PathVariable long id) {
        candidateService.deleteCandidate(id);
    }

    @GetMapping("/{id}/resumes")
    public byte[] getResumeForCandidate(@PathVariable long id) {
        return resumeService.findResume(id);
    }

    @PostMapping("/{id}/resumes")
    public void uploadResumeForCandidate(@PathVariable long id,
                                         @RequestParam("file") MultipartFile file) {
        resumeService.saveResume(id, file);
    }

    @PutMapping("/{id}/resumes")
    public void updateResumeForCandidate(@PathVariable long id,
                                         @RequestParam("file") MultipartFile file) {
        resumeService.updateResume(id, file);
    }

    @DeleteMapping("/{id}/resumes")
    public void deleteResumeForCandidate(@PathVariable long id) {
        resumeService.deleteResume(id);
    }
}
