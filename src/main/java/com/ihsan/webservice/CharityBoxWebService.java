package com.ihsan.webservice;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ihsan.constants.ErrorCodeEnum;
import com.ihsan.entities.Delegate;
import com.ihsan.entities.UserToken;
import com.ihsan.entities.charityBoxes.Attachment;
import com.ihsan.entities.charityBoxes.CharityBox;
import com.ihsan.entities.charityBoxes.CharityBoxStatus;
import com.ihsan.entities.charityBoxes.CharityBoxTransfer;
import com.ihsan.entities.charityBoxes.CharityBoxTransferDetail;
import com.ihsan.entities.charityBoxes.Emarah;
import com.ihsan.entities.charityBoxes.Location;
import com.ihsan.entities.charityBoxes.Region;
import com.ihsan.entities.charityBoxes.RouteDetail;
import com.ihsan.entities.charityBoxes.SafetyCase;
import com.ihsan.entities.charityBoxes.SubLocation;
import com.ihsan.enums.CharityBoxActionTypeEnum;
import com.ihsan.enums.CharityBoxStatusEnum;
import com.ihsan.util.CustomFileUtils;
import com.ihsan.util.GeneralUtils;
import com.ihsan.webservice.dto.GeneralResponseDTO;
import com.ihsan.webservice.dto.ServiceResponse;
import com.ihsan.webservice.dto.TransactionDTO;
import com.ihsan.webservice.dto.charityBox.BarcodeDTO;
import com.ihsan.webservice.dto.charityBox.CharityBoxDTO;
import com.ihsan.webservice.dto.charityBox.CharityBoxTransferDTO;
import com.ihsan.webservice.dto.charityBox.DocumentDTO;
import com.ihsan.webservice.dto.charityBox.LocationDTO;
import com.ihsan.webservice.dto.charityBox.RegionDTO;
import com.ihsan.webservice.dto.charityBox.SubLocationDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Component
@Path("/v1/charitybox")
@Api("")
public class CharityBoxWebService extends HAIServiceBase {

	@Value("${charityBoxAttachmentsPath}")
	private String charityBoxAttachmentsPath;

	private boolean oneCharityBoxPerSubLocation = false;

