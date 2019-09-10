package com.ihsan.dao.charityBoxes;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ihsan.entities.charityBoxes.CharityBoxTransfer;

public interface CharityBoxTransferRepository extends JpaRepository<CharityBoxTransfer, BigInteger> {

}
