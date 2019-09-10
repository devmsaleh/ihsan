package com.ihsan.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ihsan.service.UtilsService;

@Repository
@Transactional(rollbackFor = Exception.class)
public class UtilsRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private UtilsService utilsService;

}
