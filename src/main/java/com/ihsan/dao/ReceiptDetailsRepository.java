package com.ihsan.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ihsan.entities.ReceiptDetail;

public interface ReceiptDetailsRepository extends JpaRepository<ReceiptDetail, BigInteger> {

	List<ReceiptDetail> findByCreatedByIdAndCreationDateAfterAndCreationDateBeforeOrderByIdAsc(BigInteger delegateId,
			Date fromDate, Date toDate);

	List<ReceiptDetail> findByCreatedByIdAndCreationDateGreaterThanEqualAndCreationDateLessThanEqualOrderByIdAsc(
			BigInteger delegateId, Date fromDate, Date toDate);

	List<ReceiptDetail> findByCreatedByIdAndReceiptCollectedAndCreationDateGreaterThanEqualAndCreationDateLessThanEqualOrderByIdAsc(
			BigInteger delegateId, String collected, Date fromDate, Date toDate);

	@Query(value = "SELECT MAX(RECEIPT_DETAIL_CODE) FROM TM_RECEIPT_DETAILS", nativeQuery = true)
	Long getMaxReceiptDetailId();

	@Query(value = "SELECT LAST_NUMBER FROM dba_sequences where sequence_name='TM_RECEIPT_DETAILS_SEQ'", nativeQuery = true)
	Long getReceiptDetailsSequenceCurrentValue();

	@Query(value = "select tm_receipt_details_seq.nextval from dual", nativeQuery = true)
	Long getReceiptDetailsSequenceNextValue();
}
