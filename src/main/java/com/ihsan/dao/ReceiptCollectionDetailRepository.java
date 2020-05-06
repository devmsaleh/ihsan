package com.ihsan.dao;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ihsan.entities.ReceiptCollectionDetail;

public interface ReceiptCollectionDetailRepository extends JpaRepository<ReceiptCollectionDetail, BigInteger> {

	@Query("SELECT max(id) FROM ReceiptCollectionDetail")
	Long getMaxId();

	@Modifying(clearAutomatically = true)
	@Query(value = "delete from ReceiptCollectionDetail where receiptCollection.id=:receiptCollectionId")
	@Transactional
	int deleteReceiptCollectionDetails(@Param("receiptCollectionId") BigInteger receiptCollectionId);

}
