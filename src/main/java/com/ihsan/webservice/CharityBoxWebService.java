package com.ihsan.webservice;

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
import com.ihsan.entities.charityBoxes.Attachment;
import com.ihsan.entities.charityBoxes.CharityBox;
import com.ihsan.entities.charityBoxes.CharityBoxStatus;
import com.ihsan.entities.charityBoxes.CharityBoxTransfer;
import com.ihsan.entities.charityBoxes.CharityBoxTransferDetail;
import com.ihsan.entities.charityBoxes.Emarah;
import com.ihsan.entities.charityBoxes.Location;
import com.ihsan.entities.charityBoxes.Region;
import com.ihsan.entities.charityBoxes.RouteDetail;
import com.ihsan.entities.charityBoxes.SubLocation;
import com.ihsan.enums.CharityBoxActionTypeEnum;
import com.ihsan.enums.CharityBoxStatusEnum;
import com.ihsan.util.CustomFileUtils;
import com.ihsan.util.GeneralUtils;
import com.ihsan.webservice.dto.GeneralResponseDTO;
import com.ihsan.webservice.dto.ServiceResponse;
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
				charityBox = charityBoxRepository.findByNumber(barcodeDTO.getNumber());
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
	@Path("/findRegion/{emarahId}/{name}")
	@ApiOperation(value = "البحث عن منطقة بجزء من الاسم")
	public ServiceResponse findRegion(@PathParam("emarahId") BigInteger emarahId, @PathParam("name") String name,
			@HeaderParam("token") String token, @HeaderParam("lang") String lang) throws Exception {
		try {
			logger.info("###### emarahId: " + emarahId + ",name: " + name);
			List<Region> regionsList = new ArrayList<Region>();
			if (StringUtils.isNotBlank(name)) {
				// Pageable page = new PageRequest(0, 10, new Sort(Sort.Direction.ASC, "name"));
				regionsList = regionRepository.findTop10ByEmarahIdAndNameIgnoreCaseContainingOrderByNameAsc(emarahId,
						name.trim());
			} else {
				regionsList = regionRepository.findTop10ByEmarahIdOrderByNameAsc(emarahId);
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
			if (StringUtils.isNotBlank(name) && !name.equalsIgnoreCase("ALL")) {
				locationsList = locationRepository
						.findTop10ByRegionIdAndNameIgnoreCaseContainingOrderByNameAsc(regionId, name.trim());
			} else {
				locationsList = locationRepository.findTop500ByRegionIdOrderByNameAsc(regionId);
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
	@Path("/findSubLocation/{locationId}/{name}")
	@ApiOperation(value = "البحث عن مكان بجزء من الاسم")
	public ServiceResponse findSubLocation(@PathParam("locationId") BigInteger locationId,
			@PathParam("name") String name, @HeaderParam("token") String token, @HeaderParam("lang") String lang)
			throws Exception {
		try {

			List<SubLocation> subLocationsList = new ArrayList<SubLocation>();
			boolean concatDetails = false;
			if (locationId != null && locationId.compareTo(BigInteger.ZERO) > 0) {
				if (StringUtils.isNotBlank(name) && !name.equalsIgnoreCase("ALL")) {
					subLocationsList = subLocationRepository
							.findTop10ByLocationIdAndNameIgnoreCaseContainingOrderByNameAsc(locationId, name.trim());
				} else {
					subLocationsList = subLocationRepository.findTop500ByLocationIdOrderByNameAsc(locationId);
				}
			} else {
				concatDetails = true;
				subLocationsList = subLocationRepository.findTop10ByNameIgnoreCaseContainingOrderByNameAsc(name);
			}
			logger.info("###### findSubLocation,subLocationsList: " + subLocationsList.size());
			List<SubLocationDTO> resultList = convertSubLocationListToDTO(subLocationsList, concatDetails);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, resultList, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findSubLocation webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findInsertSubLocation/{locationId}/{sourceId}/{name}")
	@ApiOperation(value = "البحث عن مكان بجزء من الاسم لعملية زرع الحصالة")
	public ServiceResponse findInsertSubLocation(@PathParam("locationId") BigInteger locationId,
			@PathParam("sourceId") String sourceId, @PathParam("name") String name, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {
			List<SubLocation> subLocationsList = new ArrayList<SubLocation>();

			if (StringUtils.isNotBlank(name) && !name.equalsIgnoreCase("ALL")) {
				subLocationsList = subLocationRepository.findTop10ByLocationIdAndCategoryIdAndNameForInsert(locationId,
						sourceId, name);
			} else {
				subLocationsList = subLocationRepository.findTop500ByLocationIdAndCategoryIdForInsert(locationId,
						sourceId);
			}
			logger.info("###### findInsertSubLocation,subLocationsList: " + subLocationsList.size());
			List<SubLocationDTO> resultList = convertSubLocationListToDTO(subLocationsList, false);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, resultList, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findInsertSubLocation webservice: ", e);
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
		try {

			// التحقق من انه لا يوجد مكان لنفس الحى مضاف مسبقا
			// وذلك لمنع زرع اكثر من حصالة فى نفس المكان

			if (debugEnabled) {
				logger.info("###### createCharityBoxTransfer,JSON: " + (new ObjectMapper()
						.writerWithDefaultPrettyPrinter().writeValueAsString(charityBoxTransferDTO)));
			}

			String actionType = charityBoxTransferDTO.getActionType().getValue();
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

			if (actionType.equals(CharityBoxActionTypeEnum.INSERT.getValue())
					&& GeneralUtils.isBigIntegerGreaterThanZero(charityBoxTransferDTO.getSubLocationId())) {
				List<CharityBox> boxesList = charityBoxRepository.findBySubLocationIdAndStatusId(
						charityBoxTransferDTO.getSubLocationId(), CharityBoxStatusEnum.ACTIVE.getValue());
				if (boxesList.size() > 0)
					return new ServiceResponse(ErrorCodeEnum.SUBLOCATION_ALREADY_HAS_CHARITYBOX, errorCodeRepository,
							lang);
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
			CharityBoxTransferDetail detail = charityBoxTransfer.getCharityBoxTransferDetail();
			if (charityBoxTransfer.getErrorCode().intValue() != ErrorCodeEnum.SUCCESS_CODE.intValue()) {
				return new ServiceResponse(charityBoxTransfer.getErrorCode(), generalResponseDTO, errorCodeRepository,
						lang);
			}
			CharityBox charityBox = charityBoxRepository.findOne(charityBoxTransferDTO.getCharityBoxId());

			if (debugEnabled)
				logger.info("##### createCharityBoxTransfer,charityboxId: " + charityBoxTransferDTO.getCharityBoxId()
						+ ",subLocation: " + charityBox.getSubLocationId());

			charityBox.setSupervisor(charityBoxTransfer.getSupervisor());

			// set charityBoxTransfer sublocation
			if (actionType.equals(CharityBoxActionTypeEnum.INSERT.getValue())) {
				charityBox.setSubLocation(detail.getSubLocation());
			} else {
				if (debugEnabled)
					logger.info(
							"##### createCharityBoxTransfer not insert operation,setting subLocation in charityBoxTransfer: "
									+ charityBox.getSubLocationId());
				detail.setSubLocation(new SubLocation(charityBox.getSubLocationId()));
			}

			charityBoxTransferRepository.save(charityBoxTransfer);
			generalResponseDTO.setId(String.valueOf(charityBoxTransfer.getId()));
			generalResponseDTO.setSuccess(true);

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

			String statusId = findCharityBoxStatusId(charityBoxTransferDTO.getActionType().getValue());
			if (!statusId.equals("-1"))
				charityBox.setStatus(new CharityBoxStatus(statusId));
			charityBox.setLastUpdateDate(new Date());
			charityBox.setLastUpdateBy(charityBoxTransfer.getSupervisor());
			charityBoxRepository.save(charityBox);

			int index = 1;
			if (charityBoxTransferDTO.getAttachments() != null) {
				for (BigInteger attachmentId : charityBoxTransferDTO.getAttachments()) {
					attachmentRepository.updateAttachmentTransferId(charityBoxTransfer.getId(), "مرفق " + index,
							attachmentId);
					index++;
				}
			}

			if (charityBoxTransferDTO.getUpdateSublocationLatitude() != null
					&& charityBoxTransferDTO.getUpdateSublocationLongitude() != null) {
				subLocationRepository.updateSubLocation(charityBoxTransferDTO.getUpdateSublocationLatitude(),
						charityBoxTransferDTO.getUpdateSublocationLongitude(),
						charityBoxTransferDTO.getSubLocationId());
			}
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, generalResponseDTO, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in createCharityBoxTransfer webservice: ", e);
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

			Location location = createNewLocation(locationDTO);
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

			SubLocation subLocation = createNewSubLocation(subLocationDTO, null);
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

	private String findCharityBoxStatusId(String actionType) {
		String statusId = "-1";
		if (actionType.equals(CharityBoxActionTypeEnum.INSERT.getValue())) {
			statusId = CharityBoxStatusEnum.ACTIVE.getValue();
		} else if (actionType.equals(CharityBoxActionTypeEnum.BROKEN.getValue())) {
			statusId = CharityBoxStatusEnum.BROKEN.getValue();
		} else if (actionType.equals(CharityBoxActionTypeEnum.COLLECT.getValue())) {
			statusId = CharityBoxStatusEnum.COLLECTED.getValue();
		} else if (actionType.equals(CharityBoxActionTypeEnum.LOST.getValue())) {
			statusId = CharityBoxStatusEnum.LOST.getValue();
		} else if (actionType.equals(CharityBoxActionTypeEnum.REPLACE.getValue())) {
			statusId = CharityBoxStatusEnum.REPLACED.getValue();
		} else if (actionType.equals(CharityBoxActionTypeEnum.COLLECTED_MONEY.getValue())) {
			statusId = CharityBoxStatusEnum.ACTIVE.getValue();
		} else if (actionType.equals(CharityBoxActionTypeEnum.WITHDRAWAL.getValue())) {
			statusId = CharityBoxStatusEnum.NOT_ACTIVE.getValue();
		}
		return statusId;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/getEmarahList")
	@ApiOperation(value = "عرض اسماء الامارات")
	public ServiceResponse getEmarahList(@HeaderParam("token") String token, @HeaderParam("lang") String lang)
			throws Exception {
		try {

			List<Emarah> emarahList = emarahRepository.getEmarahList();
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, emarahList, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in getEmarahList webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

}
