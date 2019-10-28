package com.ihsan.dao.charityBoxes;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ihsan.entities.charityBoxes.CharityBox;

public interface CharityBoxRepository extends JpaRepository<CharityBox, BigInteger> {

	CharityBox findByBarcode(String barcode);

	CharityBox findByNumberIgnoreCase(String number);

	int countByCategoryIdAndSubLocationIdAndStatusIdNot(String categoryId, BigInteger subLocationId, String statusId);

	List<CharityBox> findBySubLocationIdAndStatusId(BigInteger subLocationId, String statusId);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "update CharityBox set subLocationId=:subLocationId,statusId=:statusId where id=:id")
	int updateCharityBox(@Param("subLocationId") BigInteger subLocationId, @Param("statusId") String statusId,
			@Param("id") BigInteger id);
}
