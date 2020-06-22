package com.ihsan.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ihsan.constants.ErrorCodeEnum;
import com.ihsan.entities.BankCheque;
import com.ihsan.entities.BankTransfer;
import com.ihsan.entities.CouponType;
import com.ihsan.entities.Delegate;
import com.ihsan.entities.DelegateCoupon;
import com.ihsan.entities.Donator;
import com.ihsan.entities.NewProjectCountry;
import com.ihsan.entities.NewProjectType;
import com.ihsan.entities.ProjectStudy;
import com.ihsan.entities.Receipt;
import com.ihsan.entities.ReceiptDetail;
import com.ihsan.entities.UserToken;
import com.ihsan.enums.PermissionTypeEnum;
import com.ihsan.service.CacheService;
import com.ihsan.util.GeneralUtils;
import com.ihsan.webservice.dto.BankDTO;
import com.ihsan.webservice.dto.CouponReportDTO;
import com.ihsan.webservice.dto.CouponTypeDTO;
import com.ihsan.webservice.dto.DelegateDTO;
import com.ihsan.webservice.dto.DonatorDTO;
import com.ihsan.webservice.dto.GeneralResponseDTO;
import com.ihsan.webservice.dto.NewProjectTypeDTO;
import com.ihsan.webservice.dto.OldProjectDTO;
import com.ihsan.webservice.dto.ProjectStudyDTO;
import com.ihsan.webservice.dto.ReceiptDTO;
import com.ihsan.webservice.dto.ReceiptPrintDTO;
import com.ihsan.webservice.dto.ReceiptsReportDTO;
import com.ihsan.webservice.dto.ServiceResponse;
import com.ihsan.webservice.dto.SupervisorReportDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Component
@Path("/v1")
@Api("")
public class HAIService extends HAIServiceBase {

	private static final Logger logger = LoggerFactory.getLogger(HAIService.class);

	@Autowired
	private ServiceValidation validation;

	@Autowired
	private CacheService cacheService;

