package com.ihsan.webservice;

import java.math.BigDecimal;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.ihsan.constants.ErrorCodeEnum;
import com.ihsan.entities.CouponType;
import com.ihsan.entities.PaymentTypeEnum;
import com.ihsan.util.GeneralUtils;
import com.ihsan.webservice.dto.NewCouponDTO;
import com.ihsan.webservice.dto.NewProjectDTO;
import com.ihsan.webservice.dto.NewSponsorshipDTO;
import com.ihsan.webservice.dto.OldProjectDonationDTO;
import com.ihsan.webservice.dto.OldSponsorshipDTO;
import com.ihsan.webservice.dto.OrphanSponsorshipDTO;
import com.ihsan.webservice.dto.ReceiptDTO;
import com.ihsan.webservice.dto.ReceiptPaymentDTO;

@Component
public class ServiceValidation extends HAIServiceBase {

	public ErrorCodeEnum validateCreateReceipt(ReceiptDTO receiptDTO) {

		ErrorCodeEnum errorCode = validateReceiptPayment(receiptDTO);
		if (isError(errorCode)) {
			return errorCode;
		}

		if (CollectionUtils.isNotEmpty(receiptDTO.getCouponsList())) {
			for (NewCouponDTO couponDTO : receiptDTO.getCouponsList()) {
				errorCode = validateNewCoupon(couponDTO, receiptDTO.getDonatorName());
				if (isError(errorCode)) {
					return errorCode;
				}
			}
		}

		return ErrorCodeEnum.SUCCESS_CODE;

	}

	public ErrorCodeEnum validateReceiptPayment(ReceiptDTO receiptDTO) {
		if (CollectionUtils.isEmpty(receiptDTO.getCouponsList())) {
			return ErrorCodeEnum.RECEIPT_DETAILS_REQUIRED;
		}

		if (GeneralUtils.isEmptyNumber(receiptDTO.getDelegateId())) {
			return ErrorCodeEnum.DELEGATE_ID_REQUIRED;
		}

		if (receiptDTO.getPaymentType() == PaymentTypeEnum.CREDIT
				&& StringUtils.isBlank(receiptDTO.getReceiptPaymentDTO().getCreditCardTransactionNumber())) {
			return ErrorCodeEnum.CREDIT_CARD_TRANSACTION_NUMBER_REQUIRED;
		}

		if (receiptDTO.getPaymentType() == PaymentTypeEnum.BANK_TRANSFER) {
			if (StringUtils.isBlank(receiptDTO.getReceiptPaymentDTO().getBankCode())
					|| StringUtils.isBlank(receiptDTO.getReceiptPaymentDTO().getAccountNumber()))
				return ErrorCodeEnum.BANK_CODE_AND_ACCOUNT_NUMBER_REQUIRED;
		}

		if (receiptDTO.getPaymentType() == PaymentTypeEnum.CHEQUE) {
			ReceiptPaymentDTO payment = receiptDTO.getReceiptPaymentDTO();
			if (StringUtils.isBlank(payment.getBankCode()) || StringUtils.isBlank(payment.getChequeNumber())
					|| StringUtils.isBlank(payment.getChequeDate()))
				return ErrorCodeEnum.ALL_CHEQUE_DETAILS_REQUIRED;
		}

		if (!EnumUtils.isValidEnum(PaymentTypeEnum.class, receiptDTO.getPaymentType().getValue())) {
			return ErrorCodeEnum.INVALID_PAYMENT_TYPE;
		}

		if (!delegateRepository.exists(receiptDTO.getDelegateId())) {
			return ErrorCodeEnum.DELEGATE_NOT_EXIST;
		}

		return ErrorCodeEnum.SUCCESS_CODE;
	}

	public ErrorCodeEnum validateNewCoupon(NewCouponDTO newCouponDTO, String donatorName) {

		if (GeneralUtils.isEmptyNumber(newCouponDTO.getAmount())) {
			return ErrorCodeEnum.COUPON_AMOUNT_REQUIRED;
		}

		if (GeneralUtils.isEmptyNumber(newCouponDTO.getCouponTypeId())) {
			return ErrorCodeEnum.COUPON_TYPE_ID_REQUIRED;
		}

		CouponType couponType = couponRepository.findOne(newCouponDTO.getCouponTypeId());
		if (couponType == null) {
			return ErrorCodeEnum.COUPON_TYPE_NOT_EXIST;
		}

		// if (couponType.isMustEnterDonator() && StringUtils.isBlank(donatorName)) {
		// return ErrorCodeEnum.DONATOR_NAME_REQUIRED;
		// }

		BigDecimal minimumAmount = new BigDecimal(couponType.getMinimumAmount());
		if (newCouponDTO.getAmount().compareTo(minimumAmount) < 0) {
			return ErrorCodeEnum.COUPON_AMOUNT_LESS_MINIMUM;
		}

		return ErrorCodeEnum.SUCCESS_CODE;
	}

