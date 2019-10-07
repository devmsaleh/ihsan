package com.ihsan.dao;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ihsan.entities.GiftType;
import com.ihsan.util.Constants;

public interface GiftTypeRepository extends JpaRepository<GiftType, String> {

	@Query(value = "select * from FND_LOOKUP_VALUES where lower(LOOKUP_TYPE)=lower('SP_GIFT_DESC')", nativeQuery = true)
	@Cacheable(cacheNames = Constants.CACHE_NAME_GIFT_TYPE)
	List<GiftType> getGiftTypes();

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_GIFT_TYPE, allEntries = true)
	void clearGiftTypeCache();

}
