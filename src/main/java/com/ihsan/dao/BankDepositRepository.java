package com.ihsan.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ihsan.entities.BankDeposit;
import com.ihsan.util.Constants;

public interface BankDepositRepository extends JpaRepository<BankDeposit, BigInteger> {

	@Cacheable(cacheNames = Constants.CACHE_NAME_BANKS_DEPOSIT)
	List<BankDeposit> findAll();

	@Query(value = "select count(*) from dual", nativeQuery = true)
	@CacheEvict(cacheNames = Constants.CACHE_NAME_BANKS_DEPOSIT, allEntries = true)
	void clearBankDepositCache();
}
