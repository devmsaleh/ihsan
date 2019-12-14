package com.ihsan.dao.charityBoxes;

import java.math.BigInteger;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ihsan.entities.charityBoxes.Region;
import com.ihsan.util.Constants;

public interface RegionRepository extends JpaRepository<Region, BigInteger> {

	@Cacheable(cacheNames = Constants.CACHE_NAME_CHARITY_BOX_REGION)
	List<Region> findAll();

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_CHARITY_BOX_REGION, allEntries = true)
	void clearCharityBoxRegionCache();

	List<Region> findTop10ByEmarahIdAndNameIgnoreCaseContainingOrderByNameAsc(BigInteger emarahId, String name);

	List<Region> findTop100ByEmarahIdOrderByNameAsc(BigInteger emarahId);

	@Query(value = "from Region where emarahId=:emarahId and name like %:name%")
	List<Region> findByNameAndEmarahId(@Param("emarahId") BigInteger emarahId, @Param("name") String name,
			Pageable page);

	List<Region> findByNameAndEmarahId(String name, BigInteger emarahId);

}
