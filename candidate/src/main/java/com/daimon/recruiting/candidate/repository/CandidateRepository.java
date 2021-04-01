package com.daimon.recruiting.candidate.repository;

import com.daimon.recruiting.candidate.entity.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Page<Candidate> findAll(Specification<Candidate> specification, Pageable pageable);
}
