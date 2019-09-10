package com.ihsan.dao.charityBoxes;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ihsan.entities.charityBoxes.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, BigInteger> {

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "update Attachment set charityBoxTransferId=:charityBoxTransferId,name=:name where id=:attachmentId")
	int updateAttachmentTransferId(@Param("charityBoxTransferId") BigInteger charityBoxTransferId,
			@Param("name") String name, @Param("attachmentId") BigInteger attachmentId);

}
