package com.ihs.webservice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ihs.constants.ErrorCodeEnum;
import com.ihs.dao.BankChequeRepository;
import com.ihs.dao.BankTransferRepository;
import com.ihs.dao.CouponTypeRepository;
import com.ihs.dao.DelegateRepository;
import com.ihs.dao.ErrorCodeRepository;
import com.ihs.dao.NewProjectTypeRepository;
import com.ihs.dao.ReceiptDetailsRepository;
import com.ihs.dao.ReceiptRepository;
import com.ihs.dao.TokenRepository;
import com.ihs.dao.UtilsRepository;
import com.ihs.entities.BankCheque;
import com.ihs.entities.BankTransfer;
import com.ihs.entities.CouponType;
import com.ihs.entities.Delegate;
import com.ihs.entities.NewProjectType;
import com.ihs.entities.PaymentTypeEnum;
import com.ihs.entities.Receipt;
import com.ihs.entities.ReceiptDetail;
import com.ihs.entities.ReceiptPayment;
import com.ihs.service.DelegateService;
import com.ihs.service.UtilsService;
import com.ihs.util.GeneralUtils;
import com.ihs.webservice.dto.BankDTO;
import com.ihs.webservice.dto.CouponTypeDTO;
import com.ihs.webservice.dto.DelegateDTO;
import com.ihs.webservice.dto.NewCouponDTO;
import com.ihs.webservice.dto.NewProjectTypeDTO;
import com.ihs.webservice.dto.ReceiptDTO;
import com.ihs.webservice.dto.ReceiptDetailDTO;
import com.ihs.webservice.dto.ReceiptPaymentDTO;
import com.ihs.webservice.dto.ReceiptPrintDTO;
import com.ihs.webservice.dto.ReceiptsReportDTO;
import com.ihs.webservice.dto.ServiceResponse;
import com.ihs.webservice.dto.SupervisorReportDTO;
import com.ihs.webservice.dto.TransactionTypeEnum;

@Service
public class HAIServiceBase {

	private static final Logger logger = LoggerFactory.getLogger(HAIServiceBase.class);

	@Autowired
	protected DelegateService delegateService;

	@Autowired
	protected DelegateRepository delegateRepository;

	@Autowired
	protected ErrorCodeRepository errorCodeRepository;

	@Autowired
	protected UtilsService utilsService;

	@Autowired
	protected CouponTypeRepository couponRepository;

	@Autowired
	protected NewProjectTypeRepository newProjectTypeRepository;

	@Autowired
	protected TokenRepository tokenRepository;

	@Autowired
	protected ReceiptDetailsRepository receiptDetailsRepository;

	@Autowired
	protected ReceiptRepository receiptRepository;

	@Autowired
	protected BankChequeRepository bankChequeRepository;

	@Autowired
	protected BankTransferRepository bankTransferRepository;

	@Autowired
	protected UtilsRepository utilsRepository;

	protected boolean isSuccess(ErrorCodeEnum errorCode) {
		return errorCode != null && errorCode.intValue() == ErrorCodeEnum.SUCCESS_CODE.intValue();
	}

	protected boolean isError(ErrorCodeEnum errorCode) {
		return errorCode == null || errorCode.intValue() != ErrorCodeEnum.SUCCESS_CODE.intValue();
	}

	protected boolean isSuccess(int errorCode) {
		return errorCode == ErrorCodeEnum.SUCCESS_CODE.intValue();
	}

	protected boolean isError(int errorCode) {
		return errorCode != ErrorCodeEnum.SUCCESS_CODE.intValue();
	}

	protected boolean isResponseHasError(ServiceResponse response) {
		return response == null || response.getErrorCode().intValue() != ErrorCodeEnum.SUCCESS_CODE.intValue();
	}

