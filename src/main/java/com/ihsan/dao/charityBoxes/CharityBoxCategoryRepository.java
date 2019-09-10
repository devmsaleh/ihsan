package com.ihsan.dao.charityBoxes;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ihsan.entities.charityBoxes.CharityBoxCategory;
import com.ihsan.util.Constants;

public interface CharityBoxCategoryRepository extends JpaRepository<CharityBoxCategory, String> {

	@Query(value = "select * from FND_LOOKUP_VALUES where lower(LOOKUP_TYPE)=lower('CATEGORY_OF_BOXES')", nativeQuery = true)
	@Cacheable(cacheNames = Constants.CACHE_NAME_CHARITY_BOX_CATEGORY)
	List<CharityBoxCategory> getCharityBoxCategoryList();

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_CHARITY_BOX_CATEGORY, allEntries = true)
	void clearCharityBoxCategoryCache();

}
