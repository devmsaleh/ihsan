package com.ihsan.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ihsan.entities.Delegate;

public interface DelegateRepository extends JpaRepository<Delegate, String> {

	@Query("from Delegate where lower(userName) = lower(:userName) and password =:password")
	Delegate authenticate(@Param("userName") String userName, @Param("password") String password);

}
