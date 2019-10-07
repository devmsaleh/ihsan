package com.ihsan.dao;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ihsan.entities.Gender;
import com.ihsan.util.Constants;

public interface GenderRepository extends JpaRepository<Gender, String> {

	@Query(value = "select * from FND_LOOKUP_VALUES where lower(LOOKUP_TYPE)=lower('PARTICULARGENDER')", nativeQuery = true)
	@Cacheable(cacheNames = Constants.CACHE_NAME_ALL_GENDERS)
	List<Gender> findAll();

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_ALL_GENDERS, allEntries = true)
	void clearAllGendersCache();

}
