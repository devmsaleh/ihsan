package com.ihsan.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ihsan.entities.Orphan;

public interface OrphanRepository extends JpaRepository<Orphan, BigInteger> {

	@Query(value = "select * from POS_SP_CASES_V orphan WHERE SP_CATEGORY_ID=:countryId"
			+ " and SPONCER_CATEGORY=:sponsorshipTypeId and CASE_STATUS in (3,7) and (NVL(CHECK_FLAG,0)=0 or RESERVED_BY=:delegateId) offset :startFromRowNumber rows fetch next :pageSize rows only", nativeQuery = true)
	List<Orphan> findOrphans(@Param("delegateId") BigInteger delegateId, @Param("countryId") BigInteger countryId,
			@Param("sponsorshipTypeId") String sponsorshipTypeId, @Param("startFromRowNumber") int startFromRowNumber,
			@Param("pageSize") int pageSize);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "update SP_CASES set CHECK_FLAG=1,RESERVED_BY=:delegateId,RESERVED_DATE=:reservationDate where CASE_ID=:id", nativeQuery = true)
	int flag(@Param("id") BigInteger id, @Param("delegateId") BigInteger delegateId,
			@Param("reservationDate") Date reservationDate);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "update SP_CASES set CHECK_FLAG=0,RESERVED_BY=null,RESERVED_DATE=null where CASE_ID=:id", nativeQuery = true)
	int unFlag(@Param("id") BigInteger id);

	@Query(value = "select CHECK_FLAG from POS_SP_CASES_V where CASE_ID=:id", nativeQuery = true)
	BigDecimal isFlagged(@Param("id") BigInteger id);

}
