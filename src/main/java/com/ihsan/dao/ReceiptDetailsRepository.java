package com.ihsan.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ihsan.entities.ReceiptDetail;

public interface ReceiptDetailsRepository extends JpaRepository<ReceiptDetail, BigInteger> {

	List<ReceiptDetail> findByCreatedByIdAndCreationDateAfterAndCreationDateBeforeOrderByIdAsc(BigInteger delegateId,
			Date fromDate, Date toDate);

	List<ReceiptDetail> findByCreatedByIdAndCreationDateGreaterThanEqualAndCreationDateLessThanEqualOrderByIdAsc(
			String delegateId, Date fromDate, Date toDate);
}
