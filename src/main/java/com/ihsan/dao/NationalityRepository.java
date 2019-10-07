package com.ihsan.dao;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ihsan.entities.Nationality;
import com.ihsan.util.Constants;

public interface NationalityRepository extends JpaRepository<Nationality, String> {

	@Query(value = "select * from FND_LOOKUP_VALUES where lower(LOOKUP_TYPE) = lower('FND_NATIONALITY') and LOOKUP_CODE in (select distinct NATIONALITYID from SP_CASES where CASE_STATUS =7 and CHECK_FLAG=0 and SPONCER_CATEGORY = :sponsorshipTypeId)", nativeQuery = true)
	@Cacheable(cacheNames = Constants.CACHE_NAME_COUNTRY_SPONSORSHIP)
	List<Nationality> findSponsorshipCountries(@Param("sponsorshipTypeId") String sponsorshipTypeId);

	@Query(value = "select LOOKUP_IMAGE from FND_LOOKUP_VALUES where lower(LOOKUP_TYPE) = lower('FND_NATIONALITY') and LOOKUP_CODE=:id", nativeQuery = true)
	@Cacheable(cacheNames = Constants.CACHE_NAME_COUNTRY_SPONSORSHIP_IMAGE)
	byte[] getImageById(@Param("id") String id);

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_COUNTRY_SPONSORSHIP, allEntries = true)
	void clearSponsorshipTypeCountriesCache();

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_COUNTRY_SPONSORSHIP_IMAGE, allEntries = true)
	void clearCountrySponsorshipImageCache();

	@Cacheable(cacheNames = Constants.CACHE_NAME_ALL_NATIONALITIES)
	List<Nationality> findAll();

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_ALL_NATIONALITIES, allEntries = true)
	void clearAllNationalitiesCache();
}
