package com.ihs.dao;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ihs.entities.BankCheque;
import com.ihs.util.Constants;

public interface BankChequeRepository extends JpaRepository<BankCheque, String> {

    @Query(value = "select * from FND_LOOKUP_VALUES where lower(LOOKUP_TYPE)=lower('BANKS')", nativeQuery = true)
    @Cacheable(cacheNames = Constants.CACHE_NAME_BANKS_CHEQUE)
    List<BankCheque> getBanks();

    @Query(value = "select count(*) from dual", nativeQuery = true)
    @CacheEvict(cacheNames = Constants.CACHE_NAME_BANKS_CHEQUE, allEntries = true)
    void clearBankChequeCache();

}
