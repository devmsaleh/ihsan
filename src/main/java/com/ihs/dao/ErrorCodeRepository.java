package com.ihs.dao;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ihs.entities.ErrorCode;
import com.ihs.util.Constants;

public interface ErrorCodeRepository extends CrudRepository<ErrorCode, Long> {

    @Cacheable(cacheNames = Constants.CACHE_NAME_ERROR_CODE)
    ErrorCode findById(Long id);

    @Query(value = "select count(*) from dual", nativeQuery = true)
    @CacheEvict(cacheNames = Constants.CACHE_NAME_ERROR_CODE, allEntries = true)
    void clearErrorCodesCache();

}
