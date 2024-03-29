package com.ihsan.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ihsan.entities.BankTransfer;
import com.ihsan.util.Constants;

public interface BankTransferRepository extends JpaRepository<BankTransfer, BigInteger> {

    @Cacheable(cacheNames = Constants.CACHE_NAME_BANKS_TRANSFER)
    List<BankTransfer> findAll();

    @Query(value = "select count(*) from dual", nativeQuery = true)
    @CacheEvict(cacheNames = Constants.CACHE_NAME_BANKS_TRANSFER, allEntries = true)
    void clearBankTransferCache();
}
