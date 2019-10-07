package com.ihsan.dao;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ihsan.entities.FirstTitle;
import com.ihsan.util.Constants;

public interface FirstTitleRepository extends JpaRepository<FirstTitle, String> {

	@Query(value = "select * from FND_LOOKUP_VALUES where lower(LOOKUP_TYPE)=lower('SP_FIRST_TITLE')", nativeQuery = true)
	@Cacheable(cacheNames = Constants.CACHE_NAME_FIRST_TITLE)
	List<FirstTitle> getFirstTitles();

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_FIRST_TITLE, allEntries = true)
	void clearFirstTitleCache();

}
