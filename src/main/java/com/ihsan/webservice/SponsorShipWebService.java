package com.ihsan.webservice;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ihsan.constants.ErrorCodeEnum;
import com.ihsan.entities.Donator;
import com.ihsan.entities.FirstTitle;
import com.ihsan.entities.GiftType;
import com.ihsan.entities.Orphan;
import com.ihsan.entities.SponsorshipCountry;
import com.ihsan.entities.SponsorshipType;
import com.ihsan.webservice.dto.GeneralResponseDTO;
import com.ihsan.webservice.dto.OrphanDTO;
import com.ihsan.webservice.dto.ServiceResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Component
@Path("/v1/sponsorship")
@Api("")
public class SponsorShipWebService extends HAIServiceBase {

	private static final Logger logger = LoggerFactory.getLogger(SponsorShipWebService.class);

	@Value("${orphanImagesPath}")
	private String orphanImagesPath;

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/getSponsorshipTypes")
	@ApiOperation(value = "عرض أنوع الكفالات")
	public ServiceResponse getSponsorshipTypes(@HeaderParam("token") String token, @HeaderParam("lang") String lang)
			throws Exception {
		try {

			List<SponsorshipType> list = sponsorshipTypeRepository.getSponsorshipTypes();
			logger.info("###### getSponsorshipTypes size: " + list.size());
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, list, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in getSponsorshipTypes webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findSponsorshipCountries/{sponsorshipTypeId}")
	@ApiOperation(value = "عرض الدول المتاح فيها نوع كفالة معين")
	public ServiceResponse findSponsorshipCountries(@PathParam("sponsorshipTypeId") String sponsorshipTypeId,
			@HeaderParam("token") String token, @HeaderParam("lang") String lang) throws Exception {
		try {

			List<SponsorshipCountry> list = countrySponsorshipRepository.findSponsorshipCountries(sponsorshipTypeId);
			logger.info("###### findSponsorshipCountries size: " + list.size());
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, list, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findSponsorshipCountries webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.WILDCARD)
	@Path("/getSponsorshipCountryImage/{countryId}")
	@ApiOperation(value = "تحميل صورة الدولة الخاصة بنوع المشروع")
	public Response getSponsorshipCountryImage(@PathParam("countryId") String countryId,
			@HeaderParam("token") String token, @HeaderParam("lang") String lang) throws Exception {
		try {

			byte[] image = countrySponsorshipRepository.getImageById(new BigInteger(countryId));
			String mimeType = "image/png";
			mimeType = StringUtils.isEmpty(mimeType) ? "image/*" : mimeType;
			String fileName = "SponsorshipCountry_" + countryId;
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
			logger.error("Exception in getSponsorshipCountryImage webservice: ", e);
			return Response.serverError().build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/getGiftTypes")
	@ApiOperation(value = "عرض أنوع الهدايا")
	public ServiceResponse getGiftTypes(@HeaderParam("token") String token, @HeaderParam("lang") String lang)
			throws Exception {
		try {

			List<GiftType> list = giftTypeRepository.getGiftTypes();
			logger.info("###### getGiftTypes size: " + list.size());
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, list, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in getGiftTypes webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/getFirstTitles")
	@ApiOperation(value = "عرض الألقاب")
	public ServiceResponse getFirstTitles(@HeaderParam("token") String token, @HeaderParam("lang") String lang)
			throws Exception {
		try {

			List<FirstTitle> list = firstTitleRepository.getFirstTitles();
			logger.info("###### getFirstTitles size: " + list.size());
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, list, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in getFirstTitles webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findOrphans/{countryId}/{sponsorshipTypeId}/{pageNumber}")
	@ApiOperation(value = "عرض الأيتام فى دولة معينة لنوع كفالة معين")
	public ServiceResponse findOrphans(@PathParam("countryId") String countryId,
			@PathParam("sponsorshipTypeId") String sponsorshipTypeId, @PathParam("pageNumber") int pageNumber,
			@HeaderParam("token") String token, @HeaderParam("lang") String lang) throws Exception {
		try {

			int pageSize = 10;
			int startFromRowNumber = (pageNumber * pageSize) - pageSize;
			logger.info("######### findOrphans,countryId: " + countryId + ",sponsorshipTypeId: " + sponsorshipTypeId
					+ ",startFromRowNumber: " + startFromRowNumber);
			List<Orphan> orphansList = orphanRepository.findOrphans(new BigInteger(countryId), sponsorshipTypeId,
					startFromRowNumber, pageSize);
			logger.info("###### findOrphans size: " + orphansList.size());
			List<OrphanDTO> list = convertOrphansToDTO(orphansList);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, list, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findOrphans webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findSponsor/{searchText}")
	@ApiOperation(value = "البحث عن الكافل بجزء من الإسم او رقم الجوال يبدء ب او رقم صندوق البريد يبدء ب أو رقم الحساب يبدء ب")
	public ServiceResponse findSponsor(@PathParam("searchText") String searchText, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {

			List<Donator> sponsorList = new ArrayList<Donator>();
			if (StringUtils.isNotBlank(searchText)) {
				searchText = searchText.trim();
				if (StringUtils.isAlpha(searchText) || StringUtils.containsWhitespace(searchText)) {
					sponsorList = sponsorRepository.findTop50ByNameIgnoreCaseContainingOrderByNameAsc(searchText);
				} else if (StringUtils.isNumeric(searchText)) {
					sponsorList = sponsorRepository.findByMobileOrMailBoxOrAccountNumber(searchText,
							new PageRequest(0, 50, new Sort(Sort.Direction.ASC, "name")));
				} else if (searchText.contains("@")) {
					sponsorList = sponsorRepository.findTop50ByEmailIgnoreCaseContainingOrderByNameAsc(searchText);
				}
			} else {
				sponsorList = sponsorRepository.findTop50ByNameNotNullOrderByNameAsc();
			}
			logger.info("###### findSponsor,sponsorList: " + sponsorList.size());

			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, sponsorList, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findSponsor webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/getOrphanDetails/{id}")
	@ApiOperation(value = "عرض تفاصيل اليتيم")
	public ServiceResponse findOrphanDetails(@PathParam("id") BigInteger id, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {

			OrphanDTO orphanDTO = utilsService.getOrphanDetails(id);
			logger.info("###### findOrphanDetails,orphan id: " + orphanDTO.getId());
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, orphanDTO, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findOrphanDetails webservice,id: " + id, e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/flagOrphan/{id}")
	@ApiOperation(value = "حجز يتيم")
	public ServiceResponse flagOrphan(@PathParam("id") BigInteger id, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {

			GeneralResponseDTO generalResponseDTO = new GeneralResponseDTO();
			BigDecimal isFlaggedValue = orphanRepository.isFlagged(id);
			boolean isFlagged = isFlaggedValue != null && isFlaggedValue.intValue() == 1;
			if (!isFlagged) {
				int result = orphanRepository.flag(id);
				generalResponseDTO.setSuccess(result > 0);
			}
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, generalResponseDTO, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in flagOrphan webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/unFlagOrphan/{id}")
	@ApiOperation(value = "الغاء حجز يتيم")
	public ServiceResponse unFlagOrphan(@PathParam("id") BigInteger id, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {

			GeneralResponseDTO generalResponseDTO = new GeneralResponseDTO();
			int result = orphanRepository.unFlag(id);

			generalResponseDTO.setSuccess(result > 0);
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, generalResponseDTO, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in unFlag webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findSponsorOldOrphans/{sponsorId}")
	@ApiOperation(value = "عرض الأيتام المكفولين بواسطة كافل معين")
	public ServiceResponse findSponsorOldOrphans(@PathParam("sponsorId") BigInteger sponsorId,
			@HeaderParam("token") String token, @HeaderParam("lang") String lang) throws Exception {
		try {

			// List<OldSponsorship> oldSponsorshipList =
			// oldSponsorshipRepository.getOldSponsorships(sponsorId);

			// List<OrphanDTO> list = convertOldSponsorshipToDTO(oldSponsorshipList);
			List<OrphanDTO> list = utilsRepository.getOldSponsorships(sponsorId);
			logger.info("###### oldSponsorshipList size: " + list.size());
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, list, errorCodeRepository, lang);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception in findSponsorOldOrphans webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/findSponsorshipTypeAmount/{sponsorshipTypeId}/{countryId}")
	@ApiOperation(value = "عرض الدول المتاح فيها نوع كفالة معين")
	public ServiceResponse findSponsorshipTypeAmount(@PathParam("sponsorshipTypeId") BigInteger sponsorshipTypeId,
			@PathParam("countryId") BigInteger countryId, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {

			Integer amount = sponsorshipTypeRepository.findSponsorshipTypeAmount(sponsorshipTypeId, countryId);
			if (amount == null || amount == 0) {
				amount = 150;
			}
			return new ServiceResponse(ErrorCodeEnum.SUCCESS_CODE, amount, errorCodeRepository, lang);
		} catch (Exception e) {
			logger.error("Exception in findSponsorshipTypeAmount webservice: ", e);
			return new ServiceResponse(ErrorCodeEnum.SYSTEM_ERROR_CODE, errorCodeRepository, lang);
		}
	}

	@GET
	@Produces(MediaType.WILDCARD)
	@Path("/getOrphanImage/{orphanId}/{imageFileName}")
	@ApiOperation(value = "عرض صورة اليتيم")
	public Response getOrphanImage(@PathParam("orphanId") BigInteger orphanId,
			@PathParam("imageFileName") String imageFileName, @HeaderParam("token") String token,
			@HeaderParam("lang") String lang) throws Exception {
		try {

			byte[] image = null;
			String mimeType = "image/jpg";
			if (StringUtils.isNotBlank(imageFileName)) {
				if (imageFileName.contains(".png")) {
					mimeType = "image/png";
				}
				String imagePath = orphanImagesPath + "\\" + orphanId + "\\" + imageFileName;

				java.nio.file.Path path = Paths.get(imagePath);
				image = Files.readAllBytes(path);
			}
			return Response.ok(image, mimeType)
					.header("Content-Disposition", "attachment; filename=\"" + imageFileName + "\"").build();
		} catch (Exception e) {
			logger.error("Exception in getOrphanImage webservice: ", e);
			return Response.serverError().build();
		}
	}

}