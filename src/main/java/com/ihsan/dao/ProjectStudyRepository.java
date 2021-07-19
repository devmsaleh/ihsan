package com.ihsan.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ihsan.entities.ProjectStudy;

public interface ProjectStudyRepository extends JpaRepository<ProjectStudy, BigInteger> {

	List<ProjectStudy> findTop10ByCountryIdAndProjectTypeIdAndNameIgnoreCaseContainingOrderByNameAsc(String countryId,
			BigInteger projectTypeId, String name);

	List<ProjectStudy> findTop100ByCountryIdAndProjectTypeIdOrderByNameAsc(String countryId, BigInteger projectTypeId);

}
