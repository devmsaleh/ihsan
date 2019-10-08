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
import com.ihsan.entities.Gender;
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

		String query = "select s.CASE_ID as id,s.CASENAME as name,s.BIRTHDATE as birthDate,s.CASE_NO as caseNumber,s.GENDER as genderId,c.CASEAMOUNT as caseAmount,c.SPONS_FOR as sponsorFor,c.FIRST_TITLE as firstTitleId from SP_CONTRACTS_CASES c inner join SP_CASES s on c.CASE_ID = s.CASE_ID where c.CASE_CONTRACT_STATUS=2 and c.BENEFICENT_ID=:sponsorId";
		List<Object[]> result = entityManager.createNativeQuery(query).setParameter("sponsorId", sponsorId)
				.getResultList();
		List<OrphanDTO> resultList = new ArrayList<OrphanDTO>(result.size());
		OrphanDTO orphanDTO = null;
		Gender gender = null;
		FirstTitle firstTitle = null;
		for (Iterator<Object[]> iterator = result.iterator(); iterator.hasNext();) {
			Object[] dataArray = (Object[]) iterator.next();
			orphanDTO = new OrphanDTO(((BigDecimal) dataArray[0]).toString(), (String) dataArray[1],
					(Date) dataArray[2], (String) dataArray[3], (String) dataArray[4], (BigDecimal) dataArray[5],
					(String) dataArray[6], (String) dataArray[7]);
			orphanDTO.setBirthDateStr(GeneralUtils.formateDate(orphanDTO.getBirthDate()));
			gender = utilsService.getGenderFromCache(orphanDTO.getGenderId());
			orphanDTO.setGender(gender.getName());
			firstTitle = utilsService.getFirstTitleFromCache(orphanDTO.getFirstTitleId());
			orphanDTO.setFirstTitleName(firstTitle.getName());
			resultList.add(orphanDTO);
		}

		return resultList;
	}

}
