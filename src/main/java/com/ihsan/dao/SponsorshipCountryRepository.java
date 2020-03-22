package com.ihsan.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ihsan.entities.SponsorshipCountry;
import com.ihsan.util.Constants;

public interface SponsorshipCountryRepository extends JpaRepository<SponsorshipCountry, String> {

	@Query(value = "select * from POS_SP_SPONSOR_COUNTRIES_V"
			+ " where ID in (select  COUNTRYID from POS_SP_CASES_V where  SPONCER_CATEGORY = :sponsorshipTypeId)", nativeQuery = true)
	@Cacheable(cacheNames = Constants.CACHE_NAME_COUNTRY_SPONSORSHIP)
	List<SponsorshipCountry> findSponsorshipCountries(@Param("sponsorshipTypeId") String sponsorshipTypeId);

	@Query(value = "select IMAGE from POS_SP_SPONSOR_COUNTRIES_V where ID=:id", nativeQuery = true)
	@Cacheable(cacheNames = Constants.CACHE_NAME_COUNTRY_SPONSORSHIP_IMAGE)
	byte[] getImageById(@Param("id") BigInteger id);

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_COUNTRY_SPONSORSHIP, allEntries = true)
	void clearSponsorshipTypeCountriesCache();

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_COUNTRY_SPONSORSHIP_IMAGE, allEntries = true)
	void clearCountrySponsorshipImageCache();

	@Cacheable(cacheNames = Constants.CACHE_NAME_ALL_NATIONALITIES)
	List<SponsorshipCountry> findAll();

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_ALL_NATIONALITIES, allEntries = true)
	void clearAllNationalitiesCache();
}
