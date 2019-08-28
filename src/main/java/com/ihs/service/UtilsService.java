package com.ihs.service;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ihs.dao.CouponTypeRepository;
import com.ihs.dao.DelegateRepository;
import com.ihs.dao.NewProjectTypeRepository;
import com.ihs.dao.ReceiptRepository;
import com.ihs.entities.CouponType;
import com.ihs.entities.NewProjectType;
import com.ihs.entities.Receipt;

@Service
@Transactional(rollbackFor = Exception.class)
public class UtilsService {

	private static final Logger log = LoggerFactory.getLogger(UtilsService.class);

	@Autowired
	private DelegateRepository delegateRepository;

	@Autowired
	private ReceiptRepository receiptRepository;

	@Autowired
	private NewProjectTypeRepository newProjectTypeRepository;

	@Autowired
	protected CouponTypeRepository couponRepository;

	public Receipt createReceipt(Receipt receipt) {
		receiptRepository.save(receipt);
		receipt.setNumber(String.valueOf(receipt.getId()));
		return receiptRepository.save(receipt);
	}

	public CouponType getCouponByBarcode(String barcode) {
		if (StringUtils.isBlank(barcode))
			return new CouponType();
		List<CouponType> list = couponRepository.getCoupons();
		for (CouponType couponTypeLoop : list) {
			if (couponTypeLoop.getId().toString().equals(barcode)) {
				return couponTypeLoop;
			}
		}
		CouponType couponType = couponRepository.findOne(new BigInteger(barcode));
		if (couponType == null)
			couponType = new CouponType();
		return couponType;
	}

	public NewProjectType getNewProjectTypeFromCache(BigInteger projectTypeId) {
		if (projectTypeId == null)
			return new NewProjectType();
		List<NewProjectType> list = newProjectTypeRepository.getNewProjectTypes();
		for (NewProjectType projectTypeLoop : list) {
			if (projectTypeLoop.getId().compareTo(projectTypeId) == 0) {
				return projectTypeLoop;
			}
		}
		NewProjectType newProjectType = newProjectTypeRepository.findOne(projectTypeId);
		if (newProjectType == null)
			newProjectType = new NewProjectType();
		return newProjectType;
	}

}
