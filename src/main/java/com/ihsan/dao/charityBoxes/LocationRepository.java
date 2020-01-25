package com.ihsan.dao.charityBoxes;

import java.math.BigInteger;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ihsan.entities.charityBoxes.Location;
import com.ihsan.util.Constants;

public interface LocationRepository extends JpaRepository<Location, BigInteger> {

	@Cacheable(cacheNames = Constants.CACHE_NAME_CHARITY_BOX_LOCATION)
	List<Location> findAll();

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_CHARITY_BOX_LOCATION, allEntries = true)
	void clearCharityBoxLocationCache();

	List<Location> findTop10ByRegionIdAndNameIgnoreCaseContainingOrderByNameAsc(BigInteger regionId, String name);

	List<Location> findTop500ByRegionIdOrderByNameAsc(BigInteger regionId);

	List<Location> findByNameAndRegionId(String name, BigInteger regionId);

	@Query(value = "SELECT MAX(TO_NUMBER(LOCATION_NUMBER)) FROM TM_LOCATIONS", nativeQuery = true)
	Long getMaxLocationNumber();
}
