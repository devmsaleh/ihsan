package com.ihs.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ihs.entities.Receipt;

public interface ReceiptRepository extends JpaRepository<Receipt, BigInteger> {

	Receipt findByIdAndCreatedById(BigInteger id, BigInteger delegateId);

	Receipt findTop1ByCreatedByIdOrderByIdDesc(String delegateId);

	@Modifying(clearAutomatically = true)
	@Query(value = "update Receipt set numberOfPrints=numberOfPrints+1 where id=:id")
	@Transactional
	int updateNumberOfPrints(@Param("id") BigInteger id);

	@Query(value = "select sum(TOTAL_AMOUNT) from TM_RECEIPTS where TM_DELEGATES_CODE=:delegateId and STATUS=0", nativeQuery = true)
	BigDecimal getDelegateTotalAmount(@Param("delegateId") String delegateId);

	List<Receipt> findByCollectedAndCreatedById(String collected, String delegateId);
}