	public Receipt convertReceipDTOToReceipt(ReceiptDTO receiptDTO) {
		Receipt receipt = new Receipt();
		receipt.setCreatedBy(new Delegate(receiptDTO.getDelegateId()));
		receipt.setDelegate(new Delegate(receiptDTO.getDelegateId()));
		receipt.setDonatorPhoneNumber(receiptDTO.getDonatorPhoneNumber());
		receipt.setDonatorName(receiptDTO.getDonatorName());
		receipt.setPaymentType(receiptDTO.getPaymentType().getValue());

		BigDecimal totalAmountForReceipt = new BigDecimal(0);
		ReceiptDetail receiptDetails = null;
		if (CollectionUtils.isNotEmpty(receiptDTO.getCouponsList())) {
			for (NewCouponDTO couponDTO : receiptDTO.getCouponsList()) {
				receiptDetails = new ReceiptDetail();
				receiptDetails.setNotes(couponDTO.getNotes());
				receiptDetails.setReceipt(receipt);
				receiptDetails.setAmount(couponDTO.getAmount());
				receiptDetails.setCreatedBy(receipt.getCreatedBy());
				receiptDetails.setCoupon(new CouponType(couponDTO.getCouponTypeId()));

				receiptDetails.setName(couponDTO.getCouponTypeName());

				receipt.getReceiptDetailsList().add(receiptDetails);
				totalAmountForReceipt = totalAmountForReceipt.add(couponDTO.getAmount());
			}
		}

		receipt.setTotalAmount(totalAmountForReceipt);
		receipt.setTotalPaid(totalAmountForReceipt);
		ReceiptPaymentDTO paymentDTO = receiptDTO.getReceiptPaymentDTO();
		ReceiptPayment receiptPayment = new ReceiptPayment();
		receiptPayment.setReceipt(receipt);
		receiptPayment.setCashValue(receipt.getTotalAmount());
		receiptPayment.setPaymentType(receiptDTO.getPaymentType().getValue());
		if (receiptDTO.getPaymentType() == PaymentTypeEnum.CHEQUE) {
			// receiptPayment.setBankCode(paymentDTO.getBankCode());
			receiptPayment.setChequeDate(GeneralUtils.parseDate(paymentDTO.getChequeDate()));
			receiptPayment.setChequeNumber(paymentDTO.getChequeNumber());
		} else if (receiptDTO.getPaymentType() == PaymentTypeEnum.CREDIT) {
			receiptPayment.setCreditCardTransactionNumber(paymentDTO.getCreditCardTransactionNumber());
		} else if (receiptDTO.getPaymentType() == PaymentTypeEnum.BANK_TRANSFER) {
			// receiptPayment.setDeductionNumber(paymentDTO.getDeductionNumber());
			// receiptPayment.setBankCode(paymentDTO.getBankCode());
			// receiptPayment.setAccountName(paymentDTO.getAccountName());
			// receiptPayment.setAccountNumber(paymentDTO.getAccountNumber());
		}
		receiptPayment.setCreatedBy(receipt.getCreatedBy());
		receipt.setReceiptPayment(receiptPayment);
		return receipt;
	}

	public DelegateDTO convertDelegateToDTO(Delegate delegate) {
		DelegateDTO delegateDTO = new DelegateDTO();
		delegateDTO.setId(delegate.getId().toString());
		delegateDTO.setAdmin(delegate.isAdmin());
		delegateDTO.setExpiryDate(GeneralUtils.formateDate(delegate.getExpiryDate()));
		delegateDTO.setMaxLimit(new BigDecimal(delegate.getMaxLimit()));
		delegateDTO.setName(delegate.getName());
		delegateDTO.setNumber(delegate.getNumber());
		delegateDTO.setStartDate(GeneralUtils.formateDate(delegate.getStartDate()));
		delegateDTO.setToken(delegate.getToken());
		delegateDTO.setUserName(delegate.getUserName());
		delegateDTO.setPinCode(delegate.getPinCode());
		delegateDTO.setSupervisor(delegate.isSupervisor());
		delegateDTO.setMobile(delegate.getMobile());
		return delegateDTO;
	}

	public List<DelegateDTO> convertDelegateListToDTO(List<Delegate> delegateList) {
		List<DelegateDTO> result = new ArrayList<DelegateDTO>();
		for (Delegate delegate : delegateList)
			result.add(convertDelegateToDTO(delegate));
		return result;
	}

	public List<CouponTypeDTO> convertCouponListToDTO(List<CouponType> couponsList) {
		List<CouponTypeDTO> list = new ArrayList<>(couponsList.size());
		for (CouponType coupon : couponsList) {
			CouponTypeDTO couponDTO = convertCouponToDTO(coupon);
			list.add(couponDTO);
		}
		return list;
	}

	public CouponTypeDTO convertCouponToDTO(CouponType coupon) {
		CouponTypeDTO couponDTO = new CouponTypeDTO();
		couponDTO.setId(coupon.getId().toString());
		couponDTO.setMinimumAmount(coupon.getMinimumAmount());
		couponDTO.setMustEnterDonator(coupon.getMustEnterDonator() == 1);
		couponDTO.setName(coupon.getName());
		couponDTO.setPriority(coupon.getPriority());
		couponDTO.setQrCode(coupon.getQrCode());
		couponDTO.setValue(coupon.getValue());
		return couponDTO;
	}

	protected Response getErrorCodeAsWSResponse(ErrorCodeEnum errorCode, String lang) throws JsonProcessingException {
		ServiceResponse response = new ServiceResponse(errorCode, errorCodeRepository, lang);
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(response);
		return Response.ok(jsonInString, MediaType.APPLICATION_JSON).build();

	}

