package com.ihsan.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ihsan.entities.DelegateCoupon;

public interface DelegateCouponRepository extends JpaRepository<DelegateCoupon, BigInteger> {

	List<DelegateCoupon> findByDelegateId(BigInteger delegateId);

}
