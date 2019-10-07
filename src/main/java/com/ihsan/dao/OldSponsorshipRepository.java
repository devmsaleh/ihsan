package com.ihsan.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ihsan.entities.OldSponsorship;

public interface OldSponsorshipRepository extends JpaRepository<OldSponsorship, BigInteger> {

	@Query(value = "select * from SP_CONTRACTS_CASES where CASE_CONTRACT_STATUS=2 and BENEFICENT_ID=:sponsorId", nativeQuery = true)
	List<OldSponsorship> getOldSponsorships(@Param("sponsorId") BigInteger sponsorId);

}
