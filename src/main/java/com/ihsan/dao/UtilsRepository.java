package com.ihsan.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ihsan.entities.FirstTitle;
import com.ihsan.service.UtilsService;
import com.ihsan.util.GeneralUtils;
import com.ihsan.webservice.dto.OrphanDTO;

@Repository
@Transactional(rollbackFor = Exception.class)
public class UtilsRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private UtilsService utilsService;

	@SuppressWarnings("unchecked")
	public List<OrphanDTO> getOldSponsorships(BigInteger sponsorId) {

		String query = "select * from POS_SP_CONTRACTS_CASES_V where BENEFICENT_ID=:sponsorId";
		List<Object[]> result = entityManager.createNativeQuery(query).setParameter("sponsorId", sponsorId)
				.getResultList();
		List<OrphanDTO> resultList = new ArrayList<OrphanDTO>(result.size());
		OrphanDTO orphanDTO = null;
		FirstTitle firstTitle = null;
		String genderId = null;
		String firstTitleId = null;
		String id = null;
		String name = null;
		Date birthDate = null;
		String caseNumber = null;
		BigDecimal caseAmount = BigDecimal.ZERO;
		String sponsorFor = null;
		String gender = null;

		for (Iterator<Object[]> iterator = result.iterator(); iterator.hasNext();) {
			id = null;
			name = null;
			birthDate = null;
			caseNumber = null;
			caseAmount = BigDecimal.ZERO;
			sponsorFor = null;
			genderId = null;
			firstTitleId = null;
			gender = null;
			Object[] dataArray = (Object[]) iterator.next();
			if (dataArray[0] != null) {
				id = ((BigDecimal) dataArray[0]).toString();
			}
			if (dataArray[1] != null) {
				name = (String) dataArray[1];
			}
			if (dataArray[2] != null) {
				birthDate = (Date) dataArray[2];
			}
			if (dataArray[3] != null) {
				caseNumber = (String) dataArray[3];
			}
			if (dataArray[4] != null) {
				genderId = ((BigDecimal) dataArray[4]).toString();
			}

			if (dataArray[5] != null) {
				caseAmount = (BigDecimal) dataArray[5];
			}

			if (dataArray[6] != null) {
				sponsorFor = (String) dataArray[6];
			}

			if (dataArray[7] != null) {
				firstTitleId = ((BigDecimal) dataArray[7]).toString();
			}

			if (dataArray[8] != null) {
				gender = (String) dataArray[8];
			}
			orphanDTO = new OrphanDTO(id, name, birthDate, caseNumber, genderId, caseAmount, sponsorFor, firstTitleId);
			orphanDTO.setBirthDateStr(GeneralUtils.formatDate(orphanDTO.getBirthDate()));
			orphanDTO.setGender(gender);
			firstTitle = utilsService.getFirstTitleFromCache(orphanDTO.getFirstTitleId());
			orphanDTO.setFirstTitleName(firstTitle.getName());
			resultList.add(orphanDTO);
		}

		return resultList;
	}

}