	public ErrorCodeEnum validateNewProject(NewProjectDTO newProjectDTO) {

		if (GeneralUtils.isEmptyNumber(newProjectDTO.getNewProjectTypeId())
				|| GeneralUtils.isEmptyNumber(newProjectDTO.getProjectCountryId())
				|| GeneralUtils.isEmptyNumber(newProjectDTO.getProjectStudyId())
				|| StringUtils.isBlank(newProjectDTO.getProjectName())
				|| GeneralUtils.isEmptyNumber(newProjectDTO.getAmount())) {
			return ErrorCodeEnum.MISSING_NEW_PROJECT_INFO;
		}

		if (!newProjectTypeRepository.exists(newProjectDTO.getNewProjectTypeId())) {
			return ErrorCodeEnum.PROJECT_TYPE_NOT_EXIST;
		}

		return ErrorCodeEnum.SUCCESS_CODE;
	}

	public ErrorCodeEnum validateOldProject(OldProjectDonationDTO currentProjectDTO) {

		if (GeneralUtils.isEmptyNumber(currentProjectDTO.getOldProjectId())
				|| GeneralUtils.isEmptyNumber(currentProjectDTO.getAmount())
				|| GeneralUtils.isEmptyNumber(currentProjectDTO.getDonatorId())) {
			return ErrorCodeEnum.MISSING_DONATE_CURRENT_PROJECT_INFO;
		}

		return ErrorCodeEnum.SUCCESS_CODE;
	}

	public ErrorCodeEnum validateNewSponsorship(NewSponsorshipDTO newSponsorshipDTO) {

		if (CollectionUtils.isEmpty(newSponsorshipDTO.getOrphansList())) {
			return ErrorCodeEnum.MISSING_NEW_SPONSORSHIP_INFO;
		}

		for (OrphanSponsorshipDTO orphanSponsorshipDTO : newSponsorshipDTO.getOrphansList()) {

			if (GeneralUtils.isEmptyNumber(orphanSponsorshipDTO.getOrphanId())) {
				return ErrorCodeEnum.ORPHAN_ID_REQUIRED;
			}

			if (GeneralUtils.isEmptyNumber(orphanSponsorshipDTO.getAmount())) {
				return ErrorCodeEnum.ORPHAN_AMOUNT_REQUIRED;
			}

			if (StringUtils.isNotBlank(newSponsorshipDTO.getFirstTitleId())) {

			}

			if (StringUtils.isNotBlank(orphanSponsorshipDTO.getGiftId())) {

				if (GeneralUtils.isEmptyNumber(orphanSponsorshipDTO.getGiftAmount())) {
					return ErrorCodeEnum.GIFT_AMOUNT_REQUIRED;
				}
			} else {
				if (!GeneralUtils.isEmptyNumber(orphanSponsorshipDTO.getGiftAmount())) {
					return ErrorCodeEnum.GIFT_TYPE_ID_REQUIRED;
				}
			}
		}

		return ErrorCodeEnum.SUCCESS_CODE;
	}

	public ErrorCodeEnum validateOldSponsorship(OldSponsorshipDTO oldSponsorshipDTO) {

		if (GeneralUtils.isEmptyNumber(oldSponsorshipDTO.getSponsorId())
				|| CollectionUtils.isEmpty(oldSponsorshipDTO.getOrphansList())) {
			return ErrorCodeEnum.MISSING_OLD_SPONSORSHIP_INFO;
		}

		for (OrphanSponsorshipDTO orphanSponsorshipDTO : oldSponsorshipDTO.getOrphansList()) {

			if (GeneralUtils.isEmptyNumber(orphanSponsorshipDTO.getOrphanId())) {
				return ErrorCodeEnum.ORPHAN_ID_REQUIRED;
			}

			if (GeneralUtils.isEmptyNumber(orphanSponsorshipDTO.getAmount())) {
				return ErrorCodeEnum.ORPHAN_AMOUNT_REQUIRED;
			}
			if (StringUtils.isNotBlank(orphanSponsorshipDTO.getGiftId())) {

				if (GeneralUtils.isEmptyNumber(orphanSponsorshipDTO.getGiftAmount())) {
					return ErrorCodeEnum.GIFT_AMOUNT_REQUIRED;
				}
			} else {
				if (!GeneralUtils.isEmptyNumber(orphanSponsorshipDTO.getGiftAmount())) {
					return ErrorCodeEnum.GIFT_TYPE_ID_REQUIRED;
				}
			}
		}

		return ErrorCodeEnum.SUCCESS_CODE;
	}

}
