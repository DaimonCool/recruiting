package com.daimon.recruiting.candidate.service;

import com.daimon.recruiting.candidate.entity.Candidate;
import com.daimon.recruiting.candidate.exception.FileException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final FileService fileService;
    private final CandidateService candidateService;

    @Value("${resumes.default-folder}")
    private String resumesDefaultFolder;

    @Transactional
    public void saveResume(long id, MultipartFile file) {
        var candidate = candidateService.findEntityById(id);
        var resumeName = createCandidateResumeName(candidate);
        try {
            var resumePath = fileService.uploadFile(resumesDefaultFolder, resumeName, file.getBytes());
            candidate.setResumePath(resumePath);
        } catch (IOException e) {
            throw new FileException();
        }
    }

    @Transactional
    public void updateResume(long id, MultipartFile file) {
        var candidate = candidateService.findEntityById(id);
        var resumeName = createCandidateResumeName(candidate);
        if (StringUtils.isNotEmpty(candidate.getResumePath())) {
            fileService.deleteFile(candidate.getResumePath());
        }
        try {
            var resumeNewPath = fileService.uploadFile(resumesDefaultFolder, resumeName, file.getBytes());
            candidate.setResumePath(resumeNewPath);
        } catch (IOException e) {
            throw new FileException();
        }
    }

    @Transactional
    public void deleteResume(long id) {
        var candidate = candidateService.findEntityById(id);
        fileService.deleteFile(candidate.getResumePath());
        candidate.setResumePath(null);
    }

    private String createCandidateResumeName(Candidate candidate) {
        var builder = new StringBuilder();
        builder.append(candidate.getId()).append("_")
                .append(candidate.getLastName())
                .append(candidate.getFirstName());
        if (StringUtils.isNotEmpty(candidate.getMiddleName())) {
            builder.append(candidate.getMiddleName());
        }
        return builder.toString();
    }

    @Transactional(readOnly = true)
    public byte[] findResume(long id) {
        var candidate = candidateService.findEntityById(id);
        return fileService.readFile(candidate.getResumePath());
    }
}
