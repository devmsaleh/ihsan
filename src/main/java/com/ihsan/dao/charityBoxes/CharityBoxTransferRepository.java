package com.ihsan.dao.charityBoxes;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ihsan.entities.charityBoxes.CharityBoxTransfer;

public interface CharityBoxTransferRepository extends JpaRepository<CharityBoxTransfer, BigInteger> {

	@Query(value = "SELECT MAX(TRANSFER_NUMBER) FROM TM_CHARITY_BOX_TRANSFERS", nativeQuery = true)
	Long getMaxTransferNumber();

}
