package com.daimon.recruiting.candidate.repository;

import com.daimon.recruiting.candidate.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
}
