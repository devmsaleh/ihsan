package com.ihsan.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ihsan.entities.Donator;

public interface DonatorRepository extends JpaRepository<Donator, BigInteger> {

	List<Donator> findTop50ByNameIgnoreCaseContainingOrderByNameAsc(String name);

	List<Donator> findTop50ByEmailIgnoreCaseContainingOrderByNameAsc(String email);

	List<Donator> findTop50ByNameNotNullOrderByNameAsc();

	@Query(value = "from Donator where mobile like :searchText%")
	List<Donator> findByMobile(@Param("searchText") String searchText, Pageable page);

	@Query(value = "from Donator where mobile like :searchText% or mailBox like :searchText% or accountNumber like :searchText%")
	List<Donator> findByMobileOrMailBoxOrAccountNumber(@Param("searchText") String searchText, Pageable page);

}
