package com.ihsan.dao.charityBoxes;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ihsan.entities.charityBoxes.CharityBoxTransferDetail;

public interface CharityBoxTransferDetailRepository extends JpaRepository<CharityBoxTransferDetail, BigInteger> {

	CharityBoxTransferDetail findTop1BySubLocationIdOrderByIdDesc(BigInteger subLocationId);

}
