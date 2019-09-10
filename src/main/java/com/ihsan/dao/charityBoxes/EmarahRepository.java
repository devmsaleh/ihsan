package com.ihsan.dao.charityBoxes;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ihsan.entities.charityBoxes.Emarah;
import com.ihsan.util.Constants;

public interface EmarahRepository extends JpaRepository<Emarah, String> {

	@Query(value = "select * from FND_LOOKUP_VALUES where lower(LOOKUP_TYPE)=lower('CITIES') order by ARABIC_VALUE", nativeQuery = true)
	@Cacheable(cacheNames = Constants.CACHE_NAME_EMARAH)
	List<Emarah> getEmarahList();

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_EMARAH, allEntries = true)
	void clearEmarahCache();

}
