package com.ihsan.dao;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ihsan.entities.ErrorCode;
import com.ihsan.util.Constants;

public interface ErrorCodeRepository extends CrudRepository<ErrorCode, Long> {

	@Cacheable(cacheNames = Constants.CACHE_NAME_ERROR_CODE)
	ErrorCode findByErrorCode(String errorCode);

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_ERROR_CODE, allEntries = true)
	void clearErrorCodesCache();

}