	@Value("${environment}")
	private String environmentStr;

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/login")
	@ApiOperation(value = "تسجيل الدخول")
	public ServiceResponse login(@HeaderParam("userName") String userName, @HeaderParam("password") String password,
			@HeaderParam("lang") String lang) throws Exception {
		try {
			Delegate delegate = delegateRepository.authenticate(userName, password);
			if (delegate == null) {
				return new ServiceResponse(ErrorCodeEnum.WRONG_CREDENTIALS, errorCodeRepository, lang);
			} else if (delegate.getStatus().equalsIgnoreCase("I")) {
				return new ServiceResponse(ErrorCodeEnum.USER_DISABLED, errorCodeRepository, lang);
			} else if (delegate.getExpiryDate().before(new Date())) {
				return new ServiceResponse(ErrorCodeEnum.USER_EXPIRED, errorCodeRepository, lang);
			}
			List<BigDecimal> permissionsList = delegateRepository.getDelegatePermissions(delegate.getId().toString());
			logger.info("########### delegateId: " + delegate.getId() + " HAS PERMISSIONS: " + permissionsList);
			if (permissionsList == null || permissionsList.size() == 0) {
				return new ServiceResponse(ErrorCodeEnum.USER_DISABLED, errorCodeRepository, lang);
			}
			UserToken token = new UserToken(delegate.getId());
			tokenRepository.save(token);
			delegate.setToken(token.getToken());
			DelegateDTO delegateDTO = convertDelegateToDTO(delegate);
			if (!delegateDTO.isAdmin()) {
				delegateDTO.setCharityBox(isUserHasPermission(permissionsList, PermissionTypeEnum.CHARITY_BOX));
				delegateDTO.setCoupon(isUserHasPermission(permissionsList, PermissionTypeEnum.COUPON));
				delegateDTO.setSponsorship(isUserHasPermission(permissionsList, PermissionTypeEnum.SPONSORSHIP));
				delegateDTO.setProject(isUserHasPermission(permissionsList, PermissionTypeEnum.PROJECT));
			} else {
				delegateDTO.setCharityBox(true);
				delegateDTO.setCoupon(true);
				delegateDTO.setSponsorship(true);
				delegateDTO.setProject(true);
			}
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, delegateDTO, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in login webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	private boolean isUserHasPermission(List<BigDecimal> permissionsList, PermissionTypeEnum permissionTypeEnum) {
		if (permissionsList == null || permissionsList.size() == 0)
			return false;
		for (BigDecimal permissionValue : permissionsList) {
			if (permissionValue.intValue() == permissionTypeEnum.getValue())
				return true;
		}
		return false;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/loginLocation")
	@ApiOperation(value = "تسجيل الدخول")
	public ServiceResponse loginLocation(@HeaderParam("userName") String userName,
			@HeaderParam("password") String password, @HeaderParam("latitude") String latitudeParam,
			@HeaderParam("longitude") String longitudeParam, @HeaderParam("lang") String lang) throws Exception {
		try {
			Delegate delegate = delegateRepository.authenticate(userName, password);
			if (delegate == null) {
				return new ServiceResponse(ErrorCodeEnum.WRONG_CREDENTIALS, errorCodeRepository, lang);
			} else if (delegate.getStatus().equalsIgnoreCase("I")) {
				return new ServiceResponse(ErrorCodeEnum.USER_DISABLED, errorCodeRepository, lang);
			} else if (delegate.getExpiryDate().before(new Date())) {
				return new ServiceResponse(ErrorCodeEnum.USER_EXPIRED, errorCodeRepository, lang);
			}
			List<BigDecimal> permissionsList = delegateRepository.getDelegatePermissions(delegate.getId().toString());
			logger.info("########### delegateId: " + delegate.getId() + " HAS PERMISSIONS: " + permissionsList);
			if (permissionsList == null || permissionsList.size() == 0) {
				return new ServiceResponse(ErrorCodeEnum.USER_DISABLED, errorCodeRepository, lang);
			}
			UserToken token = new UserToken(delegate.getId());
			tokenRepository.save(token);
			delegate.setToken(token.getToken());
			DelegateDTO delegateDTO = convertDelegateToDTO(delegate);
			if (!delegateDTO.isAdmin()) {
				delegateDTO.setCharityBox(isUserHasPermission(permissionsList, PermissionTypeEnum.CHARITY_BOX));
				delegateDTO.setCoupon(isUserHasPermission(permissionsList, PermissionTypeEnum.COUPON));
				delegateDTO.setSponsorship(isUserHasPermission(permissionsList, PermissionTypeEnum.SPONSORSHIP));
				delegateDTO.setProject(isUserHasPermission(permissionsList, PermissionTypeEnum.PROJECT));
			} else {
				delegateDTO.setCharityBox(true);
				delegateDTO.setCoupon(true);
				delegateDTO.setSponsorship(true);
				delegateDTO.setProject(true);
			}
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, delegateDTO, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in loginLocation webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/logout")
	@ApiOperation(value = "تسجيل خروج")
	public ServiceResponse logout(@HeaderParam("delegateId") String delegateId,
			@HeaderParam("latitude") String latitudeParam, @HeaderParam("longitude") String longitudeParam,
			@HeaderParam("token") String token, @HeaderParam("lang") String lang) throws Exception {
		try {

			GeneralResponseDTO generalResponseDTO = new GeneralResponseDTO();
			generalResponseDTO.setSuccess(true);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, generalResponseDTO, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in logout webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	// 100000000000000091
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/getCoupons/{delegateId}")
	@ApiOperation(value = "عرض انواع الكوبونات")
	public ServiceResponse getCoupons(@PathParam("delegateId") BigInteger delegateId,
			@HeaderParam("token") String token, @HeaderParam("lang") String lang) throws Exception {
		try {

			List<CouponType> couponsList = couponRepository.getCoupons();

			List<DelegateCoupon> favoritesList = delegateCouponRepository.findByDelegateId(delegateId);

			List<CouponTypeDTO> resultList = convertCouponListToDTO(couponsList);
			for (CouponTypeDTO couponTypeDTO : resultList) {
				for (DelegateCoupon delegateCoupon : favoritesList) {
					if (delegateCoupon.getCouponId().toString().equals(couponTypeDTO.getId())) {
						couponTypeDTO.setFavorite(true);
					}
				}
			}
			resultList.sort(Comparator.comparing(CouponTypeDTO::isFavorite).reversed());
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, resultList, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in getCoupons webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findCountryByName/{name}")
	@ApiOperation(value = "البحث عن الدولة بجزء من الإسم")
	public ServiceResponse findCountryByName(@PathParam("name") String name, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {

			List<NewProjectCountry> list = newProjectCountryRepository
					.findTop10ByNameIgnoreCaseContainingOrderByNameAsc(name);
			logger.info("###### findCountryByName,list: " + list.size());
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, list, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findCountryByName webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findCoupons/{name}")
	@ApiOperation(value = "البحث فى الكوبونات")
	public ServiceResponse findCoupons(@PathParam("name") String name, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {

			List<CouponType> couponsList = couponRepository.findByNameStartingWithAndStatusCode(name, 0);
			logger.info("###### findCoupons,couponsList: " + couponsList.size());
			List<CouponTypeDTO> resultList = convertCouponListToDTO(couponsList);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, resultList, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findCoupons webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findCouponByBarcode/{barcode}")
	@ApiOperation(value = "عرض تفاصيل الكوبون باستخدام الباركود")
	public ServiceResponse findCouponByBarcode(@PathParam("barcode") String barcode, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {
			CouponType couponType = utilsService.getCouponByBarcode(barcode);
			CouponTypeDTO couponTypeDTO = convertCouponToDTO(couponType);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, couponTypeDTO, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findCouponByBarcode webservice for barcode: " + barcode + ",Exception is: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/refreshApplicationCache")
	@ApiOperation(value = "تحديث الكاش ميمورى الخاص بالتطبيق واعادة تحميل جميع القيم اللوكاب من الداتا بيز ووضعها فى الكاش ميمورى مره أخرى")
	public ServiceResponse refreshApplicationCache(@QueryParam("attr1") String userName,
			@QueryParam("attr2") String password, @HeaderParam("lang") String lang) throws Exception {
		try {
			Delegate delegate = delegateRepository.authenticate(userName, password);
			if (delegate != null && delegate.getStatus().equalsIgnoreCase("A")) {
				cacheService.refreshAllCaches();
				return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, new GeneralResponseDTO(true),
						errorCodeRepository, lang);
			} else {
				return new ServiceResponse(ErrorCodeEnum.ACCESS_DENIED, errorCodeRepository, lang);
			}
		} catch (Exception e) {
			logger.error("Exception in refreshApplicationCache webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.WILDCARD)
	@Path("/getCouponImage/{couponId}")
	@ApiOperation(value = "تحميل صورة الكوبون")
	public Response getCouponImage(@PathParam("couponId") String couponId, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {

			// CouponType coupon = couponRepository.findOne(couponId);
			byte[] image = couponRepository.getImageById(couponId);
			String mimeType = "image/png";
			mimeType = StringUtils.isEmpty(mimeType) ? "image/*" : mimeType;
			String fileName = "Coupon_" + couponId;
			if (!StringUtils.isEmpty(mimeType)) {
				if (mimeType.toLowerCase().contains("png"))
					fileName += ".png";
				else if (mimeType.toLowerCase().contains("jpg") || mimeType.toLowerCase().contains("jpeg"))
					fileName += ".jpg";
				else
					fileName += ".image";
			}
			return Response.ok(image, mimeType)
					.header("Content-Disposition", "attachment; filename=\"" + fileName + "\"").build();
		} catch (Exception e) {
			logger.error("Exception in getCouponImage webservice: ", e);
			return Response.serverError().build();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/createReceipt")
	@ApiOperation(value = "انشاء ريسيت")
	public ServiceResponse createReceipt(ReceiptDTO receiptDTO, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {

			ErrorCodeEnum errorCode = validation.validateCreateReceipt(receiptDTO);
			if (isError(errorCode)) {
				logger.info("createReceipt Error " + errorCode.getErrorCode() + " , JSON: "
						+ (new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(receiptDTO)));
				return new ServiceResponse(errorCode, errorCodeRepository, lang);
			}

			utilsService.checkReceiptDetailsSequence();

			Receipt receipt = convertReceipDTOToReceipt(receiptDTO);
			receipt = utilsService.createReceipt(receipt);
			ReceiptPrintDTO receiptPrintDTO = getReceiptPrint(receipt, lang, true);
			boolean sendSMS = false;
			if (StringUtils.isNotBlank(environmentStr) && environmentStr.equalsIgnoreCase("Production")) {
				sendSMS = true;
			} else if (StringUtils.isNotBlank(receiptDTO.getDonatorPhoneNumber())
					&& receiptDTO.getDonatorPhoneNumber().equals("0566399945")) {
				sendSMS = true;
			}
			if (sendSMS) {
				if (StringUtils.isNotBlank(receiptDTO.getDonatorPhoneNumber())
						&& receiptDTO.getDonatorPhoneNumber().length() == 10) {
					sendSMS(receiptDTO.getDonatorPhoneNumber(), receiptPrintDTO.getTotalAmount().toString(),
							receipt.getNumber().toString());
				}
			}
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, receiptPrintDTO, errorCodeRepository, lang);
		} catch (DataIntegrityViolationException e) {
			logger.error("######## createReceipt Excception >>> DataIntegrityViolationException >>> retry");
			return createReceipt(receiptDTO, token, lang);
		} catch (Exception e) {
			logger.error("Exception in createReceipt webservice: ", e);
			logger.error("Exception in createReceipt webservice, JSON: "
					+ (new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(receiptDTO)));
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/changePassword")
	@ApiOperation(value = "تغيير كلمة المرور")
	public ServiceResponse changePassword(@HeaderParam("delegateId") BigInteger delegateId,
			@HeaderParam("oldPassword") String oldPassword, @HeaderParam("newPassword") String newPassword,
			@HeaderParam("token") String token, @HeaderParam("lang") String lang) throws Exception {
		try {

			Delegate delegate = delegateRepository.findOne(delegateId);
			if (StringUtils.isNotBlank(delegate.getPassword())) {
				if (!delegate.getPassword().equals(oldPassword)) {
					return new ServiceResponse(ErrorCodeEnum.OLD_PASSWORD_WRONG, errorCodeRepository, lang);
				}
			}
			delegate.setPassword(newPassword);
			delegateRepository.save(delegate);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, new GeneralResponseDTO(true), errorCodeRepository,
					lang);
		} catch (Exception e) {
			logger.error("Exception in changePassword webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/addToFavorites/{delegateId}/{couponId}")
	@ApiOperation(value = "اضافة كوبون الى المفضلة")
	public ServiceResponse addToFavorites(@PathParam("delegateId") String delegateId,
			@PathParam("couponId") String couponId, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {

			// we should make unique constraint on delegateId+couponId
			// also we should make check that coupoun is not already in favorites

			DelegateCoupon delegateCoupon = new DelegateCoupon(new BigInteger(delegateId), new BigInteger(couponId));
			delegateCouponRepository.save(delegateCoupon);
			logger.info("######## addFavorite,id: " + delegateCoupon.getId());
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, new GeneralResponseDTO(true), errorCodeRepository,
					lang);
		} catch (Exception e) {
			logger.error("Exception in addToFavorites webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/removeFromFavorites/{delegateId}/{couponId}")
	@ApiOperation(value = "إزالة كوبون من المفضلة")
	public ServiceResponse removeFromFavorites(@PathParam("delegateId") String delegateId,
			@PathParam("couponId") String couponId, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {
			delegateCouponRepository.deleteByDelegateIdAndCouponId(new BigInteger(delegateId),
					new BigInteger(couponId));
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, new GeneralResponseDTO(true), errorCodeRepository,
					lang);
		} catch (Exception e) {
			logger.error("Exception in removeFromFavorites webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findDelegateNotCollectedCoupons/{delegateId}/{fromDate}/{toDate}")
	@ApiOperation(value = "عرض الكوبونات الغير محصلة التى أنشأها مفوض فى فترة معينة")
	public ServiceResponse findDelegateNotCollectedCoupons(@PathParam("delegateId") BigInteger delegateId,
			@PathParam("fromDate") String fromDateStr, @PathParam("toDate") String toDateStr,
			@HeaderParam("token") String token, @HeaderParam("lang") String lang) throws Exception {
		try {

			Date fromDate = GeneralUtils.parseDate(fromDateStr);
			Date toDate = GeneralUtils.parseDate(toDateStr);
			toDate = DateUtils.setHours(toDate, 23);
			toDate = DateUtils.setMinutes(toDate, 59);
			toDate = DateUtils.setSeconds(toDate, 0);
			List<ReceiptDetail> list = receiptDetailsRepository
					.findByCreatedByIdAndReceiptCollectedAndCreationDateGreaterThanEqualAndCreationDateLessThanEqualOrderByIdAsc(
							delegateId, "N", fromDate, toDate);

			List<CouponReportDTO> resultList = convertReceiptDetailsToCouponReportDTO(list, lang);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, resultList, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findDelegatedReceipts webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findDelegateReceipts/{delegateId}/{fromDate}/{toDate}")
	@ApiOperation(value = "عرض الايصالات التى أنشأها مفوض فى فترة معينة")
	public ServiceResponse findDelegateReceipts(@PathParam("delegateId") BigInteger delegateId,
			@PathParam("fromDate") String fromDateStr, @PathParam("toDate") String toDateStr,
			@HeaderParam("token") String token, @HeaderParam("lang") String lang) throws Exception {
		try {

			Date fromDate = GeneralUtils.parseDate(fromDateStr);
			Date toDate = GeneralUtils.parseDate(toDateStr);
			toDate = DateUtils.setHours(toDate, 23);
			toDate = DateUtils.setMinutes(toDate, 59);
			toDate = DateUtils.setSeconds(toDate, 0);
			boolean notCollectedOnly = true;
			List<ReceiptDetail> list = new ArrayList<ReceiptDetail>();
			if (notCollectedOnly)
				list = receiptDetailsRepository
						.findByCreatedByIdAndReceiptCollectedAndCreationDateGreaterThanEqualAndCreationDateLessThanEqualOrderByIdAsc(
								delegateId, "N", fromDate, toDate);
			else
				list = receiptDetailsRepository
						.findByCreatedByIdAndCreationDateGreaterThanEqualAndCreationDateLessThanEqualOrderByIdAsc(
								delegateId, fromDate, toDate);

			ReceiptsReportDTO receiptsReportDTO = convertReceiptDetailsToReceiptsReportDTO(list, lang);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, receiptsReportDTO, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findDelegatedReceipts webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findDelegateNotCollectedReceipts/{delegateId}")
	@ApiOperation(value = "عرض المبالغ الغير محصلة عند المندوب")
	public ServiceResponse findDelegateNotCollectedReceipts(@PathParam("delegateId") BigInteger delegateId,
			@HeaderParam("token") String token, @HeaderParam("lang") String lang) throws Exception {
		try {

			List<Receipt> list = receiptRepository.findByCollectedAndCreatedById("N", delegateId);
			SupervisorReportDTO supervisorReportDTO = convertReceiptListToSupervisorReport(list);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, supervisorReportDTO, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findDelegatedNotCollectedReceipts webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findReceiptDetails/{delegateId}/{receiptId}")
	@ApiOperation(value = "عرض تفاصيل ايصال منشأ بواسطة مفوض")
	public ServiceResponse findReceiptDetails(@PathParam("delegateId") BigInteger delegateId,
			@PathParam("receiptId") BigInteger receiptId, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {
			List<Receipt> receiptsList = receiptRepository.findByNumberAndCreatedById(String.valueOf(receiptId),
					delegateId);
			Receipt receipt = null;
			if (receiptsList != null && receiptsList.size() > 0)
				receipt = receiptsList.get(0);
			ReceiptPrintDTO receiptPrintDTO = getReceiptPrint(receipt, lang, false);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, receiptPrintDTO, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findReceiptDetails webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findDelegateLatestReceipt/{delegateId}")
	@ApiOperation(value = "عرض تفاصيل أخر ايصال منشأ بواسطة مفوض")
	public ServiceResponse findDelegateLatestReceipt(@PathParam("delegateId") BigInteger delegateId,
			@HeaderParam("token") String token, @HeaderParam("lang") String lang) throws Exception {
		try {

			Receipt receipt = receiptRepository.findTop1ByCreatedByIdOrderByIdDesc(delegateId);
			ReceiptPrintDTO receiptPrintDTO = getReceiptPrint(receipt, lang, false);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, receiptPrintDTO, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findReceiptDetails webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findDonator/{searchText}")
	@ApiOperation(value = "البحث عن المتبرع بجزء من الإسم او رقم الجوال يبدء ب او رقم صندوق البريد يبدء ب")
	public ServiceResponse findDonator(@PathParam("searchText") String searchText, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {

			List<Donator> donatorsList = new ArrayList<Donator>();
			if (StringUtils.isNotBlank(searchText)) {
				searchText = searchText.trim();
				if (StringUtils.isAlpha(searchText) || StringUtils.containsWhitespace(searchText)) {
					donatorsList = sponsorRepository.findTop50ByNameIgnoreCaseContainingOrderByNameAsc(searchText);
				} else if (StringUtils.isNumeric(searchText)) {
					donatorsList = sponsorRepository.findByMobileOrMailBoxOrAccountNumber(searchText,
							new PageRequest(0, 50, new Sort(Sort.Direction.ASC, "name")));
				} else if (searchText.contains("@")) {
					donatorsList = sponsorRepository.findTop50ByEmailIgnoreCaseContainingOrderByNameAsc(searchText);
				}
			} else {
				donatorsList = sponsorRepository.findTop50ByNameNotNullOrderByNameAsc();
			}
			logger.info("###### findDonator,donatorsList: " + donatorsList.size());
			List<DonatorDTO> resultList = convertDonatorToDTO(donatorsList);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, resultList, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findDonator webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findDonatorOldProjects/{id}")
	@ApiOperation(value = "عرض المشاريع القديمة لمتبرع معين")
	public ServiceResponse findDonatorOldProjects(@PathParam("id") BigInteger id, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {

			List<OldProjectDTO> list = getDonatorOldProjects(id);
			logger.info("###### oldProjectsList: " + list.size());
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, list, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findDonatorOldProjects webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findNewProjectCountries/{newProjectTypeId}")
	@ApiOperation(value = "عرض الدول المتاح فيها نوع المشروع")
	public ServiceResponse findNewProjectCountries(@PathParam("newProjectTypeId") long newProjectTypeId,
			@HeaderParam("token") String token, @HeaderParam("lang") String lang) throws Exception {
		try {

			List<NewProjectCountry> countriesList = newProjectCountryRepository.getProjectCountries(newProjectTypeId);
			logger.info("###### findNewProjectCountries,list: " + countriesList.size());
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, countriesList, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findNewProjectCountries webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findOldProjects/{name}")
	@ApiOperation(value = "البحث فى المشاريع القديمة بجزء من الإسم")
	public ServiceResponse findOldProjects(@PathParam("name") String name, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {

			List<OldProjectDTO> list = findOldProjectsByName(name);
			logger.info("###### oldProjectsList: " + list.size());
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, list, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findOldProjects webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findProjectStudy/{countryId}/{projectCategoryId}/{name}")
	@ApiOperation(value = "البحث عن دراسة مشروع فى دولة معينة لتصنيف مشاريع معين بجزء من إسم الدراسة")
	public ServiceResponse findProjectStudy(@PathParam("countryId") String countryId,
			@PathParam("projectCategoryId") BigInteger projectTypeId, @PathParam("name") String name,
			@HeaderParam("token") String token, @HeaderParam("lang") String lang) throws Exception {
		try {
			List<ProjectStudy> projectStudyList = new ArrayList<ProjectStudy>();
			if (StringUtils.isNotBlank(name)) {
				projectStudyList = projectStudyRepository
						.findTop10ByCountryIdAndProjectTypeIdAndNameIgnoreCaseContainingOrderByNameAsc(countryId,
								projectTypeId, name);
			} else {
				projectStudyList = projectStudyRepository.findTop10ByCountryIdAndProjectTypeIdOrderByNameAsc(countryId,
						projectTypeId);
			}
			logger.info("###### projectStudyList: " + projectStudyList.size());
			List<ProjectStudyDTO> resultList = convertProjectStudyToDTO(projectStudyList);
			logger.info("###### resultList: " + resultList);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, resultList, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findProjectStudy webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.WILDCARD)
	@Path("/getNewProjectTypeImage/{newProjectTypeId}")
	@ApiOperation(value = "تحميل صورة نوع المشروع")
	public Response getNewProjectTypeImage(@PathParam("newProjectTypeId") BigInteger newProjectTypeId,
			@HeaderParam("token") String token, @HeaderParam("lang") String lang) throws Exception {
		try {

			// CouponType coupon = couponRepository.findOne(couponId);
			byte[] image = newProjectTypeRepository.getImageById(newProjectTypeId);
			String mimeType = "image/png";
			mimeType = StringUtils.isEmpty(mimeType) ? "image/*" : mimeType;
			String fileName = "NewProjectType_" + newProjectTypeId;
			if (!StringUtils.isEmpty(mimeType)) {
				if (mimeType.toLowerCase().contains("png"))
					fileName += ".png";
				else if (mimeType.toLowerCase().contains("jpg") || mimeType.toLowerCase().contains("jpeg"))
					fileName += ".jpg";
				else
					fileName += ".image";
			}
			return Response.ok(image, mimeType)
					.header("Content-Disposition", "attachment; filename=\"" + fileName + "\"").build();
		} catch (Exception e) {
			logger.error("Exception in getNewProjectTypeImage webservice: ", e);
			return Response.serverError().build();
		}
	}

	@GET
	@Produces(MediaType.WILDCARD)
	@Path("/getNewProjectCountryImage/{countryId}")
	@ApiOperation(value = "تحميل صورة الدولة الخاصة بنوع المشروع")
	public Response getNewProjectCountryImage(@PathParam("countryId") String countryId,
			@HeaderParam("token") String token, @HeaderParam("lang") String lang) throws Exception {
		try {

			byte[] image = newProjectCountryRepository.getImageById(countryId);
			String mimeType = "image/png";
			mimeType = StringUtils.isEmpty(mimeType) ? "image/*" : mimeType;
			String fileName = "NewProjectCountry_" + countryId;
			if (!StringUtils.isEmpty(mimeType)) {
				if (mimeType.toLowerCase().contains("png"))
					fileName += ".png";
				else if (mimeType.toLowerCase().contains("jpg") || mimeType.toLowerCase().contains("jpeg"))
					fileName += ".jpg";
				else
					fileName += ".image";
			}
			return Response.ok(image, mimeType)
					.header("Content-Disposition", "attachment; filename=\"" + fileName + "\"").build();
		} catch (Exception e) {
			logger.error("Exception in getNewProjectCountryImage webservice: ", e);
			return Response.serverError().build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/getNewProjectTypes")
	@ApiOperation(value = "عرض انواع المشاريع الجديدة")
	public ServiceResponse getNewProjectTypes(@HeaderParam("token") String token, @HeaderParam("lang") String lang)
			throws Exception {
		try {

			List<NewProjectType> list = newProjectTypeRepository.getNewProjectTypes();
			logger.info("###### getNewProjectTypes,list: " + list.size());
			List<NewProjectTypeDTO> resultList = convertNewProjectTypeToDTO(list);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, resultList, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in getNewProjectTypes webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/getBanksForCheque")
	@ApiOperation(value = "عرض البنوك الخاصة بالشيكات")
	public ServiceResponse getBanksForCheque(@HeaderParam("token") String token, @HeaderParam("lang") String lang)
			throws Exception {
		try {

			List<BankCheque> banksList = bankChequeRepository.getBanks();
			List<BankDTO> newList = convertBankChequeListToDTO(banksList, lang);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, newList, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in getBanksForCheque webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/getBanksForTransfer")
	@ApiOperation(value = "عرض البنوك الخاصة بالتحويل البنكي")
	public ServiceResponse getBanksForTransfer(@HeaderParam("token") String token, @HeaderParam("lang") String lang)
			throws Exception {
		try {

			List<BankTransfer> banksList = bankTransferRepository.findAll();
			List<BankDTO> newList = convertBankTransferListToDTO(banksList, lang);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, newList, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in getBanksForTransfer webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findDelegateTotalAmount/{delegateId}")
	@ApiOperation(value = "عرض رصيد المندوب")
	public ServiceResponse findDelegateTotalAmount(@PathParam("delegateId") BigInteger delegateId,
			@HeaderParam("token") String token, @HeaderParam("lang") String lang) throws Exception {
		try {

			BigDecimal amount = receiptRepository.getDelegateTotalAmount(delegateId);
			if (amount == null) {
				amount = new BigDecimal(0);
			}
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, amount, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findDelegateTotalAmount webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@Async
	public String sendSMS(String mobileNumber, String totalDonatedAmount, String receiptNumber) {

		mobileNumber = "971" + mobileNumber.substring(1);
		HttpURLConnection connection = null;
		BufferedReader bufferedReader = null;
		String result = null;
		try {

			StringBuffer messageTextStringBuffer = new StringBuffer();
			messageTextStringBuffer.append("نشكركم على تبرعكم بمبلغ").append(" ").append(totalDonatedAmount).append(" ")
					.append("درهم").append("\n");
			messageTextStringBuffer.append("رقم المعاملة : ").append(receiptNumber).append("\n");
			messageTextStringBuffer.append("مع تحيات جمعية الاحسان الخيرية").append("\n");
			messageTextStringBuffer.append("www.alihsan.ae");
			String messageTextStr = URLEncoder.encode(messageTextStringBuffer.toString(), "UTF-8");
			// http://fuj.smscharity.net:9980/smsgw.aspx?user=fuj191&pass=Passw0rd191$&ProviderID=1019&text=%D8%AA%D8%AC%D8%B1%D8%A8%D8%A9&msisdn=971504339373&encoding=2
			String userName = "sms_pos";
			String password = "0fKu9H5ZaGgImlUN";
			String smsURL = "http://alihsan.smscharity.net:9980/nlsmsgw.aspx?user=" + userName + "&pass=" + password
					+ "&ProviderID=1013&text=" + messageTextStr + "&msisdn=" + mobileNumber + "&encoding=1";
			URL url = new URL(smsURL);
			logger.info("####### sending sms to url: " + smsURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(1000 * 30);
			connection.setReadTimeout(1000 * 30);
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.connect();
			bufferedReader = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
			String line;
			StringBuffer sb = new StringBuffer();
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line).append("\n");
			}
			result = sb.toString();
			boolean success = false;
			if (StringUtils.isNotBlank(result) && result.trim().toLowerCase().contains("ok")) {
				success = true;
			} else {
				logger.info("########## UNABLE TO SEND SMS,RESULT IS: " + result);
			}
		} catch (Exception e) {
			logger.error("Exception in sendSMS: ", e);
		} finally {
			if (connection != null)
				connection.disconnect();
			if (bufferedReader != null)
				try {
					bufferedReader.close();
				} catch (IOException e) {
					logger.error("Exception in sendSMS: ", e);
				}
		}
		return result;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/getDelegates/{supervisorId}")
	@ApiOperation(value = "عرض المحصلين التابعين لمشرف معين")
	public ServiceResponse getDelegates(@HeaderParam("token") String token,
			@PathParam("supervisorId") BigInteger supervisorId, @HeaderParam("lang") String lang) throws Exception {
		try {

			List<Delegate> delegateList = delegateRepository.getSupervisorDelegates(supervisorId);
			List<DelegateDTO> resultList = convertDelegateListToDTO(delegateList);
			for (DelegateDTO delegateDTO : resultList) {
				delegateDTO.setAmountNotCollected(
						receiptRepository.getDelegateTotalAmount(new BigInteger(delegateDTO.getId())));
			}
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, resultList, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in getDelegates webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/collectMoneyFromDelegate")
	@ApiOperation(value = "تحصيل من المندوب")
	public ServiceResponse collectMoneyFromDelegate(@HeaderParam("delegateId") BigInteger delegateId,
			@HeaderParam("supervisorId") BigInteger supervisorId, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {
			List<Receipt> list = utilsService.loadDelegateNotCollectedReceipts(delegateId);
			SupervisorReportDTO supervisorReportDTO = convertReceiptListToSupervisorReport(list);
			utilsService.collectMoneyFromDelegate(delegateId, supervisorId, list);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, supervisorReportDTO, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in collectMoneyFromDelegate webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, "خطأ فى النظام : " + e.getMessage());
		}
	}

}