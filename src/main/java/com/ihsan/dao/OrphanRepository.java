package com.ihsan.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ihsan.entities.Orphan;

public interface OrphanRepository extends JpaRepository<Orphan, BigInteger> {

	@Query(value = "select * from SP_CASES orphan left outer join FND_LOOKUP_VALUES country on orphan.NATIONALITYID = country.LOOKUP_CODE and lower(country.LOOKUP_TYPE)=lower('FND_NATIONALITY')"
			+ " left outer join FND_LOOKUP_VALUES sponsorshipType on orphan.SPONCER_CATEGORY=sponsorshipType.LOOKUP_CODE and lower(sponsorshipType.LOOKUP_TYPE)=lower('SP_SPONSOR_CATEGORY')"
			+ "where orphan.CASE_STATUS=7 and country.LOOKUP_CODE=:countryId and sponsorshipType.LOOKUP_CODE=:sponsorshipTypeId and orphan.CHECK_FLAG=0 offset :startFromRowNumber rows fetch next :pageSize rows only", nativeQuery = true)
	List<Orphan> findOrphans(@Param("countryId") String countryId, @Param("sponsorshipTypeId") String sponsorshipTypeId,
			@Param("startFromRowNumber") int startFromRowNumber, @Param("pageSize") int pageSize);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "update Orphan set reserved=true where id=:id")
	int flag(@Param("id") BigInteger id);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "update Orphan set reserved=false where id=:id")
	int unFlag(@Param("id") BigInteger id);

	@Query(value = "select reserved from Orphan where id=:id")
	boolean isFlagged(@Param("id") BigInteger id);

}
