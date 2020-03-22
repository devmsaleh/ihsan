package com.ihsan.dao;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ihsan.entities.NewProjectCountry;
import com.ihsan.util.Constants;

public interface NewProjectCountryRepository extends JpaRepository<NewProjectCountry, String> {

	@Query(value = "select * from POS_CP_PROJECTS_COUNTRIES_V where COUNTRY_ID in (select distinct COUNTRY_ID from POS_CP_PROJECT_CATEGORIES_V where PROJECT_TYPE=:newProjectTypeId)", nativeQuery = true)
	@Cacheable(cacheNames = Constants.CACHE_NAME_PROJECT_TYPE_COUNTRIES)
	List<NewProjectCountry> getProjectCountries(@Param("newProjectTypeId") long newProjectTypeId);

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_PROJECT_TYPE_COUNTRIES, allEntries = true)
	void clearNewProjectCountriesCache();

	@Query(value = "select COUNTRY_IMAGE from POS_CP_PROJECTS_COUNTRIES_V where COUNTRY_ID=:id", nativeQuery = true)
	@Cacheable(cacheNames = Constants.CACHE_NAME_COUNTRY_IMAGE)
	byte[] getImageById(@Param("id") String id);

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_COUNTRY_IMAGE, allEntries = true)
	void clearCountryImageCache();

	List<NewProjectCountry> findTop10ByNameIgnoreCaseContainingOrderByNameAsc(String name);

	@Cacheable(cacheNames = Constants.CACHE_NAME_ALL_COUNTRIES)
	List<NewProjectCountry> findAll();

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_ALL_COUNTRIES, allEntries = true)
	void clearAllCountriesCache();

}
