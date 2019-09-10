package com.ihsan.dao.charityBoxes;

import java.math.BigInteger;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ihsan.entities.charityBoxes.CharityBoxType;
import com.ihsan.util.Constants;

public interface CharityBoxTypeRepository extends JpaRepository<CharityBoxType, BigInteger> {

	@Cacheable(cacheNames = Constants.CACHE_NAME_CHARITY_BOX_TYPE)
	List<CharityBoxType> findAll();

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_CHARITY_BOX_TYPE, allEntries = true)
	void clearCharityBoxTypeCache();

}
