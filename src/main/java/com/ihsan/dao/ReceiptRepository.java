package com.ihsan.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ihsan.entities.Receipt;

public interface ReceiptRepository extends JpaRepository<Receipt, BigInteger> {

	Receipt findByIdAndCreatedById(BigInteger id, BigInteger delegateId);

	List<Receipt> findByNumberAndCreatedById(String receiptNumber, BigInteger delegateId);

	Receipt findTop1ByCreatedByIdOrderByIdDesc(BigInteger delegateId);

	@Modifying(clearAutomatically = true)
	@Query(value = "update Receipt set numberOfPrints=numberOfPrints+1 where id=:id")
	@Transactional
	int updateNumberOfPrints(@Param("id") BigInteger id);

	@Query(value = "select sum(TOTAL_AMOUNT) from TM_RECEIPTS where TM_DELEGATES_CODE=:delegateId and STATUS=0", nativeQuery = true)
	BigDecimal getDelegateTotalAmount(@Param("delegateId") BigInteger delegateId);

	List<Receipt> findByCollectedAndCreatedByIdOrderByIdDesc(boolean collected, BigInteger delegateId);

	@Query(value = "SELECT MAX(TO_NUMBER(receipt_number)) FROM tm_receipts", nativeQuery = true)
	Long getMaxReceiptNumber();
}
