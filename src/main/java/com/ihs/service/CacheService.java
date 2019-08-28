package com.ihs.service;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihs.dao.BankChequeRepository;
import com.ihs.dao.BankTransferRepository;
import com.ihs.dao.CouponTypeRepository;
import com.ihs.dao.ErrorCodeRepository;
import com.ihs.dao.NewProjectTypeRepository;
import com.ihs.entities.BankCheque;
import com.ihs.entities.BankTransfer;
import com.ihs.entities.CouponType;
import com.ihs.entities.NewProjectType;

@Service
public class CacheService {

	private static final Logger logger = LoggerFactory.getLogger(CacheService.class);

	@Autowired
	private NewProjectTypeRepository newProjectTypeRepository;

	@Autowired
	private CouponTypeRepository couponTypeRepository;

	@Autowired
	private BankChequeRepository bankChequeRepository;

	@Autowired
	private BankTransferRepository bankTransferRepository;

	@Autowired
	private ErrorCodeRepository errorCodeRepository;

	public void refreshAllCaches() {
		DateTime startDate = DateTime.now();
		logger.info("######### refreshAllCaches started at: " + startDate);

		newProjectTypeRepository.clearNewProjectTypesCache();
		List<NewProjectType> newProjectTypesList = newProjectTypeRepository.getNewProjectTypes();
		logger.info("######### newProjectTypesList: " + newProjectTypesList.size());
		couponTypeRepository.clearCouponsTypesCache();
		List<CouponType> couponTypeList = couponTypeRepository.getCoupons();
		logger.info("######### couponTypeList: " + couponTypeList.size());

		bankChequeRepository.clearBankChequeCache();
		List<BankCheque> bankChequeList = bankChequeRepository.getBanks();
		logger.info("######### bankChequeList: " + bankChequeList.size());
		bankTransferRepository.clearBankTransferCache();
		List<BankTransfer> bankTransferList = bankTransferRepository.findAll();
		logger.info("######### bankTransferList: " + bankTransferList.size());

		DateTime endDate = DateTime.now();
		int seconds = Seconds.secondsBetween(startDate, endDate).getSeconds();
		logger.info("######### refreshAllCaches ended at: " + endDate + ", it took " + seconds + " seconds");

		errorCodeRepository.clearErrorCodesCache();

		couponTypeRepository.clearCouponImageCache();
		newProjectTypeRepository.clearNewProjectTypeImageCache();

	}

}
