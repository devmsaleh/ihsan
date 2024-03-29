package com.ihsan.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ihsan.entities.CouponType;
import com.ihsan.util.Constants;

public interface CouponTypeRepository extends JpaRepository<CouponType, BigInteger> {

	@Query("from CouponType where statusCode=0 and expiryDate > CURRENT_DATE order by priority")
	// @Cacheable(cacheNames = Constants.CACHE_NAME_COUPONS_TYPES)
	List<CouponType> getCoupons();

	CouponType findByQrCode(String qrCode);

	@Query(value = "select PROJECT_IMAGE_NEW from TM_PROJECTS where PROJECT_CODE=:id", nativeQuery = true)
	// @Cacheable(cacheNames = Constants.CACHE_NAME_COUPON_IMAGE)
	byte[] getImageById(@Param("id") String id);

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_COUPONS_TYPES, allEntries = true)
	void clearCouponsTypesCache();

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_COUPON_IMAGE, allEntries = true)
	void clearCouponImageCache();

	List<CouponType> findByNameStartingWithAndStatusCode(String name, int statusCode);

}