	public List<NewProjectTypeDTO> convertNewProjectTypeToDTO(List<NewProjectType> list) {
		List<NewProjectTypeDTO> newList = new ArrayList<NewProjectTypeDTO>(list.size());
		for (NewProjectType newProjectType : list) {
			NewProjectTypeDTO newProjectTypeDTO = new NewProjectTypeDTO();
			newProjectTypeDTO.setId(newProjectType.getId().toString());
			newProjectTypeDTO.setName(newProjectType.getName());
			newProjectTypeDTO.setProjectCategoryId(newProjectType.getId().toString());
			newList.add(newProjectTypeDTO);
		}
		return newList;
	}

	public List<ReceiptDetailDTO> convertReceiptDetailsListToDTO(Set<ReceiptDetail> list, String lang) {
		List<ReceiptDetailDTO> newList = new ArrayList<ReceiptDetailDTO>(list.size());
		for (ReceiptDetail receiptDetail : list) {
			newList.add(convertReceiptDetailToDTO(receiptDetail, lang));
		}
		return newList;
	}

	public ReceiptDetailDTO convertReceiptDetailToDTO(ReceiptDetail receiptDetail, String lang) {
		ReceiptDetailDTO receiptDetailDTO = new ReceiptDetailDTO();

		if (StringUtils.isBlank(receiptDetailDTO.getPaymentType())) {
			receiptDetailDTO.setPaymentType(receiptDetail.getReceipt().getPaymentType());
		}
		receiptDetailDTO.setPaymentTypeDisplay(receiptDetailDTO.getPaymentType());
		receiptDetailDTO.setAmount(receiptDetail.getAmount());

		receiptDetailDTO.setName(receiptDetail.getName());
		if (receiptDetail.getReceiptId() != null)
			receiptDetailDTO.setReceiptId(receiptDetail.getReceiptId().toString());
		receiptDetailDTO.setDate(GeneralUtils.formateDateTime(receiptDetail.getCreationDate(), lang));
		if (StringUtils.isBlank(receiptDetailDTO.getName())) {
			receiptDetailDTO.setName(findReceiptDetailName(receiptDetail));
		}
		receiptDetailDTO.setTotalAmount(receiptDetailDTO.getTotalAmount().add(receiptDetailDTO.getAmount()));
		receiptDetailDTO.setType(TransactionTypeEnum.COUPON.getValue());
		return receiptDetailDTO;
	}

	public SupervisorReportDTO convertReceiptListToSupervisorReport(List<Receipt> receiptList) {
		SupervisorReportDTO supervisorReportDTO = new SupervisorReportDTO();
		supervisorReportDTO.setDate(GeneralUtils.formateDateTime(new Date()));
		for (Receipt receipt : receiptList) {
			if (receipt.getPaymentType().equals(PaymentTypeEnum.CASH.getValue())) {
				supervisorReportDTO.setCashAmount(supervisorReportDTO.getCashAmount().add(receipt.getTotalAmount()));
				supervisorReportDTO.setCashReceiptsCount(supervisorReportDTO.getCashReceiptsCount() + 1);
			} else if (receipt.getPaymentType().equals(PaymentTypeEnum.CHEQUE.getValue())) {
				supervisorReportDTO
						.setChequeAmount(supervisorReportDTO.getChequeAmount().add(receipt.getTotalAmount()));
				supervisorReportDTO.setChequeReceiptsCount((supervisorReportDTO.getChequeReceiptsCount() + 1));
			} else if (receipt.getPaymentType().equals(PaymentTypeEnum.CREDIT.getValue())) {
				supervisorReportDTO
						.setCreditCardAmount(supervisorReportDTO.getCreditCardAmount().add(receipt.getTotalAmount()));
				supervisorReportDTO.setCreditCardReceiptsCount((supervisorReportDTO.getCreditCardReceiptsCount() + 1));
			}
			supervisorReportDTO.setTotalAmount(supervisorReportDTO.getCashAmount()
					.add(supervisorReportDTO.getChequeAmount()).add(supervisorReportDTO.getCreditCardAmount()));
		}
		return supervisorReportDTO;
	}

