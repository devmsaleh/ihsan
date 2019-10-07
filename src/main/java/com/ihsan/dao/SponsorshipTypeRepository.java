package com.ihsan.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ihsan.entities.SponsorshipType;
import com.ihsan.util.Constants;

public interface SponsorshipTypeRepository extends JpaRepository<SponsorshipType, String> {

	@Query(value = "select * from FND_LOOKUP_VALUES where lower(LOOKUP_TYPE)=lower('SP_SPONSOR_CATEGORY') and LOOKUP_CODE in (select distinct SPONCER_CATEGORY from SP_CASES)", nativeQuery = true)
	@Cacheable(cacheNames = Constants.CACHE_NAME_SPONSORSHIP_TYPE)
	List<SponsorshipType> getSponsorshipTypes();

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_SPONSORSHIP_TYPE, allEntries = true)
	void clearSponsorshipTypesCache();

	@Query(value = "select AMOUNT from SP_PAYMENT_SETTING_DTL where SPONCER_CATEGORY=:sponsorshipTypeId and NATIONAL_CODE=:countryId order by EFFECTIVE_DATE desc FETCH NEXT 1 ROWS ONLY", nativeQuery = true)
	Integer findSponsorshipTypeAmount(@Param("sponsorshipTypeId") BigInteger sponsorshipTypeId,
			@Param("countryId") BigInteger countryId);
}
