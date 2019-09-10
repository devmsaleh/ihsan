package com.ihsan.dao.charityBoxes;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ihsan.entities.charityBoxes.CharityBoxActionType;
import com.ihsan.util.Constants;

public interface CharityBoxActionTypeRepository extends JpaRepository<CharityBoxActionType, String> {

	@Query(value = "select * from FND_LOOKUP_VALUES where lower(LOOKUP_TYPE)=lower('CHARITY_BOX_ACTION_TYPE')", nativeQuery = true)
	@Cacheable(cacheNames = Constants.CACHE_NAME_CHARITY_BOX_ACTION_TYPE)
	List<CharityBoxActionType> getCharityBoxActionTypeList();

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_CHARITY_BOX_ACTION_TYPE, allEntries = true)
	void clearCharityBoxActionTypeCache();

}
