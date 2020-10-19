package com.ihsan.dao.charityBoxes;

import java.math.BigInteger;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ihsan.entities.charityBoxes.SafetyCase;

public interface SafetyCaseRepository extends JpaRepository<SafetyCase, BigInteger> {

	SafetyCase findByBarcode(String barcode);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "update SafetyCase set status=:status,lastUpdateDate=:lastUpdateDate where id=:id")
	int updateSafetyCaseStatus(@Param("id") BigInteger id, @Param("status") String status,
			@Param("lastUpdateDate") Date lastUpdateDate);
}
