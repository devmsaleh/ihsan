package com.ihsan.dao.charityBoxes;

import java.math.BigInteger;
import java.util.Date;
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

	List<SubLocation> findTop10ByLocationRegionIdAndNameIgnoreCaseContainingOrderByNameAsc(BigInteger regionId,
			String name);

	List<SubLocation> findTop10ByLocationRegionEmarahIdAndNameIgnoreCaseContainingOrderByNameAsc(BigInteger emarahId,
			String name);

	List<SubLocation> findTop10ByNameIgnoreCaseContainingOrderByNameAsc(String name);

	List<SubLocation> findTop500ByOrderByNameAsc();

	List<SubLocation> findTop500ByLocationIdOrderByNameAsc(BigInteger locationId);

	@Query(value = "select * from TM_SUB_LOCATIONS sub where sub.LOCATION_ID=:locationId and sub.SUB_LOCATION_ID not in (select SUB_LOCATION_ID from TM_CHARITY_BOXES where CATEGORY_OF_BOXES=:categoryId and STATUS!='1') and sub.SUB_LOCATION_NAME like %:name% fetch next 10 rows only", nativeQuery = true)
	List<SubLocation> findTop10ByLocationIdAndCategoryIdAndNameForInsertSingle(
			@Param("locationId") BigInteger locationId, @Param("categoryId") String categoryId,
			@Param("name") String name);

	@Query(value = "select * from TM_SUB_LOCATIONS sub inner join TM_LOCATIONS loc on sub.LOCATION_ID=loc.LOCATION_ID where loc.REGION_ID=:regionId and sub.SUB_LOCATION_ID not in (select SUB_LOCATION_ID from TM_CHARITY_BOXES where CATEGORY_OF_BOXES=:categoryId and STATUS!='1') and sub.SUB_LOCATION_NAME like %:name% fetch next 10 rows only", nativeQuery = true)
	List<SubLocation> findTop10ByLocationRegionIdAndCategoryIdAndNameForInsertSingle(
			@Param("regionId") BigInteger regionId, @Param("categoryId") String categoryId, @Param("name") String name);

	@Query(value = "select * from TM_SUB_LOCATIONS sub inner join TM_LOCATIONS loc on sub.LOCATION_ID=loc.LOCATION_ID inner join TM_REGION reg on loc.REGION_ID=reg.TM_REGION_ID where reg.TM_CITY_ID=:emarahId and sub.SUB_LOCATION_ID not in (select SUB_LOCATION_ID from TM_CHARITY_BOXES where CATEGORY_OF_BOXES=:categoryId and STATUS!='1') and sub.SUB_LOCATION_NAME like %:name% fetch next 10 rows only", nativeQuery = true)
	List<SubLocation> findTop10ByLocationRegionEmarahIdAndCategoryIdAndNameForInsertSingle(
			@Param("emarahId") BigInteger emarahId, @Param("categoryId") String categoryId, @Param("name") String name);

	@Query(value = "select * from TM_SUB_LOCATIONS sub where sub.LOCATION_ID=:locationId and sub.SUB_LOCATION_NAME like %:name% order by SUB_LOCATION_NAME fetch next 10 rows only", nativeQuery = true)
	List<SubLocation> findTop10ByLocationIdAndCategoryIdAndNameForInsertMultiple(
			@Param("locationId") BigInteger locationId, @Param("name") String name);

	@Query(value = "select * from TM_SUB_LOCATIONS sub inner join TM_LOCATIONS loc on sub.LOCATION_ID=loc.LOCATION_ID where loc.REGION_ID=:regionId and sub.SUB_LOCATION_NAME like %:name% order by SUB_LOCATION_NAME fetch next 10 rows only", nativeQuery = true)
	List<SubLocation> findTop10ByLocationRegionIdAndCategoryIdAndNameForInsertMultiple(
			@Param("regionId") BigInteger regionId, @Param("name") String name);

	@Query(value = "select * from TM_SUB_LOCATIONS sub inner join TM_LOCATIONS loc on sub.LOCATION_ID=loc.LOCATION_ID inner join TM_REGION reg on loc.REGION_ID=reg.TM_REGION_ID where reg.TM_CITY_ID=:emarahId and sub.SUB_LOCATION_NAME like %:name% order by SUB_LOCATION_NAME fetch next 10 rows only", nativeQuery = true)
	List<SubLocation> findTop10ByLocationRegionEmarahIdAndCategoryIdAndNameForInsertMultiple(
			@Param("emarahId") BigInteger emarahId, @Param("name") String name);

	@Query(value = "select * from TM_SUB_LOCATIONS sub where sub.LOCATION_ID=:locationId and sub.SUB_LOCATION_ID not in (select SUB_LOCATION_ID from TM_CHARITY_BOXES where CATEGORY_OF_BOXES=:categoryId and STATUS!='1') fetch next 500 rows only", nativeQuery = true)
	List<SubLocation> findTop500ByLocationIdAndCategoryIdForInsertSingle(@Param("locationId") BigInteger locationId,
			@Param("categoryId") String categoryId);

	@Query(value = "select * from TM_SUB_LOCATIONS sub inner join TM_LOCATIONS loc on sub.LOCATION_ID=loc.LOCATION_ID where loc.REGION_ID=:regionId and sub.SUB_LOCATION_ID not in (select SUB_LOCATION_ID from TM_CHARITY_BOXES where CATEGORY_OF_BOXES=:categoryId and STATUS!='1') fetch next 500 rows only", nativeQuery = true)
	List<SubLocation> findTop500ByLocationRegionIdAndCategoryIdForInsertSingle(@Param("regionId") BigInteger regionId,
			@Param("categoryId") String categoryId);

	@Query(value = "select * from TM_SUB_LOCATIONS sub inner join TM_LOCATIONS loc on sub.LOCATION_ID=loc.LOCATION_ID inner join TM_REGION reg on loc.REGION_ID=reg.TM_REGION_ID where reg.TM_CITY_ID=:emarahId and sub.SUB_LOCATION_ID not in (select SUB_LOCATION_ID from TM_CHARITY_BOXES where CATEGORY_OF_BOXES=:categoryId and STATUS!='1') fetch next 500 rows only", nativeQuery = true)
	List<SubLocation> findTop500ByLocationRegionEmarahIdAndCategoryIdForInsertSingle(
			@Param("emarahId") BigInteger emarahId, @Param("categoryId") String categoryId);

	@Query(value = "select * from TM_SUB_LOCATIONS sub where sub.LOCATION_ID=:locationId order by SUB_LOCATION_NAME fetch next 500 rows only", nativeQuery = true)
	List<SubLocation> findTop500ByLocationIdAndCategoryIdForInsertMultiple(@Param("locationId") BigInteger locationId);

	@Query(value = "select * from TM_SUB_LOCATIONS sub inner join TM_LOCATIONS loc on sub.LOCATION_ID=loc.LOCATION_ID where loc.REGION_ID=:regionId order by SUB_LOCATION_NAME fetch next 500 rows only", nativeQuery = true)
	List<SubLocation> findTop500ByLocationRegionIdAndCategoryIdForInsertMultiple(
			@Param("regionId") BigInteger regionId);

	@Query(value = "select * from TM_SUB_LOCATIONS sub inner join TM_LOCATIONS loc on sub.LOCATION_ID=loc.LOCATION_ID inner join TM_REGION reg on loc.REGION_ID=reg.TM_REGION_ID where reg.TM_CITY_ID=:emarahId order by SUB_LOCATION_NAME fetch next 500 rows only", nativeQuery = true)
	List<SubLocation> findTop500ByLocationRegionEmarahIdAndCategoryIdForInsertMultiple(
			@Param("emarahId") BigInteger emarahId);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "update SubLocation set locationLatitude=:locationLatitude,locationLongitude=:locationLongitude where id=:id")
	int updateSubLocation(@Param("locationLatitude") Float locationLatitude,
			@Param("locationLongitude") Float locationLongitude, @Param("id") BigInteger id);

	@Modifying(clearAutomatically = true)
	@Query(value = "update SubLocation set subLocationTemporaryClosed=:subLocationTemporaryClosed,lastUpdateBy=:lastUpdateBy,lastUpdateDate=:lastUpdateDate where id=:id")
	@Transactional
	int updateTemporaryClosed(@Param("id") BigInteger id,
			@Param("subLocationTemporaryClosed") boolean subLocationTemporaryClosed,
			@Param("lastUpdateBy") BigInteger lastUpdateBy, @Param("lastUpdateDate") Date lastUpdateDate);

	@Modifying(clearAutomatically = true)
	@Query(value = "update SubLocation set rating=:rating,lastRatingBy=:lastRatingBy,lastRatingDate=:lastRatingDate where id=:id")
	@Transactional
	int updateRating(@Param("id") BigInteger id, @Param("rating") int rating,
			@Param("lastRatingBy") BigInteger lastRatingBy, @Param("lastRatingDate") Date lastRatingDate);
}
