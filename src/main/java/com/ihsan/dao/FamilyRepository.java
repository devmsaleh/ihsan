package com.ihsan.dao;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ihsan.entities.Family;

public interface FamilyRepository extends JpaRepository<Family, BigInteger> {

}
