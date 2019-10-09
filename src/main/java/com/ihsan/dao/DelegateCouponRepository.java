package com.ihsan.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.ihsan.entities.DelegateCoupon;

public interface DelegateCouponRepository extends JpaRepository<DelegateCoupon, BigInteger> {

	List<DelegateCoupon> findByDelegateId(BigInteger delegateId);

	@Modifying(clearAutomatically = true)
	@Transactional
	void deleteByDelegateIdAndCouponId(BigInteger delegateId, BigInteger couponId);
}