	public ReceiptsReportDTO convertReceiptDetailsToReceiptsReportDTO(List<ReceiptDetail> list, String lang) {
		ReceiptsReportDTO receiptsReportDTO = new ReceiptsReportDTO();
		for (ReceiptDetail receiptDetail : list) {
			ReceiptDetailDTO receiptDetailDTO = convertReceiptDetailToDTO(receiptDetail, lang);
			if (receiptDetailDTO.getPaymentType().equals(PaymentTypeEnum.BANK_TRANSFER.getValue())) {
				receiptsReportDTO.getBankTransferList().add(receiptDetailDTO);
			} else if (receiptDetailDTO.getPaymentType().equals(PaymentTypeEnum.CASH.getValue())) {
				receiptsReportDTO.getCashList().add(receiptDetailDTO);
			} else if (receiptDetailDTO.getPaymentType().equals(PaymentTypeEnum.CHEQUE.getValue())) {
				receiptsReportDTO.getChequeList().add(receiptDetailDTO);
			} else if (receiptDetailDTO.getPaymentType().equals(PaymentTypeEnum.CREDIT.getValue())) {
				receiptsReportDTO.getCreditCardList().add(receiptDetailDTO);
			}

		}

		// calculating totals
		for (ReceiptDetailDTO obj : receiptsReportDTO.getCashList()) {
			receiptsReportDTO.setCashAmount(receiptsReportDTO.getCashAmount().add(obj.getTotalAmount()));
		}
		for (ReceiptDetailDTO obj : receiptsReportDTO.getChequeList()) {
			receiptsReportDTO.setChequeAmount(receiptsReportDTO.getChequeAmount().add(obj.getTotalAmount()));
		}
		for (ReceiptDetailDTO obj : receiptsReportDTO.getCreditCardList()) {
			receiptsReportDTO.setCreditCardAmount(receiptsReportDTO.getCreditCardAmount().add(obj.getTotalAmount()));
		}
		for (ReceiptDetailDTO obj : receiptsReportDTO.getBankTransferList()) {
			receiptsReportDTO
					.setBankTransferAmount(receiptsReportDTO.getBankTransferAmount().add(obj.getTotalAmount()));
		}
		receiptsReportDTO.setTotalAmount(receiptsReportDTO.getCashAmount().add(receiptsReportDTO.getChequeAmount())
				.add(receiptsReportDTO.getCreditCardAmount()).add(receiptsReportDTO.getBankTransferAmount()));
		return receiptsReportDTO;
	}

	private String findReceiptDetailName(ReceiptDetail receiptDetails) {
		String name = "";
		name = receiptDetails.getCoupon().getName();
		return name;
	}

	ReceiptPrintDTO getReceiptPrint(Receipt receipt, String lang, boolean isPrint) {

		if (receipt == null)
			return new ReceiptPrintDTO();
		List<ReceiptDetailDTO> details = convertReceiptDetailsListToDTO(receipt.getReceiptDetailsList(), lang);
		ReceiptPrintDTO receiptPrintDTO = new ReceiptPrintDTO();
		BigDecimal totalAmount = receiptPrintDTO.getTotalAmount();
		for (ReceiptDetailDTO receiptDetailDTO : details) {
			totalAmount = totalAmount.add(receiptDetailDTO.getTotalAmount());
			receiptDetailDTO.setReceiptId(receipt.getId().toString());
		}
		receiptPrintDTO.setNumberOfPrints(receipt.getNumberOfPrints());
		receiptPrintDTO.setTotalAmount(totalAmount);
		receiptPrintDTO.setDetails(details);
		receiptPrintDTO.setReceiptNumber(receipt.getId().toString());
		receiptPrintDTO.setDonatorMobile(receipt.getDonatorPhoneNumber());
		receiptPrintDTO.setDonatorName(receipt.getDonatorName());
		receiptPrintDTO.setPaymentType(receipt.getPaymentType());
		receiptPrintDTO.setPaymentTypeDisplay(receipt.getPaymentType());
		receiptPrintDTO.setReceiptDate(GeneralUtils.formateDateTime(receipt.getCreationDate(), lang));
		receiptRepository.updateNumberOfPrints(receipt.getId());
		return receiptPrintDTO;
	}

	public List<BankDTO> convertBankChequeListToDTO(List<BankCheque> list, String lang) {
		List<BankDTO> newList = new ArrayList<BankDTO>(list.size());
		if (StringUtils.isBlank(lang)) {
			lang = "ar";
		}
		BankDTO bankDTO = null;
		for (BankCheque bankCheque : list) {
			bankDTO = new BankDTO();
			bankDTO.setId(bankCheque.getId());
			if (lang.equalsIgnoreCase("ar")) {
				bankDTO.setName(bankCheque.getNameArabic());
			} else {
				bankDTO.setName(bankCheque.getNameEnglish());
			}
			newList.add(bankDTO);
		}
		return newList;
	}

	public List<BankDTO> convertBankTransferListToDTO(List<BankTransfer> list, String lang) {
		List<BankDTO> newList = new ArrayList<BankDTO>(list.size());
		BankDTO bankDTO = null;
		for (BankTransfer bankTransfer : list) {
			bankDTO = new BankDTO();
			bankDTO.setId(bankTransfer.getId().toString());
			bankDTO.setName(bankTransfer.getName());
			newList.add(bankDTO);
		}
		return newList;
	}

}
