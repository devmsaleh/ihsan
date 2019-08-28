package com.ihs.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ihs.entities.NewProjectType;
import com.ihs.util.Constants;

public interface NewProjectTypeRepository extends JpaRepository<NewProjectType, BigInteger> {

    @Query(value = "select * from PROJECT_TYPES where PROJ_CAT=1", nativeQuery = true)
    @Cacheable(cacheNames = Constants.CACHE_NAME_NEW_PROJECT_TYPE)
    List<NewProjectType> getNewProjectTypes();

    @Query(value = "select PROJECT_IMAGE from PROJECT_TYPES where TYPE_ID=:id", nativeQuery = true)
    @Cacheable(cacheNames = Constants.CACHE_NAME_NEW_PROJECT_TYPE_IMAGE)
    byte[] getImageById(@Param("id") BigInteger id);

    @Query(value = "select count(*) from dual", nativeQuery = true)
    @CacheEvict(cacheNames = Constants.CACHE_NAME_NEW_PROJECT_TYPE, allEntries = true)
    void clearNewProjectTypesCache();

    @Query(value = "select count(*) from dual", nativeQuery = true)
    @CacheEvict(cacheNames = Constants.CACHE_NAME_NEW_PROJECT_TYPE_IMAGE, allEntries = true)
    void clearNewProjectTypeImageCache();

}
