package com.ihsan.webservice;

import java.math.BigDecimal;
import java.math.BigInteger;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ihsan.constants.ErrorCodeEnum;
import com.ihsan.dao.BankChequeRepository;
import com.ihsan.dao.BankDepositRepository;
import com.ihsan.dao.BankTransferRepository;
import com.ihsan.dao.CouponTypeRepository;
import com.ihsan.dao.DelegateCouponRepository;
import com.ihsan.dao.DelegateNoteRepository;
import com.ihsan.dao.DelegateRepository;
import com.ihsan.dao.DonatorRepository;
import com.ihsan.dao.ErrorCodeRepository;
import com.ihsan.dao.FirstTitleRepository;
import com.ihsan.dao.GiftTypeRepository;
import com.ihsan.dao.NewProjectCountryRepository;
import com.ihsan.dao.NewProjectTypeRepository;
import com.ihsan.dao.OldProjectRepository;
import com.ihsan.dao.OrphanRepository;
import com.ihsan.dao.ProjectStudyRepository;
import com.ihsan.dao.ReceiptDetailsRepository;
import com.ihsan.dao.ReceiptRepository;
import com.ihsan.dao.SponsorshipCountryRepository;
import com.ihsan.dao.SponsorshipTypeRepository;
import com.ihsan.dao.TokenRepository;
import com.ihsan.dao.UtilsRepository;
import com.ihsan.dao.charityBoxes.AttachmentRepository;
import com.ihsan.dao.charityBoxes.CharityBoxRepository;
import com.ihsan.dao.charityBoxes.CharityBoxTransferDetailRepository;
import com.ihsan.dao.charityBoxes.CharityBoxTransferRepository;
import com.ihsan.dao.charityBoxes.EmarahRepository;
import com.ihsan.dao.charityBoxes.LocationRepository;
import com.ihsan.dao.charityBoxes.RegionRepository;
import com.ihsan.dao.charityBoxes.RouteDetailRepository;
import com.ihsan.dao.charityBoxes.SafetyCaseRepository;
import com.ihsan.dao.charityBoxes.SubLocationRepository;
import com.ihsan.entities.BankCheque;
import com.ihsan.entities.BankTransfer;
import com.ihsan.entities.CouponType;
import com.ihsan.entities.Delegate;
import com.ihsan.entities.Donator;
import com.ihsan.entities.FirstTitle;
import com.ihsan.entities.GiftType;
import com.ihsan.entities.NewProjectCountry;
import com.ihsan.entities.NewProjectType;
import com.ihsan.entities.OldProject;
import com.ihsan.entities.Orphan;
import com.ihsan.entities.PaymentTypeEnum;
import com.ihsan.entities.ProjectStudy;
import com.ihsan.entities.Receipt;
import com.ihsan.entities.ReceiptDetail;
import com.ihsan.entities.ReceiptPayment;
import com.ihsan.entities.charityBoxes.CharityBox;
import com.ihsan.entities.charityBoxes.CharityBoxActionType;
import com.ihsan.entities.charityBoxes.CharityBoxTransfer;
import com.ihsan.entities.charityBoxes.CharityBoxTransferDetail;
import com.ihsan.entities.charityBoxes.Emarah;
import com.ihsan.entities.charityBoxes.Location;
import com.ihsan.entities.charityBoxes.Region;
import com.ihsan.entities.charityBoxes.RouteDetail;
import com.ihsan.entities.charityBoxes.SubLocation;
import com.ihsan.enums.CharityBoxActionTypeEnum;
import com.ihsan.enums.CouponTypeEnum;
import com.ihsan.service.DelegateService;
import com.ihsan.service.UtilsService;
import com.ihsan.util.GeneralUtils;
import com.ihsan.webservice.dto.BankDTO;
import com.ihsan.webservice.dto.CouponReportDTO;
import com.ihsan.webservice.dto.CouponTypeDTO;
import com.ihsan.webservice.dto.DelegateDTO;
import com.ihsan.webservice.dto.DonatorDTO;
import com.ihsan.webservice.dto.NewCouponDTO;
import com.ihsan.webservice.dto.NewProjectDTO;
import com.ihsan.webservice.dto.NewProjectTypeDTO;
import com.ihsan.webservice.dto.NewSponsorshipDTO;
import com.ihsan.webservice.dto.OldProjectDTO;
import com.ihsan.webservice.dto.OldProjectDonationDTO;
import com.ihsan.webservice.dto.OldSponsorshipDTO;
import com.ihsan.webservice.dto.OrphanDTO;
import com.ihsan.webservice.dto.OrphanSponsorshipDTO;
import com.ihsan.webservice.dto.ProjectStudyDTO;
import com.ihsan.webservice.dto.ReceiptDTO;
import com.ihsan.webservice.dto.ReceiptDetailDTO;
import com.ihsan.webservice.dto.ReceiptPaymentDTO;
import com.ihsan.webservice.dto.ReceiptPrintDTO;
import com.ihsan.webservice.dto.ReceiptsReportDTO;
import com.ihsan.webservice.dto.ServiceResponse;
import com.ihsan.webservice.dto.SupervisorReportDTO;
import com.ihsan.webservice.dto.TransactionTypeEnum;
import com.ihsan.webservice.dto.charityBox.CharityBoxDTO;
import com.ihsan.webservice.dto.charityBox.CharityBoxTransferDTO;
import com.ihsan.webservice.dto.charityBox.LocationDTO;
import com.ihsan.webservice.dto.charityBox.RegionDTO;
import com.ihsan.webservice.dto.charityBox.SubLocationDTO;

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
	protected DelegateCouponRepository delegateCouponRepository;

	@Autowired
	protected DelegateNoteRepository delegateNoteRepository;

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

	@Autowired
	protected CharityBoxRepository charityBoxRepository;

	@Autowired
	protected EmarahRepository emarahRepository;

	@Autowired
	protected RegionRepository regionRepository;

	@Autowired
	protected LocationRepository locationRepository;

	@Autowired
	protected SubLocationRepository subLocationRepository;

	@Autowired
	protected CharityBoxTransferRepository charityBoxTransferRepository;

	@Autowired
	protected AttachmentRepository attachmentRepository;

	@Autowired
	protected RouteDetailRepository routeDetailRepository;

	@Autowired
	protected NewProjectCountryRepository newProjectCountryRepository;

	@Autowired
	protected DonatorRepository sponsorRepository;

	@Autowired
	protected OldProjectRepository oldProjectRepository;

	@Autowired
	protected ProjectStudyRepository projectStudyRepository;

	@Autowired
	protected SponsorshipTypeRepository sponsorshipTypeRepository;

	@Autowired
	protected SponsorshipCountryRepository countrySponsorshipRepository;

	@Autowired
	protected GiftTypeRepository giftTypeRepository;

	@Autowired
	protected OrphanRepository orphanRepository;

	@Autowired
	protected FirstTitleRepository firstTitleRepository;

	@Autowired
	protected TokenRepository userTokenRepository;

	@Autowired
	protected BankDepositRepository bankDepositRepository;

	@Autowired
	protected CharityBoxTransferDetailRepository charityBoxTransferDetailRepository;

	@Autowired
	protected SafetyCaseRepository safetyCaseRepository;

	@Value("${debugEnabled}")
	protected boolean debugEnabled;

	protected boolean isSuccess(ErrorCodeEnum errorCode) {
		return errorCode != null
				&& errorCode.getErrorCode().equalsIgnoreCase(ErrorCodeEnum.SUCCESS_CODE.getErrorCode());
	}

	protected boolean isError(ErrorCodeEnum errorCode) {
		return errorCode == null
				|| !errorCode.getErrorCode().equalsIgnoreCase(ErrorCodeEnum.SUCCESS_CODE.getErrorCode());
	}

	protected boolean isSuccess(String errorCode) {
		return errorCode.equalsIgnoreCase(ErrorCodeEnum.SUCCESS_CODE.getErrorCode());
	}

	protected boolean isError(String errorCode) {
		return !errorCode.equalsIgnoreCase(ErrorCodeEnum.SUCCESS_CODE.getErrorCode());
	}

	protected boolean isResponseHasError(ServiceResponse response) {
		return response == null
				|| !response.getErrorCode().getErrorCode().equalsIgnoreCase(ErrorCodeEnum.SUCCESS_CODE.getErrorCode());
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
				receiptDetails.setTransactionType(TransactionTypeEnum.COUPON.getValue());
				receiptDetails.setName(couponDTO.getCouponTypeName());
				receiptDetails.setPaymentType(receiptDTO.getPaymentType().getValue());
				receipt.getReceiptDetailsList().add(receiptDetails);
				totalAmountForReceipt = totalAmountForReceipt.add(couponDTO.getAmount());
			}
		}
		if (CollectionUtils.isNotEmpty(receiptDTO.getNewProjectsList())) {
			for (NewProjectDTO newProjectDTO : receiptDTO.getNewProjectsList()) {
				receiptDetails = new ReceiptDetail();
				receiptDetails.setReceipt(receipt);
				receiptDetails.setAmount(newProjectDTO.getAmount());
				receiptDetails.setCreatedBy(receipt.getCreatedBy());
				receiptDetails.setProjectStudy(new ProjectStudy(newProjectDTO.getProjectStudyId()));
				receiptDetails.setProjectCountry(new NewProjectCountry(newProjectDTO.getProjectCountryId()));
				receiptDetails.setReceiptType(new NewProjectType(newProjectDTO.getNewProjectTypeId()));
				receiptDetails.setProjectCommitment(newProjectDTO.getCommitment());
				receiptDetails.setProjectName(newProjectDTO.getProjectName());
				if (!GeneralUtils.isEmptyNumber(newProjectDTO.getDonatorId())) {
					receiptDetails.setDonator(new Donator(newProjectDTO.getDonatorId()));
				} else {
					if (!GeneralUtils.isEmptyNumber(newProjectDTO.getNewDonatorDTO().getDonatorCountryId())) {
						receiptDetails.setDonatorCountry(
								new NewProjectCountry(newProjectDTO.getNewDonatorDTO().getDonatorCountryId()));
					}
					receiptDetails.setDonatorEmail(newProjectDTO.getNewDonatorDTO().getDonatorEmail());
					receiptDetails.setDonatorMobile(newProjectDTO.getNewDonatorDTO().getDonatorMobile());
					receiptDetails.setDonatorName(newProjectDTO.getNewDonatorDTO().getDonatorName());
					receiptDetails.setDonatorPhone(newProjectDTO.getNewDonatorDTO().getDonatorPhone());
					receiptDetails.setDonatorPOBOX(newProjectDTO.getNewDonatorDTO().getDonatorPOBOX());
				}
				receiptDetails.setTransactionType(TransactionTypeEnum.PROJECT_NEW.getValue());
				receiptDetails.setName(newProjectDTO.getProjectName());
				receiptDetails.setPaymentType(receiptDTO.getPaymentType().getValue());
				receipt.getReceiptDetailsList().add(receiptDetails);
				totalAmountForReceipt = totalAmountForReceipt.add(newProjectDTO.getAmount());
			}
		}

		if (CollectionUtils.isNotEmpty(receiptDTO.getOldProjectsList())) {
			for (OldProjectDonationDTO oldProjectDTO : receiptDTO.getOldProjectsList()) {
				receiptDetails = new ReceiptDetail();
				receiptDetails.setReceipt(receipt);
				receiptDetails.setAmount(oldProjectDTO.getAmount());
				receiptDetails.setCreatedBy(receipt.getCreatedBy());
				receiptDetails.setOldProject(new OldProject(oldProjectDTO.getOldProjectId()));
				receiptDetails.setDonator(new Donator(oldProjectDTO.getDonatorId()));
				receiptDetails.setName(oldProjectDTO.getOldProjectName());
				receiptDetails.setTransactionType(TransactionTypeEnum.PROJECT_OLD.getValue());
				receiptDetails.setPaymentType(receiptDTO.getPaymentType().getValue());
				receipt.getReceiptDetailsList().add(receiptDetails);
				totalAmountForReceipt = totalAmountForReceipt.add(oldProjectDTO.getAmount());
			}
		}
		if (CollectionUtils.isNotEmpty(receiptDTO.getNewSponsorshipList())) {
			for (NewSponsorshipDTO newSponsorshipDTO : receiptDTO.getNewSponsorshipList()) {
				for (OrphanSponsorshipDTO orphanSponsorshipDTO : newSponsorshipDTO.getOrphansList()) {
					receiptDetails = new ReceiptDetail();
					receiptDetails.setReceipt(receipt);
					receiptDetails.setCreatedBy(receipt.getCreatedBy());
					receiptDetails.setOrphanId(orphanSponsorshipDTO.getOrphanId());
					if (StringUtils.isNotBlank(orphanSponsorshipDTO.getSponsorshipStartDate())) {
						receiptDetails.setSponsorshipStartDate(
								GeneralUtils.parseDate(orphanSponsorshipDTO.getSponsorshipStartDate()));
					}
					BigDecimal amountPerMonth = orphanSponsorshipDTO.getAmount()
							.divide(new BigDecimal(orphanSponsorshipDTO.getNumberOfMonths()));
					receiptDetails.setCaseAmount(amountPerMonth);
					receiptDetails.setAmount(orphanSponsorshipDTO.getAmount());
					receiptDetails.setCasePaymentType(receiptDTO.getPaymentType().getValue());
					receiptDetails.setSponsorFor(newSponsorshipDTO.getSponsorFor());
					if (StringUtils.isNotBlank(newSponsorshipDTO.getFirstTitleId()))
						receiptDetails.setFirstTitle(new FirstTitle(newSponsorshipDTO.getFirstTitleId()));
					if (StringUtils.isNotBlank(orphanSponsorshipDTO.getGiftId())) {
						receiptDetails.setGiftType(new GiftType(orphanSponsorshipDTO.getGiftId()));
						receiptDetails.setGiftAmount(orphanSponsorshipDTO.getGiftAmount());
						if (orphanSponsorshipDTO.getGiftAmount() != null) {
							BigDecimal newAmount = receiptDetails.getAmount().add(orphanSponsorshipDTO.getGiftAmount());
							receiptDetails.setAmount(newAmount);
						}
					}
					if (!GeneralUtils.isEmptyNumber(newSponsorshipDTO.getSponsorId())) {
						receiptDetails.setSponsor(new Donator(newSponsorshipDTO.getSponsorId()));
					} else {
						if (!GeneralUtils.isEmptyNumber(newSponsorshipDTO.getNewDonatorDTO().getDonatorCountryId())) {
							receiptDetails.setDonatorCountry(
									new NewProjectCountry(newSponsorshipDTO.getNewDonatorDTO().getDonatorCountryId()));
						}
						receiptDetails.setDonatorEmail(newSponsorshipDTO.getNewDonatorDTO().getDonatorEmail());
						receiptDetails.setDonatorMobile(newSponsorshipDTO.getNewDonatorDTO().getDonatorMobile());
						receiptDetails.setDonatorName(newSponsorshipDTO.getNewDonatorDTO().getDonatorName());
						receiptDetails.setDonatorPhone(newSponsorshipDTO.getNewDonatorDTO().getDonatorPhone());
						receiptDetails.setDonatorPOBOX(newSponsorshipDTO.getNewDonatorDTO().getDonatorPOBOX());
					}
					receiptDetails.setName(orphanSponsorshipDTO.getOrphanName());
					receiptDetails.setPaymentType(receiptDTO.getPaymentType().getValue());
					receiptDetails.setCasePaymentNumberOfMonths(orphanSponsorshipDTO.getNumberOfMonths());
					receiptDetails.setCaseAmountPerMonth(amountPerMonth);
					receiptDetails.setTransactionType(TransactionTypeEnum.SPONSORSHIP_NEW.getValue());
					receipt.getReceiptDetailsList().add(receiptDetails);
					totalAmountForReceipt = totalAmountForReceipt.add(orphanSponsorshipDTO.getAmount());
					if (!GeneralUtils.isEmptyNumber(orphanSponsorshipDTO.getGiftAmount()))
						totalAmountForReceipt = totalAmountForReceipt.add(orphanSponsorshipDTO.getGiftAmount());
				}

			}
		}

		if (CollectionUtils.isNotEmpty(receiptDTO.getOldSponsorshipList())) {
			for (OldSponsorshipDTO oldSponsorshipDTO : receiptDTO.getOldSponsorshipList()) {
				for (OrphanSponsorshipDTO orphanSponsorshipDTO : oldSponsorshipDTO.getOrphansList()) {
					receiptDetails = new ReceiptDetail();
					receiptDetails.setReceipt(receipt);
					receiptDetails.setCreatedBy(receipt.getCreatedBy());
					receiptDetails.setOrphanId(orphanSponsorshipDTO.getOrphanId());
					if (StringUtils.isNotBlank(orphanSponsorshipDTO.getSponsorshipStartDate())) {
						receiptDetails.setSponsorshipStartDate(
								GeneralUtils.parseDate(orphanSponsorshipDTO.getSponsorshipStartDate()));
					}
					BigDecimal amountPerMonth = orphanSponsorshipDTO.getAmount()
							.divide(new BigDecimal(orphanSponsorshipDTO.getNumberOfMonths()));
					receiptDetails.setCaseAmount(amountPerMonth);
					receiptDetails.setAmount(orphanSponsorshipDTO.getAmount());
					receiptDetails.setCasePaymentType(receiptDTO.getPaymentType().getValue());
					receiptDetails.setSponsorFor(orphanSponsorshipDTO.getSponsorFor());
					if (StringUtils.isNotBlank(orphanSponsorshipDTO.getFirstTitleId()))
						receiptDetails.setFirstTitle(new FirstTitle(orphanSponsorshipDTO.getFirstTitleId()));
					if (StringUtils.isNotBlank(orphanSponsorshipDTO.getGiftId())) {
						receiptDetails.setGiftType(new GiftType(orphanSponsorshipDTO.getGiftId()));
						receiptDetails.setGiftAmount(orphanSponsorshipDTO.getGiftAmount());
						if (orphanSponsorshipDTO.getGiftAmount() != null) {
							BigDecimal newAmount = receiptDetails.getAmount().add(orphanSponsorshipDTO.getGiftAmount());
							receiptDetails.setAmount(newAmount);
						}
					}
					receiptDetails.setSponsor(new Donator(oldSponsorshipDTO.getSponsorId()));
					receiptDetails.setName(orphanSponsorshipDTO.getOrphanName());
					receiptDetails.setPaymentType(receiptDTO.getPaymentType().getValue());
					receiptDetails.setCasePaymentNumberOfMonths(orphanSponsorshipDTO.getNumberOfMonths());
					receiptDetails.setCaseAmountPerMonth(amountPerMonth);
					receiptDetails.setTransactionType(TransactionTypeEnum.SPONSORSHIP_OLD.getValue());
					receipt.getReceiptDetailsList().add(receiptDetails);
					totalAmountForReceipt = totalAmountForReceipt.add(orphanSponsorshipDTO.getAmount());
					if (!GeneralUtils.isEmptyNumber(orphanSponsorshipDTO.getGiftAmount()))
						totalAmountForReceipt = totalAmountForReceipt.add(orphanSponsorshipDTO.getGiftAmount());
				}

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
			receiptPayment.setBankCode(paymentDTO.getBankCode());
			receiptPayment.setChequeDate(GeneralUtils.parseDate(paymentDTO.getChequeDate()));
			receiptPayment.setChequeNumber(paymentDTO.getChequeNumber());
		} else if (receiptDTO.getPaymentType() == PaymentTypeEnum.CREDIT) {
			receiptPayment.setCreditCardTransactionNumber(paymentDTO.getCreditCardTransactionNumber());
		} else if (receiptDTO.getPaymentType() == PaymentTypeEnum.BANK_TRANSFER) {
			receiptPayment.setDeductionNumber(paymentDTO.getDeductionNumber());
			receiptPayment.setBankCode(paymentDTO.getBankCode());
			receiptPayment.setAccountName(paymentDTO.getAccountName());
			receiptPayment.setAccountNumber(paymentDTO.getAccountNumber());
		} else if (receiptDTO.getPaymentType() == PaymentTypeEnum.DEPOSIT) {
			receiptPayment.setDepositBankAccountId(paymentDTO.getDepositBankAccountId());
			receiptPayment.setDepositTransactionNumber(paymentDTO.getDepositTransactionNumber());
		}
		receiptPayment.setCreatedBy(receipt.getCreatedBy());
		receipt.setReceiptPayment(receiptPayment);
		return receipt;
	}

	public DelegateDTO convertDelegateToDTO(Delegate delegate) {
		DelegateDTO delegateDTO = new DelegateDTO();
		delegateDTO.setId(delegate.getId().toString());
		delegateDTO.setAdmin(delegate.isAdmin());
		delegateDTO.setExpiryDate(GeneralUtils.formatDate(delegate.getExpiryDate()));
		delegateDTO.setMaxLimit(new BigDecimal(delegate.getMaxLimit()));
		delegateDTO.setName(delegate.getName());
		delegateDTO.setNumber(delegate.getNumber());
		delegateDTO.setStartDate(GeneralUtils.formatDate(delegate.getStartDate()));
		delegateDTO.setToken(delegate.getToken());
		delegateDTO.setUserName(delegate.getUserName());
		delegateDTO.setPinCode(delegate.getPinCode());
		delegateDTO.setSupervisor(delegate.isSupervisor());
		delegateDTO.setMobile(delegate.getMobile());
		delegateDTO.setCoupon(delegate.isCoupon());
		delegateDTO.setCharityBox(delegate.isCharityBox());
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
		couponDTO.setVersion(coupon.getVersion());
		if (coupon.getType() == CouponTypeEnum.YEARLY.getValue()) {
			couponDTO.setType(CouponTypeEnum.YEARLY);
		} else if (coupon.getType() == CouponTypeEnum.QUICK_PAY.getValue()) {
			couponDTO.setType(CouponTypeEnum.QUICK_PAY);
		}
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
		receiptDetailDTO.setPaymentType(receiptDetail.getPaymentType());

		receiptDetailDTO.setPaymentTypeDisplay(receiptDetail.getPaymentType());
		receiptDetailDTO.setAmount(receiptDetail.getAmount());

		receiptDetailDTO.setName(receiptDetail.getName());
		if (receiptDetail.getReceiptId() != null) {
			receiptDetailDTO.setReceiptId(receiptDetail.getReceiptId().toString());
			Receipt receipt = receiptRepository.findOne(receiptDetail.getReceiptId());
			if (receipt != null)
				receiptDetailDTO.setReceiptNumber(receipt.getNumber());
		}
		receiptDetailDTO.setDate(GeneralUtils.formatDateTime(receiptDetail.getCreationDate(), lang));
		if (StringUtils.isBlank(receiptDetailDTO.getName())) {
			receiptDetailDTO.setName(findReceiptDetailName(receiptDetail));
		}
		receiptDetailDTO.setTotalAmount(receiptDetailDTO.getTotalAmount().add(receiptDetailDTO.getAmount()));
		receiptDetailDTO.setType(receiptDetail.getTransactionType());
		return receiptDetailDTO;
	}

	public SupervisorReportDTO convertReceiptListToSupervisorReport(List<Receipt> receiptList) {
		SupervisorReportDTO supervisorReportDTO = new SupervisorReportDTO();
		supervisorReportDTO.setDate(GeneralUtils.formatDateTime(new Date()));
		for (Receipt receipt : receiptList) {
			if (receipt.getReceiptPayment().getPaymentType().equals(PaymentTypeEnum.CASH.getValue())) {
				supervisorReportDTO.setCashAmount(supervisorReportDTO.getCashAmount().add(receipt.getTotalAmount()));
				supervisorReportDTO.setCashReceiptsCount(supervisorReportDTO.getCashReceiptsCount() + 1);
			} else if (receipt.getReceiptPayment().getPaymentType().equals(PaymentTypeEnum.CHEQUE.getValue())) {
				supervisorReportDTO
						.setChequeAmount(supervisorReportDTO.getChequeAmount().add(receipt.getTotalAmount()));
				supervisorReportDTO.setChequeReceiptsCount((supervisorReportDTO.getChequeReceiptsCount() + 1));
			} else if (receipt.getReceiptPayment().getPaymentType().equals(PaymentTypeEnum.CREDIT.getValue())) {
				supervisorReportDTO
						.setCreditCardAmount(supervisorReportDTO.getCreditCardAmount().add(receipt.getTotalAmount()));
				supervisorReportDTO.setCreditCardReceiptsCount((supervisorReportDTO.getCreditCardReceiptsCount() + 1));
			} else if (receipt.getReceiptPayment().getPaymentType().equals(PaymentTypeEnum.DEPOSIT.getValue())) {
				supervisorReportDTO
						.setDepositAmount(supervisorReportDTO.getDepositAmount().add(receipt.getTotalAmount()));
				supervisorReportDTO.setDepositReceiptsCount((supervisorReportDTO.getDepositReceiptsCount() + 1));
			}
			supervisorReportDTO.setTotalAmount(supervisorReportDTO.getCashAmount()
					.add(supervisorReportDTO.getChequeAmount()).add(supervisorReportDTO.getCreditCardAmount())
					.add(supervisorReportDTO.getDepositAmount()));
		}
		return supervisorReportDTO;
	}

	public List<CouponReportDTO> convertReceiptDetailsToCouponReportDTO(List<ReceiptDetail> list, String lang) {
		List<CouponReportDTO> resultList = new ArrayList<CouponReportDTO>();
		for (ReceiptDetail receiptDetail : list) {
			if (GeneralUtils.isBigIntegerGreaterThanZero(receiptDetail.getCouponId())) {
				CouponType couponType = utilsService.getCouponFromCache(receiptDetail.getCouponId());
				CouponReportDTO couponReportDTONew = new CouponReportDTO(couponType, receiptDetail.getAmount());
				if (!resultList.contains(couponReportDTONew)) {
					resultList.add(couponReportDTONew);
				} else {
					CouponReportDTO couponReportDTOExisting = resultList.get(resultList.indexOf(couponReportDTONew));
					couponReportDTOExisting
							.setAmount(couponReportDTOExisting.getAmount().add(couponReportDTONew.getAmount()));
				}
			}
		}
		return resultList;
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
		receiptPrintDTO.setReceiptNumber(receipt.getNumber().toString());
		receiptPrintDTO.setDonatorMobile(receipt.getDonatorPhoneNumber());
		receiptPrintDTO.setDonatorName(receipt.getDonatorName());
		receiptPrintDTO.setPaymentType(receipt.getPaymentType());
		receiptPrintDTO.setPaymentTypeDisplay(receipt.getPaymentType());
		receiptPrintDTO.setReceiptDate(GeneralUtils.formatDateTime(receipt.getCreationDate(), lang));
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

	public List<CharityBoxDTO> convertCharityBoxListToDTO(List<CharityBox> inputList) {
		if (inputList == null || inputList.size() == 0) {
			return new ArrayList<CharityBoxDTO>();
		}
		List<CharityBoxDTO> resultList = new ArrayList<CharityBoxDTO>(inputList.size());
		for (CharityBox charityBox : inputList) {
			resultList.add(convertCharityBoxToDTO(charityBox));
		}
		return resultList;
	}

	public List<SubLocationDTO> convertRouteDetailListToDTO(List<RouteDetail> inputList) {
		if (inputList == null || inputList.size() == 0) {
			return new ArrayList<SubLocationDTO>();
		}
		List<SubLocationDTO> resultList = new ArrayList<SubLocationDTO>(inputList.size());
		for (RouteDetail routeDetail : inputList) {
			resultList.add(new SubLocationDTO(routeDetail.getSubLocation()));
		}
		return resultList;
	}

	public CharityBoxDTO convertCharityBoxToDTO(CharityBox charityBox) {
		CharityBoxDTO charityBoxDTO = new CharityBoxDTO();
		if (charityBox == null)
			return charityBoxDTO;
		charityBoxDTO.setId(charityBox.getId().toString());
		charityBoxDTO.setBarcode(charityBox.getBarcode());
		charityBoxDTO.setName(charityBox.getName());
		// logger.info("####### charityBox.getSubLocationId(): " +
		// charityBox.getSubLocationId());
		SubLocation subLocation = utilsService.getSubLocationFromCache(charityBox.getSubLocationId());
		// logger.info("####### subLocation: " + subLocation);
		if (subLocation != null) {
			// logger.info("####### subLocation.getName(): " + subLocation.getName());
			// logger.info("####### subLocation.getId(): " + subLocation.getId());
			// logger.info("####### subLocation.getLocationId(): " +
			// subLocation.getLocationId());
		}
		if (subLocation != null && subLocation.getId() != null && subLocation.getId().compareTo(BigInteger.ZERO) > 0) {
			charityBoxDTO.setSubLocation(subLocation.getName());
			Location location = utilsService.getLocationFromCache(subLocation.getLocationId());
			// logger.info("####### location: " + location);
			if (location != null) {
				// logger.info("####### location.getName(): " + location.getName());
				// logger.info("####### location.getId(): " + location.getId());
				// logger.info("####### location.getRegionId(): " + location.getRegionId());
			}
			if (location != null && location.getId() != null && location.getId().compareTo(BigInteger.ZERO) > 0) {
				charityBoxDTO.setLocation(location.getName());
				Region region = utilsService.getRegionFromCache(location.getRegionId());
				// logger.info("####### region: " + region);
				if (location != null) {
					// logger.info("####### region.getName(): " + location.getName());
				}
				if (region != null && region.getId() != null && region.getId().compareTo(BigInteger.ZERO) > 0) {
					charityBoxDTO.setRegion(region.getName());
				}
			}
		}
		charityBoxDTO.setSource(utilsService.getCharityBoxCategoryFromCache(charityBox.getCategoryId()).getName());
		charityBoxDTO.setSourceId(charityBox.getCategoryId());
		charityBoxDTO.setStatus(utilsService.getCharityBoxStatusFromCache(charityBox.getStatusId()).getName());
		charityBoxDTO.setType(utilsService.getCharityBoxTypeFromCache(charityBox.getTypeId()).getName());
		return charityBoxDTO;
	}

	public List<RegionDTO> convertRegionToDTO(List<Region> inputList) {
		if (inputList == null || inputList.size() == 0) {
			return new ArrayList<RegionDTO>();
		}
		List<RegionDTO> resultList = new ArrayList<RegionDTO>(inputList.size());
		for (Region region : inputList) {
			resultList.add(new RegionDTO(region.getId(), region.getName()));
		}
		return resultList;
	}

	public List<LocationDTO> convertLocationToDTO(List<Location> inputList) {
		if (inputList == null || inputList.size() == 0) {
			return new ArrayList<LocationDTO>();
		}
		List<LocationDTO> resultList = new ArrayList<LocationDTO>(inputList.size());
		for (Location location : inputList) {
			resultList.add(new LocationDTO(location.getId(), location.getRegionId(), location.getName()));
		}
		return resultList;
	}

	public List<SubLocationDTO> convertSubLocationListToDTO(List<SubLocation> inputList, boolean concatDetails) {
		if (inputList == null || inputList.size() == 0) {
			return new ArrayList<SubLocationDTO>();
		}
		List<SubLocationDTO> resultList = new ArrayList<SubLocationDTO>(inputList.size());
		SubLocationDTO subLocationDTO = null;
		Region region = null;
		Location location = null;
		for (SubLocation subLocation : inputList) {
			subLocationDTO = new SubLocationDTO(subLocation.getId(), subLocation.getLocationId(), subLocation.getName(),
					subLocation.getLocationLatitude(), subLocation.getLocationLongitude());
			if (concatDetails && subLocation.getLocationId() != null
					&& subLocation.getLocationId().compareTo(BigInteger.ZERO) > 0) {
				location = utilsService.getLocationFromCache(subLocation.getLocationId());
				if (location != null && location.getRegionId() != null
						&& location.getRegionId().compareTo(BigInteger.ZERO) > 0) {
					region = utilsService.getRegionFromCache(location.getRegionId());
				}
				if (location != null && StringUtils.isNotBlank(location.getName()))
					subLocationDTO.setName(location.getName() + " - " + subLocationDTO.getName());
				if (region != null && StringUtils.isNotBlank(region.getName()))
					subLocationDTO.setName(region.getName() + " - " + subLocationDTO.getName());
			}
			resultList.add(subLocationDTO);
		}
		return resultList;
	}

	public Region createNewRegion(RegionDTO regionDTO) {
		Region region = new Region(regionDTO.getName().trim(), new BigInteger(regionDTO.getEmarahId()));
		List<Region> resultList = regionRepository.findByNameAndEmarahId(region.getName(), region.getEmarahId());
		if (resultList != null && resultList.size() > 0) {
			region = resultList.get(0);
			region.setAlreadyExist(true);
			logger.info("##### NEW REGION ALREADY EXISTS: " + region.getId());
		} else {
			regionRepository.save(region);
			if (debugEnabled)
				logger.info("##### CREATED NEW REGION: " + region.getId());
			region = regionRepository.findOne(region.getId());
			regionRepository.findAll().add(region);
		}
		return region;
	}

	public Location createNewLocation(LocationDTO locationDTO, BigInteger delegateId) {
		Location location = new Location(locationDTO.getName().trim(), locationDTO.getRegionId());
		location.setAddress(locationDTO.getAddress());
		if (delegateId != null)
			location.setCreatedBy(new Delegate(delegateId));
		location.setMobile(locationDTO.getMobile());
		location.setLocationLatitude(locationDTO.getLocationLatitude());
		location.setLocationLongitude(locationDTO.getLocationLongitude());
		List<Location> resultList = locationRepository.findByNameAndRegionId(location.getName(),
				location.getRegionId());
		if (resultList != null && resultList.size() > 0) {
			location = resultList.get(0);
			location.setAlreadyExist(true);
			logger.info("##### NEW LOCATION ALREADY EXISTS: " + location.getId());
		} else {
			Long locationNumber = locationRepository.getMaxLocationNumber();
			locationNumber = locationNumber + 1;
			location.setNumber(locationNumber.toString());
			locationRepository.save(location);
			if (debugEnabled)
				logger.info("##### CREATED NEW LOCATION: " + location.getId());
			location = locationRepository.findOne(location.getId());
			locationRepository.findAll().add(location);
		}

		return location;
	}

	public SubLocation createNewSubLocation(SubLocationDTO subLocationDTO, String actionType, BigInteger delegateId) {
		SubLocation subLocation = new SubLocation(subLocationDTO.getName().trim(), subLocationDTO.getLocationId(),
				subLocationDTO.getLocationLatitude(), subLocationDTO.getLocationLongitude());
		if (delegateId != null) {
			subLocation.setCreatedBy(new Delegate(delegateId));
			subLocation.setDelegate(new Delegate(delegateId));
		}
		subLocation.setMobile(subLocationDTO.getMobile());
		subLocation.setAddress(subLocationDTO.getAddress());
		List<SubLocation> resultList = subLocationRepository.findByNameAndLocationId(subLocation.getName(),
				subLocation.getLocationId());
		logger.info("##### createNewSubLocation,name: " + subLocation.getName() + ",locationId: "
				+ subLocation.getLocationId() + ",resultList: " + resultList);
		if (resultList != null && resultList.size() > 0) {
			subLocation = resultList.get(0);
			subLocation.setAlreadyExist(true);
			logger.info("##### NEW SUB LOCATION ALREADY EXISTS: " + subLocation.getId());
		}

		if (!subLocation.isAlreadyExist()) {
			subLocationRepository.save(subLocation);
			if (debugEnabled)
				logger.info("##### CREATED NEW SUB LOCATION: " + subLocation.getId());
			subLocation = subLocationRepository.findOne(subLocation.getId());
			subLocationRepository.findAll().add(subLocation);
		} else {
			if (actionType.equals(CharityBoxActionTypeEnum.INSERT.getValue())) {
				subLocation.setErrorCode(ErrorCodeEnum.SUBLOCATION_ALREADY_HAS_CHARITYBOX);
			}
		}

		return subLocation;
	}

	public CharityBoxTransfer convertCharityBoxTransferDTOToEntity(CharityBoxTransferDTO charityBoxTransferDTO) {

		CharityBoxTransfer charityBoxTransfer = new CharityBoxTransfer();
		Long transferNumber = charityBoxTransferRepository.getMaxTransferNumber();
		transferNumber = transferNumber + 1;
		charityBoxTransfer.setTransferNumber(new BigInteger(transferNumber.toString()));
		CharityBoxTransferDetail detail = new CharityBoxTransferDetail();
		if (charityBoxTransferDTO.getNewRegionDTO() != null
				&& StringUtils.isNotBlank(charityBoxTransferDTO.getNewRegionDTO().getName())
				&& !charityBoxTransferDTO.getNewRegionDTO().getName().equalsIgnoreCase("string")) {
			Region region = createNewRegion(charityBoxTransferDTO.getNewRegionDTO());
			if (region != null)
				charityBoxTransferDTO.setRegionId(region.getId());
		}

		if (charityBoxTransferDTO.getNewLocationDTO() != null
				&& StringUtils.isNotBlank(charityBoxTransferDTO.getNewLocationDTO().getName())
				&& !charityBoxTransferDTO.getNewLocationDTO().getName().equalsIgnoreCase("string")) {
			if (charityBoxTransferDTO.getNewLocationDTO().getRegionId() == null)
				charityBoxTransferDTO.getNewLocationDTO().setRegionId(charityBoxTransferDTO.getRegionId());
			Location location = createNewLocation(charityBoxTransferDTO.getNewLocationDTO(),
					charityBoxTransferDTO.getDelegateId());
			if (location != null)
				charityBoxTransferDTO.setLocationId(location.getId());
		}

		// التحقق من انه لا يوجد مكان لنفس الحى مضاف مسبقا
		// وذلك لمنع زرع اكثر من حصالة فى نفس المكان
		if (charityBoxTransferDTO.getNewSubLocationDTO() != null
				&& StringUtils.isNotBlank(charityBoxTransferDTO.getNewSubLocationDTO().getName())
				&& !charityBoxTransferDTO.getNewSubLocationDTO().getName().equalsIgnoreCase("string")) {

			logger.info("######### charityBoxTransferDTO.getLocationId(): " + charityBoxTransferDTO.getLocationId());
			logger.info("######### charityBoxTransferDTO.getNewSubLocationDTO().getLocationId(): "
					+ charityBoxTransferDTO.getNewSubLocationDTO().getLocationId());
			if (charityBoxTransferDTO.getNewSubLocationDTO().getLocationId() == null)
				charityBoxTransferDTO.getNewSubLocationDTO().setLocationId(charityBoxTransferDTO.getLocationId());
			SubLocation subLocation = createNewSubLocation(charityBoxTransferDTO.getNewSubLocationDTO(),
					charityBoxTransferDTO.getActionType().getValue(), charityBoxTransferDTO.getDelegateId());
			if (subLocation != null) {
				if (subLocation.getErrorCode() != null) {
					charityBoxTransfer.setErrorCode(subLocation.getErrorCode());
					return charityBoxTransfer;
				}
				charityBoxTransferDTO.setSubLocationId(subLocation.getId());
			}

		}
		detail.setActionType(new CharityBoxActionType(charityBoxTransferDTO.getActionType().getValue()));
		detail.setTransferType(new BigInteger(detail.getActionType().getId()));
		detail.setCharityBox(new CharityBox(charityBoxTransferDTO.getCharityBoxId()));
		if (charityBoxTransferDTO.getActionType().getValue().equals(CharityBoxActionTypeEnum.INSERT.getValue())) {
			detail.setNewCharityBox(new CharityBox(charityBoxTransferDTO.getCharityBoxId()));
			detail.setCharityBox(null);
		}

		if (debugEnabled)
			logger.info("###### convertCharityBoxTransferDTOToEntity,supervisor id: "
					+ charityBoxTransferDTO.getDelegateId());

		detail.setCreatedBy(new Delegate(charityBoxTransferDTO.getDelegateId()));
		if (!GeneralUtils.isEmptyNumber(charityBoxTransferDTO.getNewCharityBoxId())) {
			detail.setNewCharityBox(new CharityBox(charityBoxTransferDTO.getNewCharityBoxId()));
		}
		detail.setNotes(charityBoxTransferDTO.getNotes());
		if (!GeneralUtils.isEmptyNumber(charityBoxTransferDTO.getSubLocationId())) {
			detail.setSubLocation(new SubLocation(charityBoxTransferDTO.getSubLocationId()));
			charityBoxTransfer.setSubLocation(detail.getSubLocation());
		}
		detail.setSupervisor(new Delegate(charityBoxTransferDTO.getDelegateId()));
		if (!GeneralUtils.isEmptyNumber(charityBoxTransferDTO.getSafetyCaseId()))
			detail.setSafetyCase(charityBoxTransferDTO.getSafetyCaseId());

		charityBoxTransfer.setCharityBoxTransferDetail(detail);
		detail.setCharityBoxTransfer(charityBoxTransfer);
		charityBoxTransfer.setSupervisor(detail.getSupervisor());
		charityBoxTransfer.setCreatedBy(detail.getCreatedBy());
		charityBoxTransfer.setNotes(detail.getNotes());

		return charityBoxTransfer;
	}

	public List<ProjectStudyDTO> convertProjectStudyToDTO(List<ProjectStudy> list) {
		List<ProjectStudyDTO> newList = new ArrayList<ProjectStudyDTO>(list.size());
		for (ProjectStudy projectStudy : list) {
			ProjectStudyDTO projectStudyDTO = new ProjectStudyDTO();
			projectStudyDTO.setId(projectStudy.getId().toString());
			projectStudyDTO.setName(projectStudy.getName());
			projectStudyDTO.setDescription(projectStudy.getDescription());
			projectStudyDTO.setCost(projectStudy.getCost());
			logger.info("####### processing projectStudy: " + projectStudy.getId() + ",typeId: "
					+ projectStudy.getProjectTypeId());
			if (projectStudy.getProjectTypeId() != null) {
				projectStudyDTO.setProjectCategoryId(projectStudy.getProjectTypeId().toString());
				NewProjectType newProjectType = utilsService
						.getNewProjectTypeFromCache(projectStudy.getProjectTypeId().toString());
				logger.info("####### processing projectStudy: " + projectStudy.getId() + ",NewProjectType: "
						+ newProjectType);
				projectStudyDTO.setProjectCategoryName(newProjectType.getName());
			}
			newList.add(projectStudyDTO);
		}
		return newList;
	}

	public List<DonatorDTO> convertDonatorToDTO(List<Donator> list) {
		List<DonatorDTO> newList = new ArrayList<DonatorDTO>(list.size());
		for (Donator donator : list) {
			DonatorDTO donatorDTO = new DonatorDTO();
			donatorDTO.setId(donator.getId().toString());
			donatorDTO.setName(donator.getName());
			donatorDTO.setMailBox(donator.getMailBox());
			donatorDTO.setMobile(donator.getMobile());
			newList.add(donatorDTO);
		}
		return newList;
	}

	public List<OldProjectDTO> getDonatorOldProjects(BigInteger donatorId) {
		List<OldProject> list = oldProjectRepository.getDonatorOldProjects(donatorId);
		return convertOldProjectToDTO(list);
	}

	public List<OldProjectDTO> getMostRecentProjects() {
		List<OldProject> list = oldProjectRepository.getMostRecentProjects();
		return convertOldProjectToDTO(list);
	}

	public List<OldProjectDTO> findOldProjectsByName(String name) {
		List<OldProject> list = oldProjectRepository.findTop10ByNameIgnoreCaseContainingOrderByNameAsc(name);
		return convertOldProjectToDTO(list);
	}

	private List<OldProjectDTO> convertOldProjectToDTO(List<OldProject> list) {
		List<OldProjectDTO> newList = new ArrayList<OldProjectDTO>(list.size());
		OldProjectDTO oldProjectDTO = null;
		for (OldProject oldProject : list) {
			oldProjectDTO = new OldProjectDTO(oldProject.getId().toString(), oldProject.getName());
			if (oldProject.getProjectCategoryId() != null) {
				NewProjectType newProjectType = utilsService
						.getNewProjectTypeFromCache(oldProject.getProjectCategoryId());
				oldProjectDTO.setCategoryName(newProjectType.getName());
			}
			if (oldProject.getCountryId() != null) {
				NewProjectCountry newProjectCountry = utilsService
						.getCountryFromCache(oldProject.getCountryId().toString());
				oldProjectDTO.setCountryName(newProjectCountry.getName());
			}

			newList.add(oldProjectDTO);

		}
		logger.info("########### oldProject: " + list);
		logger.info("########### newList: " + newList);
		return newList;
	}

	public List<OrphanDTO> convertOrphansToDTO(List<Orphan> orphansList) {
		List<OrphanDTO> list = new ArrayList<>(orphansList.size());

		for (Orphan orphan : orphansList) {

			list.add(new OrphanDTO(orphan.getId().toString(), orphan.getName(), orphan.getBirthDateStr(),
					orphan.getCaseNumber(), orphan.getGenderName()));
		}
		return list;
	}

	public Emarah findEmarahById(List<Emarah> list, BigInteger emarahId) {
		if (list == null)
			return null;
		for (Emarah emarah : list) {
			if (new BigInteger(emarah.getId()).compareTo(emarahId) == 0) {
				return emarah;
			}
		}
		return null;
	}

}
