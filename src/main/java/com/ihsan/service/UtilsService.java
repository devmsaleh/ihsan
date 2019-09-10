package com.ihsan.service;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ihsan.dao.CouponTypeRepository;
import com.ihsan.dao.DelegateRepository;
import com.ihsan.dao.NewProjectTypeRepository;
import com.ihsan.dao.ReceiptRepository;
import com.ihsan.entities.CouponType;
import com.ihsan.entities.NewProjectType;
import com.ihsan.entities.Receipt;

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
