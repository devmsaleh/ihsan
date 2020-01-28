package com.ihsan.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ihsan.entities.Delegate;

public interface DelegateRepository extends JpaRepository<Delegate, BigInteger> {

	@Query("from Delegate where lower(userName) = lower(:userName) and password =:password")
	Delegate authenticate(@Param("userName") String userName, @Param("password") String password);

	@Query(value = "select distinct CITY_ID from TM_DELEGATE_PRIVILEGES where DELEGATE_ID=:delegateId", nativeQuery = true)
	List<BigDecimal> getAutorizedEmaratesList(@Param("delegateId") BigInteger delegateId);

	@Query(value = "select PROJECTNAME from POSPERMISSIONS where DELEGATEID:delegateId and STATUS=0", nativeQuery = true)
	List<Integer> getDelegatePermissions(@Param("delegateId") String delegateId);
}
