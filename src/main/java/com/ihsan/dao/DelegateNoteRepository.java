package com.ihsan.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ihsan.entities.DelegateNote;

public interface DelegateNoteRepository extends JpaRepository<DelegateNote, BigInteger> {

	@Modifying(clearAutomatically = true)
	@Query(value = "update DelegateNote set active=false where id=:id")
	@Transactional
	int removeNote(@Param("id") BigInteger id);

	List<DelegateNote> findByDelegateIdAndActiveTrueOrderByIdDesc(BigInteger delegateId);
}
