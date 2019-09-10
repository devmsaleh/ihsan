package com.ihsan.dao.charityBoxes;

import java.math.BigInteger;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ihsan.entities.charityBoxes.SubLocation;
import com.ihsan.util.Constants;

public interface SubLocationRepository extends JpaRepository<SubLocation, BigInteger> {

	List<SubLocation> findByNameAndLocationId(String name, BigInteger locationId);

	@Cacheable(cacheNames = Constants.CACHE_NAME_CHARITY_BOX_SUB_LOCATION)
	List<SubLocation> findAll();

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_CHARITY_BOX_SUB_LOCATION, allEntries = true)
	void clearCharityBoxSubLocationCache();

	List<SubLocation> findTop10ByLocationIdAndNameIgnoreCaseContainingOrderByNameAsc(BigInteger locationId,
			String name);

	List<SubLocation> findTop10ByNameIgnoreCaseContainingOrderByNameAsc(String name);

	List<SubLocation> findTop500ByLocationIdOrderByNameAsc(BigInteger locationId);

	@Query(value = "select * from TM_SUB_LOCATIONS sub where sub.LOCATION_ID=:locationId and sub.SUB_LOCATION_ID not in (select SUB_LOCATION_ID from TM_CHARITY_BOXES where CATEGORY_OF_BOXES=:categoryId and STATUS!='1') and sub.SUB_LOCATION_NAME like %:name% fetch next 10 rows only", nativeQuery = true)
	List<SubLocation> findTop10ByLocationIdAndCategoryIdAndNameForInsert(@Param("locationId") BigInteger locationId,
			@Param("categoryId") String categoryId, @Param("name") String name);

	@Query(value = "select * from TM_SUB_LOCATIONS sub where sub.LOCATION_ID=:locationId and sub.SUB_LOCATION_ID not in (select SUB_LOCATION_ID from TM_CHARITY_BOXES where CATEGORY_OF_BOXES=:categoryId and STATUS!='1') fetch next 100 rows only", nativeQuery = true)
	List<SubLocation> findTop500ByLocationIdAndCategoryIdForInsert(@Param("locationId") BigInteger locationId,
			@Param("categoryId") String categoryId);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "update SubLocation set locationLatitude=:locationLatitude,locationLongitude=:locationLongitude where id=:id")
	int updateSubLocation(@Param("locationLatitude") Float locationLatitude,
			@Param("locationLongitude") Float locationLongitude, @Param("id") BigInteger id);

}
