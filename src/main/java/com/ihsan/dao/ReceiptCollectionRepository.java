package com.ihsan.dao;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ihsan.entities.ReceiptCollection;

public interface ReceiptCollectionRepository extends JpaRepository<ReceiptCollection, BigInteger> {

	@Query("SELECT max(id) FROM ReceiptCollection")
	Long getMaxId();

	@Query(value = "select max(value) from FND_COUNTERS where code='CPE'", nativeQuery = true)
	Long getMaxCollectionNumber();

	@Modifying(clearAutomatically = true)
	@Query(value = "delete from ReceiptCollection where id=:id")
	@Transactional
	int deleteReceiptCollection(@Param("id") BigInteger id);

	@Transactional
	@Procedure(name = "collectProc")
	String collectReceiptsFromDelegate(@Param("P_ID") BigInteger collectionId,
			@Param("P_USER") BigInteger supervisorId);

}