	private static final Logger logger = LoggerFactory.getLogger(CharityBoxWebService.class);

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findCharityBox")
	@ApiOperation(value = "البحث عن حصالة بواسطة الباركود")
	public ServiceResponse findCharityBox(BarcodeDTO barcodeDTO, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {

			CharityBox charityBox = null;
			if (StringUtils.isNotBlank(barcodeDTO.getBarcode()))
				charityBox = charityBoxRepository.findByBarcode(barcodeDTO.getBarcode());
			else if (StringUtils.isNotBlank(barcodeDTO.getNumber()))
				charityBox = charityBoxRepository.findByNumberIgnoreCase(barcodeDTO.getNumber());
			if (charityBox == null) {
				if (StringUtils.isNotBlank(barcodeDTO.getBarcode()))
					return new ServiceResponse(ErrorCodeEnum.BARCODE_NOT_EXIST, errorCodeRepository, lang);
				else if (StringUtils.isNotBlank(barcodeDTO.getNumber()))
					return new ServiceResponse(ErrorCodeEnum.CHARITYBOX_NUMBER_NOT_EXIST, errorCodeRepository, lang);
			}

			if (barcodeDTO.getActionType().getValue().equals(CharityBoxActionTypeEnum.INSERT.getValue())) {
				if (!charityBox.getStatusId().equals(CharityBoxStatusEnum.NOT_ACTIVE.getValue())) {
					return new ServiceResponse(ErrorCodeEnum.CHARITY_BOX_IN_USE, errorCodeRepository, lang);
				}
				if (barcodeDTO.isRoute()) {
					if (charityBoxRepository.countByCategoryIdAndSubLocationIdAndStatusIdNot(charityBox.getCategoryId(),
							barcodeDTO.getSubLocationId(), CharityBoxStatusEnum.NOT_ACTIVE.getValue()) > 0) {
						return new ServiceResponse(ErrorCodeEnum.CHARITY_BOX_SAME_SOURCE_EXISTS, errorCodeRepository,
								lang);
					}
				}
			}
			CharityBoxDTO charityBoxDTO = convertCharityBoxToDTO(charityBox);
			logger.info("###### findCharityBox,charityBoxDTO: " + charityBoxDTO);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, charityBoxDTO, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findCharityBox webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findSafetyCase/{barcode}")
	@ApiOperation(value = "البحث عن الكيس بواسطة الباركود")
	public ServiceResponse findSafetyCase(@PathParam("barcode") String barcode, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {

			SafetyCase safetyCase = safetyCaseRepository.findByBarcode(barcode);
			logger.info("###### findSafetyCase,safetyCase: " + safetyCase);
			if (safetyCase == null) {
				return new ServiceResponse(ErrorCodeEnum.WRONG_SAFETY_CASE_BARCODE, "لا يمكن العثور على بيانات الكيس",
						lang);
			}
			if (safetyCase.getStatus().equalsIgnoreCase("Y")) {
				return new ServiceResponse(ErrorCodeEnum.SAFETY_CASE_ACTIVE, "لا يمكن استخدام الكيس لأن حالته فعال",
						lang);
			}
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, safetyCase, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findSafetyCase webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findRegion/{emarahId}/{name}")
	@ApiOperation(value = "البحث عن منطقة بجزء من الاسم")
	public ServiceResponse findRegion(@PathParam("emarahId") BigInteger emarahId, @PathParam("name") String name,
			@HeaderParam("token") String token, @HeaderParam("lang") String lang) throws Exception {
		try {
			logger.info("###### emarahId: " + emarahId + ",name: " + name);
			List<Region> regionsList = new ArrayList<Region>();
			if (StringUtils.isNotBlank(name) && !name.equalsIgnoreCase("-1")) {
				// Pageable page = new PageRequest(0, 10, new Sort(Sort.Direction.ASC, "name"));
				regionsList = regionRepository.findTop10ByEmarahIdAndNameIgnoreCaseContainingOrderByNameAsc(emarahId,
						name.trim());
			} else {
				regionsList = regionRepository.findTop100ByEmarahIdOrderByNameAsc(emarahId);
			}
			logger.info("###### regionsList: " + regionsList.size());
			List<RegionDTO> resultList = convertRegionToDTO(regionsList);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, resultList, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findRegion webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findLocation/{regionId}/{name}")
	@ApiOperation(value = "البحث عن موقع بجزء من الاسم")
	public ServiceResponse findLocation(@PathParam("regionId") BigInteger regionId, @PathParam("name") String name,
			@HeaderParam("token") String token, @HeaderParam("lang") String lang) throws Exception {
		try {
			List<Location> locationsList = new ArrayList<Location>();
			if (GeneralUtils.isBigIntegerGreaterThanZero(regionId)) {
				if (StringUtils.isNotBlank(name) && !name.equalsIgnoreCase("-1")) {
					locationsList = locationRepository
							.findTop10ByRegionIdAndNameIgnoreCaseContainingOrderByNameAsc(regionId, name.trim());
				} else {
					locationsList = locationRepository.findTop500ByRegionIdOrderByNameAsc(regionId);
				}
			} else {
				locationsList = locationRepository.findTop500ByOrderByNameAsc();
			}
			logger.info("###### locationsList: " + locationsList.size());
			List<LocationDTO> resultList = convertLocationToDTO(locationsList);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, resultList, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findLocation webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findSubLocation/{emarahId}/{regionId}/{locationId}/{name}")
	@ApiOperation(value = "البحث عن الموقع الفرعى بجزء من الاسم")
	public ServiceResponse findSubLocation(@PathParam("emarahId") BigInteger emarahId,
			@PathParam("regionId") BigInteger regionId, @PathParam("locationId") BigInteger locationId,
			@PathParam("name") String name, @HeaderParam("token") String token, @HeaderParam("lang") String lang)
			throws Exception {
		try {
			logger.info("###### findSubLocation,emarahId: " + emarahId + ",regionId: " + regionId + ",locationId: "
					+ locationId + ",name: " + name);
			List<SubLocation> subLocationsList = new ArrayList<SubLocation>();
			boolean concatDetails = false;
			if (GeneralUtils.isBigIntegerGreaterThanZero(locationId)) {
				subLocationsList = subLocationRepository
						.findTop10ByLocationIdAndNameIgnoreCaseContainingOrderByNameAsc(locationId, name.trim());
			} else if (GeneralUtils.isBigIntegerGreaterThanZero(regionId)) {
				subLocationsList = subLocationRepository
						.findTop10ByLocationRegionIdAndNameIgnoreCaseContainingOrderByNameAsc(regionId, name.trim());
			} else if (GeneralUtils.isBigIntegerGreaterThanZero(emarahId)) {
				subLocationsList = subLocationRepository
						.findTop10ByLocationRegionEmarahIdAndNameIgnoreCaseContainingOrderByNameAsc(emarahId,
								name.trim());
			} else {
				concatDetails = true;
				subLocationsList = subLocationRepository.findTop10ByNameIgnoreCaseContainingOrderByNameAsc(name);
			}
			logger.info("###### findSubLocation,subLocationsList: " + subLocationsList.size());
			concatDetails = false;
			List<SubLocationDTO> resultList = convertSubLocationListToDTO(subLocationsList, concatDetails);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, resultList, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findSubLocation webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findSubLocation/{locationId}/{name}")
	@ApiOperation(value = "البحث عن مكان بجزء من الاسم")
	public ServiceResponse findSubLocation(@PathParam("locationId") BigInteger locationId,
			@PathParam("name") String name, @HeaderParam("token") String token, @HeaderParam("lang") String lang)
			throws Exception {
		try {

			List<SubLocation> subLocationsList = new ArrayList<SubLocation>();
			boolean concatDetails = false;
			if (GeneralUtils.isBigIntegerGreaterThanZero(locationId)) {
				if (StringUtils.isNotBlank(name) && !name.equalsIgnoreCase("-1")) {
					subLocationsList = subLocationRepository
							.findTop10ByLocationIdAndNameIgnoreCaseContainingOrderByNameAsc(locationId, name.trim());
				} else {
					subLocationsList = subLocationRepository.findTop500ByLocationIdOrderByNameAsc(locationId);
				}
			} else {
				concatDetails = true;
				if (StringUtils.isNotBlank(name) && !name.equalsIgnoreCase("-1"))
					subLocationsList = subLocationRepository.findTop10ByNameIgnoreCaseContainingOrderByNameAsc(name);
				else
					subLocationsList = subLocationRepository.findTop500ByOrderByNameAsc();
			}
			logger.info("###### findSubLocation,subLocationsList: " + subLocationsList.size());
			concatDetails = false;
			List<SubLocationDTO> resultList = convertSubLocationListToDTO(subLocationsList, concatDetails);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, resultList, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findSubLocation webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findInsertSubLocation/{emarahId}/{regionId}/{locationId}/{sourceId}/{name}")
	@ApiOperation(value = "البحث عن مكان بجزء من الاسم لعملية زرع الحصالة")
	public ServiceResponse findInsertSubLocation(@PathParam("emarahId") BigInteger emarahId,
			@PathParam("regionId") BigInteger regionId, @PathParam("locationId") BigInteger locationId,
			@PathParam("sourceId") String sourceId, @PathParam("name") String name, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {

			List<SubLocation> subLocationsList = new ArrayList<SubLocation>();
			if (StringUtils.isNotBlank(name) && !name.equalsIgnoreCase("ALL")) {
				if (oneCharityBoxPerSubLocation) {
					if (GeneralUtils.isBigIntegerGreaterThanZero(locationId))
						subLocationsList = subLocationRepository
								.findTop10ByLocationIdAndCategoryIdAndNameForInsertSingle(locationId, sourceId, name);
					else if (GeneralUtils.isBigIntegerGreaterThanZero(regionId))
						subLocationsList = subLocationRepository
								.findTop10ByLocationRegionIdAndCategoryIdAndNameForInsertSingle(regionId, sourceId,
										name);
					else if (GeneralUtils.isBigIntegerGreaterThanZero(emarahId))
						subLocationsList = subLocationRepository
								.findTop10ByLocationRegionEmarahIdAndCategoryIdAndNameForInsertSingle(emarahId,
										sourceId, name);
				} else {
					if (GeneralUtils.isBigIntegerGreaterThanZero(locationId))
						subLocationsList = subLocationRepository
								.findTop10ByLocationIdAndCategoryIdAndNameForInsertMultiple(locationId, name);
					else if (GeneralUtils.isBigIntegerGreaterThanZero(regionId))
						subLocationsList = subLocationRepository
								.findTop10ByLocationRegionIdAndCategoryIdAndNameForInsertMultiple(regionId, name);
					else if (GeneralUtils.isBigIntegerGreaterThanZero(emarahId))
						subLocationsList = subLocationRepository
								.findTop10ByLocationRegionEmarahIdAndCategoryIdAndNameForInsertMultiple(emarahId, name);
				}
			} else {
				if (oneCharityBoxPerSubLocation) {
					if (GeneralUtils.isBigIntegerGreaterThanZero(locationId))
						subLocationsList = subLocationRepository
								.findTop500ByLocationIdAndCategoryIdForInsertSingle(locationId, sourceId);
					else if (GeneralUtils.isBigIntegerGreaterThanZero(regionId))
						subLocationsList = subLocationRepository
								.findTop500ByLocationRegionIdAndCategoryIdForInsertSingle(regionId, sourceId);
					else if (GeneralUtils.isBigIntegerGreaterThanZero(emarahId))
						subLocationsList = subLocationRepository
								.findTop500ByLocationRegionEmarahIdAndCategoryIdForInsertSingle(emarahId, sourceId);
				} else {
					if (GeneralUtils.isBigIntegerGreaterThanZero(locationId))
						subLocationsList = subLocationRepository
								.findTop500ByLocationIdAndCategoryIdForInsertMultiple(locationId);
					else if (GeneralUtils.isBigIntegerGreaterThanZero(regionId))
						subLocationsList = subLocationRepository
								.findTop500ByLocationRegionIdAndCategoryIdForInsertMultiple(regionId);
					else if (GeneralUtils.isBigIntegerGreaterThanZero(emarahId))
						subLocationsList = subLocationRepository
								.findTop500ByLocationRegionEmarahIdAndCategoryIdForInsertMultiple(emarahId);
				}
			}
			logger.info("###### findInsertSubLocation,subLocationsList: " + subLocationsList.size() + ",locationId: "
					+ locationId + ",sourceId: " + sourceId + ",name: " + name);
			List<SubLocationDTO> resultList = convertSubLocationListToDTO(subLocationsList, false);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, resultList, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findInsertSubLocation webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findSubLocationLatestOperation/{subLocationId}")
	@ApiOperation(value = "عرض آخر عملية تمت فى الموقع الفرعي")
	public ServiceResponse findSubLocationLatestOperation(@PathParam("subLocationId") BigInteger subLocationId,
			@HeaderParam("token") String token, @HeaderParam("lang") String lang) throws Exception {
		try {

			StringBuffer sb = new StringBuffer("");
			CharityBoxTransferDetail charityBoxTransferDetail = charityBoxTransferDetailRepository
					.findTop1BySubLocationIdOrderByIdDesc(subLocationId);
			logger.info("########### findSubLocationLatestOperation for subLocationId: " + subLocationId + ",result: "
					+ charityBoxTransferDetail);
			if (charityBoxTransferDetail != null && charityBoxTransferDetail.getActionType() != null) {
				String actionTypeDesc = "";
				actionTypeDesc = charityBoxTransferDetail.getActionType().getName();
				sb.append("نوع العملية : ").append(actionTypeDesc).append("\n");
				if (charityBoxTransferDetail.getCreatedBy() != null) {
					try {
						sb.append("تمت بواسطة : ").append(charityBoxTransferDetail.getCreatedBy().getName())
								.append("\n");
					} catch (Exception e) {
						logger.error(
								"Exception in findSubLocationCharityBoxes webservice for charityBoxTransferDetailId: "
										+ charityBoxTransferDetail.getId() + ",unable to find delegate with id: "
										+ charityBoxTransferDetail.getCreatedBy().getId(),
								e);
					}
				}
				sb.append("وقت العملية : ")
						.append(GeneralUtils.formatDateTime(charityBoxTransferDetail.getCreationDate(), "en"))
						.append("\n");
				if (charityBoxTransferDetail.getActionType().getId()
						.equals(CharityBoxActionTypeEnum.CHECK.getValue())) {
					if (charityBoxTransferDetail.isSubLocationTemporaryClosed())
						sb.append("المكان مغلق مؤقتا").append("\n");
				}

				if (StringUtils.isNotBlank(charityBoxTransferDetail.getNotes())) {
					sb.append("ملاحظات : ").append(charityBoxTransferDetail.getNotes().trim()).append("\n");
				}

			} else {
				sb.append("لا يوجد عمليات");
			}
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, new TransactionDTO(sb.toString()),
					errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findSubLocationCharityBoxes webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findSubLocationCharityBoxes/{subLocationId}")
	@ApiOperation(value = "عرض حصالات المكان")
	public ServiceResponse findSubLocationCharityBoxes(@PathParam("subLocationId") BigInteger subLocationId,
			@HeaderParam("token") String token, @HeaderParam("lang") String lang) throws Exception {
		try {

			List<CharityBox> boxesList = charityBoxRepository.findBySubLocationIdAndStatusId(subLocationId,
					CharityBoxStatusEnum.ACTIVE.getValue());
			List<CharityBoxDTO> resultList = convertCharityBoxListToDTO(boxesList);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, resultList, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findSubLocationCharityBoxes webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/createCharityBoxTransfer")
	@ApiOperation(value = "انشاء عملية للحصالة")
	// @Transactional(rollbackFor = Exception.class)
	public ServiceResponse createCharityBoxTransfer(CharityBoxTransferDTO charityBoxTransferDTO,
			@HeaderParam("token") String token, @HeaderParam("lang") String lang) throws Exception {

		String actionType = "";
		try {

			// التحقق من انه لا يوجد مكان لنفس الحى مضاف مسبقا
			// وذلك لمنع زرع اكثر من حصالة فى نفس المكان

			if (debugEnabled) {
				logger.info("###### createCharityBoxTransfer,JSON: " + (new ObjectMapper()
						.writerWithDefaultPrettyPrinter().writeValueAsString(charityBoxTransferDTO)));
			}

			actionType = charityBoxTransferDTO.getActionType().getValue();
			if (actionType.equals(CharityBoxActionTypeEnum.COLLECT.getValue())
					|| actionType.equals(CharityBoxActionTypeEnum.WITHDRAWAL.getValue())
					|| actionType.equals(CharityBoxActionTypeEnum.COLLECTED_MONEY.getValue())) {
				CharityBox charityBox = charityBoxRepository.findOne(charityBoxTransferDTO.getCharityBoxId());
				if (charityBox == null)
					return new ServiceResponse(ErrorCodeEnum.CHARITYBOX_NOT_EXIST_IN_DB, errorCodeRepository, lang);
				if (!charityBox.getStatusId().equals(CharityBoxStatusEnum.ACTIVE.getValue())) {
					ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.ONLY_ACTIVE_CHARITYBOX_CAN_BE_COLLECTED;
					if (actionType.equals(CharityBoxActionTypeEnum.WITHDRAWAL.getValue())) {
						errorCodeEnum = ErrorCodeEnum.ONLY_ACTIVE_CHARITYBOX_CAN_BE_REMOVED;
					}
					return new ServiceResponse(errorCodeEnum, errorCodeRepository, lang);
				}
			}

			if (oneCharityBoxPerSubLocation) {
				if (actionType.equals(CharityBoxActionTypeEnum.INSERT.getValue())
						&& GeneralUtils.isBigIntegerGreaterThanZero(charityBoxTransferDTO.getSubLocationId())) {
					List<CharityBox> boxesList = charityBoxRepository.findBySubLocationIdAndStatusId(
							charityBoxTransferDTO.getSubLocationId(), CharityBoxStatusEnum.ACTIVE.getValue());
					if (boxesList.size() > 0)
						return new ServiceResponse(ErrorCodeEnum.SUBLOCATION_ALREADY_HAS_CHARITYBOX,
								errorCodeRepository, lang);
				}
			}

			if (actionType.equals(CharityBoxActionTypeEnum.CHECK.getValue())) {
				CharityBox charityBox = charityBoxRepository.findOne(charityBoxTransferDTO.getCharityBoxId());
				if (charityBox == null)
					return new ServiceResponse(ErrorCodeEnum.CHARITYBOX_NOT_EXIST_IN_DB, errorCodeRepository, lang);
				if (!charityBox.getStatusId().equals(CharityBoxStatusEnum.ACTIVE.getValue())) {
					CharityBoxStatus charityBoxStatus = utilsService
							.getCharityBoxStatusFromCache(charityBox.getStatusId());
					String charityBoxStatusStr = "";
					if (charityBoxStatus != null)
						charityBoxStatusStr = "حالة الحصالة : " + charityBoxStatus.getName();
					else
						charityBoxStatusStr = "حالة الحصالة : " + charityBox.getStatusId();
					return new ServiceResponse(ErrorCodeEnum.CANNOT_CHECK_CHARITY_BOX, charityBoxStatusStr, lang);
				}
			}

			// replace operation for not active only
			if (!GeneralUtils.isEmptyNumber(charityBoxTransferDTO.getNewCharityBoxId())) {
				if (charityBoxTransferDTO.getCharityBoxId() == charityBoxTransferDTO.getNewCharityBoxId())
					return new ServiceResponse(ErrorCodeEnum.CANNOT_REPLACE_CHARITYBOX_WITH_ITSELF, errorCodeRepository,
							lang);
				CharityBox charityBoxToReplace = charityBoxRepository
						.findOne(charityBoxTransferDTO.getNewCharityBoxId());
				if (charityBoxToReplace == null)
					return new ServiceResponse(ErrorCodeEnum.NEW_CHARITYBOX_NOT_EXIST_IN_DB, errorCodeRepository, lang);
				if (!charityBoxToReplace.getStatusId().equals(CharityBoxStatusEnum.NOT_ACTIVE.getValue())) {
					return new ServiceResponse(ErrorCodeEnum.NEW_CHARITYBOX_MUST_BE_INACTIVE, errorCodeRepository,
							lang);
				}
			}

			GeneralResponseDTO generalResponseDTO = new GeneralResponseDTO();
			// here we add the new regions/locations/sub locations
			CharityBoxTransfer charityBoxTransfer = convertCharityBoxTransferDTOToEntity(charityBoxTransferDTO);
			CharityBoxTransferDetail charityBoxTransferDetail = charityBoxTransfer.getCharityBoxTransferDetail();
			if (!charityBoxTransfer.getErrorCode().getErrorCode()
					.equalsIgnoreCase(ErrorCodeEnum.SUCCESS_CODE.getErrorCode())) {
				return new ServiceResponse(charityBoxTransfer.getErrorCode(), generalResponseDTO, errorCodeRepository,
						lang);
			}
			CharityBox charityBox = charityBoxRepository.findOne(charityBoxTransferDTO.getCharityBoxId());

			if (debugEnabled)
				logger.info("##### createCharityBoxTransfer,charityboxId: " + charityBoxTransferDTO.getCharityBoxId()
						+ ",subLocation: " + charityBox.getSubLocationId());

			if (charityBoxTransfer.getSubLocation() == null)
				charityBoxTransfer.setSubLocation(new SubLocation(charityBox.getSubLocationId()));
			charityBox.setSupervisor(charityBoxTransfer.getSupervisor());

			// set charityBoxTransfer sublocation
			if (actionType.equals(CharityBoxActionTypeEnum.INSERT.getValue())) {
				charityBox.setSubLocation(charityBoxTransferDetail.getSubLocation());
			} else {
				if (debugEnabled)
					logger.info(
							"##### createCharityBoxTransfer not insert operation,setting subLocation in charityBoxTransfer: "
									+ charityBox.getSubLocationId());
				if (charityBox.getSubLocationId() == null) {
					return new ServiceResponse(ErrorCodeEnum.CHARITYBOX_MUST_BE_ASSIGNED_TO_SUBLOCATION,
							errorCodeRepository, lang);
				}
				charityBoxTransferDetail.setSubLocation(new SubLocation(charityBox.getSubLocationId()));
			}

			CharityBoxActionTypeEnum actionEnum = CharityBoxActionTypeEnum.getEnumByValue(actionType);
			if (actionEnum != null)
				charityBoxTransfer.setActionType(actionEnum.toString());
			charityBoxTransfer.setSubLocationTemporaryClosed(charityBoxTransferDTO.isSubLocationTemporaryClosed());
			charityBoxTransferDetail
					.setSubLocationTemporaryClosed(charityBoxTransferDTO.isSubLocationTemporaryClosed());
			if (charityBox != null) {
				charityBoxTransfer.setBranchId(charityBox.getBranchId());
			}
			charityBoxTransferRepository.save(charityBoxTransfer);
			generalResponseDTO.setId(String.valueOf(charityBoxTransfer.getId()));
			generalResponseDTO.setSuccess(true);

			if (charityBoxTransferDTO.getSubLocationRating() > 0) {
				logger.info(
						"####### UPDATE SUBLOCATION RATING,id: " + charityBoxTransferDetail.getSubLocation().getId());
				subLocationRepository.updateRating(charityBoxTransferDetail.getSubLocation().getId(),
						charityBoxTransferDTO.getSubLocationRating(), charityBoxTransferDTO.getDelegateId(),
						new Date());
			}

			if (StringUtils.isNotBlank(charityBoxTransferDTO.getSubLocationNotes())) {
				logger.info(
						"####### UPDATE SUBLOCATION NOTES,id: " + charityBoxTransferDetail.getSubLocation().getId());
				subLocationRepository.updateNotes(charityBoxTransferDetail.getSubLocation().getId(),
						charityBoxTransferDTO.getSubLocationNotes().trim(), charityBoxTransferDTO.getDelegateId(),
						new Date());
			}

			if (actionType.equals(CharityBoxActionTypeEnum.CHECK.getValue())) {
				if (charityBoxTransferDetail.getSubLocation() != null) {
					logger.info("####### UPDATE SUBLOCATION STATUS,id: "
							+ charityBoxTransferDetail.getSubLocation().getId());
					subLocationRepository.updateTemporaryClosed(charityBoxTransferDetail.getSubLocation().getId(),
							charityBoxTransferDTO.isSubLocationTemporaryClosed(), charityBoxTransferDTO.getDelegateId(),
							new Date());
				} else {
					logger.info(
							"####### UNABLE TO UPDATE SUBLOCATION STATUS >>> charityBoxTransfer.getSubLocation() IS NULL");
				}
			}

			// تحديث حالة الحصالة التى يتم استبدالها لتكون نشطه
			if (actionType.equals(CharityBoxActionTypeEnum.REPLACE.getValue())) {
				BigInteger newCharityBoxId = charityBoxTransferDTO.getNewCharityBoxId();
				if (!GeneralUtils.isEmptyNumber(newCharityBoxId)) {
					CharityBox newCharityBox = charityBoxRepository.findOne(newCharityBoxId);
					newCharityBox.setSubLocation(new SubLocation(charityBox.getSubLocationId()));
					newCharityBox.setStatus(new CharityBoxStatus(CharityBoxStatusEnum.ACTIVE.getValue()));
					newCharityBox.setSupervisor(charityBoxTransfer.getSupervisor());
					charityBoxRepository.save(newCharityBox);
				}
			}

			// CharityBoxStatusEnum statusEnum =
			// charityBoxTransferDTO.getActionType().getRelatedStatusEnum();
			CharityBoxStatusEnum statusEnum = CharityBoxActionTypeEnum
					.findStatus(charityBoxTransferDTO.getActionType());
			logger.info("######### updating charitybox: " + charityBox.getId() + ",charityBoxActionType: "
					+ charityBoxTransferDTO.getActionType() + ",new statusEnum: " + statusEnum);
			if (statusEnum != null) {
				charityBox.setStatus(new CharityBoxStatus(statusEnum.getValue()));
			}
			charityBox.setLastUpdateDate(new Date());
			charityBox.setLastUpdateBy(charityBoxTransfer.getSupervisor());
			logger.info("########## charitybox: " + charityBox.getId() + " new status before save is: "
					+ charityBox.getStatus());
			charityBoxRepository.save(charityBox);
			charityBox = charityBoxRepository.findOne(charityBox.getId());
			logger.info("########## charitybox: " + charityBox.getId() + " status after save is: "
					+ charityBox.getStatus());
			int index = 1;
			if (charityBoxTransferDTO.getAttachments() != null) {
				for (BigInteger attachmentId : charityBoxTransferDTO.getAttachments()) {
					attachmentRepository.updateAttachmentTransferId(charityBoxTransfer.getId(), "مرفق " + index,
							attachmentId);
					index++;
				}
			}

			if (actionType.equals(CharityBoxActionTypeEnum.COLLECTED_MONEY.getValue())
					&& !GeneralUtils.isEmptyNumber(charityBoxTransferDTO.getSafetyCaseId())) {
				if (debugEnabled)
					logger.info("######### updating safety case: " + charityBoxTransferDTO.getSafetyCaseId());
				safetyCaseRepository.updateSafetyCaseStatus(charityBoxTransferDTO.getSafetyCaseId(), "Y", new Date());
			}

			if (charityBoxTransferDTO.getUpdateSublocationLatitude() != null
					&& charityBoxTransferDTO.getUpdateSublocationLongitude() != null) {
				subLocationRepository.updateSubLocation(charityBoxTransferDTO.getUpdateSublocationLatitude(),
						charityBoxTransferDTO.getUpdateSublocationLongitude(),
						charityBoxTransferDTO.getSubLocationId());
			}
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, generalResponseDTO, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in createCharityBoxTransfer webservice,operation: " + actionType, e);
			logger.error("Exception in createCharityBoxTransfer webservice, JSON: "
					+ (new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(charityBoxTransferDTO)));
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findDelegateRoute/{delegateId}")
	@ApiOperation(value = "عرض خط السير لموظف")
	public ServiceResponse findDelegateRoute(@PathParam("delegateId") BigInteger delegateId,
			@HeaderParam("token") String token, @HeaderParam("lang") String lang) throws Exception {
		try {
			List<RouteDetail> routeDetailsList = new ArrayList<RouteDetail>();
			routeDetailsList = routeDetailRepository.findDelegateRouteDetails(delegateId);
			logger.info("###### routeDetailsList: " + routeDetailsList.size());
			List<SubLocationDTO> resultList = convertRouteDetailListToDTO(routeDetailsList);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, resultList, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findRegion webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/uploadDocument")
	@ApiOperation(value = "رفع مرفق")
	public ServiceResponse uploadDocument(DocumentDTO documentDTO, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {

			String filePath = new CustomFileUtils().uploadFile(charityBoxAttachmentsPath,
					documentDTO.getDocumentContent());
			if (StringUtils.isBlank(filePath)) {
				return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
			}

			Attachment attachment = new Attachment();
			attachment.setFilePath(filePath);
			attachment.setCreatedBy(new Delegate(documentDTO.getDelegateId()));
			attachmentRepository.save(attachment);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE,
					new GeneralResponseDTO(attachment.getId().toString()), errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in uploadDocument webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/createNewRegion")
	@ApiOperation(value = "إضافة منطقة")
	public ServiceResponse createNewRegion(RegionDTO regionDTO, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {

			Region region = createNewRegion(regionDTO);
			if (!region.isAlreadyExist())
				return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE,
						new GeneralResponseDTO(region.getId().toString()), errorCodeRepository, lang);
			else
				return new ServiceResponse(ErrorCodeEnum.REGION_ALREADY_EXIST, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in createNewRegion webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/createNewLocation")
	@ApiOperation(value = "إضافة موقع")
	public ServiceResponse createNewLocation(LocationDTO locationDTO, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {

			UserToken userToken = userTokenRepository.findByToken(token);
			BigInteger delegateId = null;
			if (userToken != null)
				delegateId = userToken.getDelegateId();
			Location location = createNewLocation(locationDTO, delegateId);
			if (!location.isAlreadyExist())
				return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE,
						new GeneralResponseDTO(location.getId().toString()), errorCodeRepository, lang);
			else
				return new ServiceResponse(ErrorCodeEnum.LOCATION_ALREADY_EXIST, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in createNewLocation webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/createNewSubLocation")
	@ApiOperation(value = "إضافة موقع فرعي")
	public ServiceResponse createNewSubLocation(SubLocationDTO subLocationDTO, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {

			UserToken userToken = userTokenRepository.findByToken(token);
			BigInteger delegateId = null;
			if (userToken != null)
				delegateId = userToken.getDelegateId();
			SubLocation subLocation = createNewSubLocation(subLocationDTO, null, delegateId);
			if (!subLocation.isAlreadyExist())
				return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE,
						new GeneralResponseDTO(subLocation.getId().toString()), errorCodeRepository, lang);
			else
				return new ServiceResponse(ErrorCodeEnum.SUBLOCATION_ALREADY_EXIST, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in createNewSubLocation webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/getEmarahList/{delegateId}")
	@ApiOperation(value = "عرض اسماء الامارات")
	public ServiceResponse getEmarahList(@PathParam("delegateId") BigInteger delegateId,
			@HeaderParam("token") String token, @HeaderParam("lang") String lang) throws Exception {
		try {

			List<Emarah> allEmarahList = emarahRepository.getEmarahList();
			List<Emarah> emarahListFinal = new ArrayList<Emarah>();
			Delegate delegate = delegateRepository.findOne(delegateId);
			if (!delegate.isAdmin()) {
				List<BigDecimal> authorizedList = delegateRepository.getAutorizedEmaratesList(delegateId);
				for (BigDecimal emarahId : authorizedList) {
					Emarah emarah = findEmarahById(allEmarahList, emarahId.toBigInteger());
					if (emarah != null)
						emarahListFinal.add(emarah);
				}
			} else {
				emarahListFinal.addAll(allEmarahList);
			}
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, emarahListFinal, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in getEmarahList webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

}
