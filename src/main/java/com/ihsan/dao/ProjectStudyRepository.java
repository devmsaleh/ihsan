package com.ihsan.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ihsan.entities.ProjectStudy;

public interface ProjectStudyRepository extends JpaRepository<ProjectStudy, BigInteger> {
	@Query(value = "select * from SCPROJECT_TYPES_INFO projectStudy inner join FND_LOOKUP_VALUES country on projectStudy.COUNTRY_ID=country.LOOKUP_CODE and country.LOOKUP_TYPE='FND_NATIONALITY'"
			+ " where status=1 and country.LOOKUP_CODE=:countryId and projectStudy.PROJECT_TYPE=:projectTypeId and projectStudy.PROJECT_CATEGORY=:projectCategoryId and (lower(projectStudy.PROJECT_NAME) like lower('%'|| :name || '%') ) order by projectStudy.PROJECT_NAME fetch first 10 rows only", nativeQuery = true)
	List<ProjectStudy> findTop10ByCountryIdAndProjectTypeIdAndProjectCategoryIdAndNameIgnoreCaseContainingOrderByNameAsc(
			@Param("countryId") String countryId, @Param("projectTypeId") BigInteger projectTypeId,
			@Param("projectCategoryId") BigInteger projectCategoryId, @Param("name") String name);

	@Query(value = "select * from SCPROJECT_TYPES_INFO projectStudy inner join FND_LOOKUP_VALUES country on projectStudy.COUNTRY_ID=country.LOOKUP_CODE and country.LOOKUP_TYPE='FND_NATIONALITY'"
			+ " where status=1 and country.LOOKUP_CODE=:countryId and projectStudy.PROJECT_TYPE=:projectTypeId and projectStudy.PROJECT_CATEGORY=:projectCategoryId order by projectStudy.PROJECT_NAME fetch first 10 rows only", nativeQuery = true)
	List<ProjectStudy> findTop10ByCountryIdAndProjectTypeIdAndProjectCategoryIdOrderByNameAsc(
			@Param("countryId") String countryId, @Param("projectTypeId") BigInteger projectTypeId,
			@Param("projectCategoryId") BigInteger projectCategoryId);
}
