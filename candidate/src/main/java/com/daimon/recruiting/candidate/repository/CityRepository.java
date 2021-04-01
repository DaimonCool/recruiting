package com.daimon.recruiting.candidate.repository;

import com.daimon.recruiting.candidate.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
}
