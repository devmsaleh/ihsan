package com.ihsan.dao;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ihsan.entities.Country;
import com.ihsan.util.Constants;

public interface CountryRepository extends JpaRepository<Country, String> {

	@Query(value = "select * from FND_LOOKUP_VALUES where LOOKUP_TYPE = 'FND_COUNTRY' and LOOKUP_CODE in (select distinct COUNTRY_ID from SCPROJECT_TYPES_INFO where project_category =1 and PROJECT_TYPE=:newProjectTypeId)", nativeQuery = true)
	@Cacheable(cacheNames = Constants.CACHE_NAME_PROJECT_TYPE_COUNTRIES)
	List<Country> getProjectCountries(@Param("newProjectTypeId") long newProjectTypeId);

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_PROJECT_TYPE_COUNTRIES, allEntries = true)
	void clearNewProjectCountriesCache();

	@Query(value = "select LOOKUP_IMAGE from FND_LOOKUP_VALUES where lower(LOOKUP_TYPE)=lower('FND_COUNTRY') and LOOKUP_CODE=:id", nativeQuery = true)
	@Cacheable(cacheNames = Constants.CACHE_NAME_COUNTRY_IMAGE)
	byte[] getImageById(@Param("id") String id);

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_COUNTRY_IMAGE, allEntries = true)
	void clearCountryImageCache();

	List<Country> findTop10ByNameIgnoreCaseContainingOrderByNameAsc(String name);

	@Cacheable(cacheNames = Constants.CACHE_NAME_ALL_COUNTRIES)
	List<Country> findAll();

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_ALL_COUNTRIES, allEntries = true)
	void clearAllCountriesCache();

}
