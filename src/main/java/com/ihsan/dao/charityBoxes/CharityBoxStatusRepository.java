package com.ihsan.dao.charityBoxes;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ihsan.entities.charityBoxes.CharityBoxStatus;
import com.ihsan.util.Constants;

public interface CharityBoxStatusRepository extends JpaRepository<CharityBoxStatus, String> {

	@Query(value = "select * from FND_LOOKUP_VALUES where lower(LOOKUP_TYPE)=lower('BOXES_STATUS')", nativeQuery = true)
	@Cacheable(cacheNames = Constants.CACHE_NAME_CHARITY_BOX_STATUS)
	List<CharityBoxStatus> getCharityBoxStatusList();

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_CHARITY_BOX_STATUS, allEntries = true)
	void clearCharityBoxStatusCache();

}
