package com.ihsan.service;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ihsan.dao.CountryRepository;
import com.ihsan.dao.CouponTypeRepository;
import com.ihsan.dao.DelegateRepository;
import com.ihsan.dao.FamilyRepository;
import com.ihsan.dao.FirstTitleRepository;
import com.ihsan.dao.GenderRepository;
import com.ihsan.dao.GiftTypeRepository;
import com.ihsan.dao.NationalityRepository;
import com.ihsan.dao.NewProjectTypeRepository;
import com.ihsan.dao.OrphanRepository;
import com.ihsan.dao.ReceiptDetailsRepository;
import com.ihsan.dao.ReceiptRepository;
import com.ihsan.dao.charityBoxes.CharityBoxActionTypeRepository;
import com.ihsan.dao.charityBoxes.CharityBoxCategoryRepository;
import com.ihsan.dao.charityBoxes.CharityBoxStatusRepository;
import com.ihsan.dao.charityBoxes.CharityBoxTypeRepository;
import com.ihsan.dao.charityBoxes.LocationRepository;
import com.ihsan.dao.charityBoxes.RegionRepository;
import com.ihsan.dao.charityBoxes.SubLocationRepository;
import com.ihsan.entities.Country;
import com.ihsan.entities.CouponType;
import com.ihsan.entities.Family;
import com.ihsan.entities.FirstTitle;
import com.ihsan.entities.Gender;
import com.ihsan.entities.GiftType;
import com.ihsan.entities.Nationality;
import com.ihsan.entities.NewProjectType;
import com.ihsan.entities.Orphan;
import com.ihsan.entities.Receipt;
import com.ihsan.entities.charityBoxes.CharityBoxActionType;
import com.ihsan.entities.charityBoxes.CharityBoxCategory;
import com.ihsan.entities.charityBoxes.CharityBoxStatus;
import com.ihsan.entities.charityBoxes.CharityBoxType;
import com.ihsan.entities.charityBoxes.Location;
import com.ihsan.entities.charityBoxes.Region;
import com.ihsan.entities.charityBoxes.SubLocation;
import com.ihsan.util.GeneralUtils;
import com.ihsan.webservice.dto.OrphanDTO;

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

	@Autowired
	private CharityBoxCategoryRepository charityBoxCategoryRepository;

	@Autowired
	private CharityBoxStatusRepository charityBoxStatusRepository;

	@Autowired
	private CharityBoxTypeRepository charityBoxTypeRepository;

	@Autowired
	private RegionRepository regionRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private SubLocationRepository subLocationRepository;

	@Autowired
	private CharityBoxActionTypeRepository charityBoxActionTypeRepository;

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private GiftTypeRepository giftTypeRepository;

	@Autowired
	private GenderRepository genderRepository;

	@Autowired
	private FirstTitleRepository firstTitleRepository;

	@Autowired
	private OrphanRepository orphanRepository;

	@Autowired
	private NationalityRepository nationalityRepository;

	@Autowired
	private FamilyRepository familyRepository;

	@Autowired
	private ReceiptDetailsRepository receiptDetailsRepository;

	public Receipt createReceipt(Receipt receipt) {
		receiptRepository.save(receipt);
		Long receiptNumber = receiptRepository.getMaxReceiptNumber();
		receiptNumber = receiptNumber + 1;
		receipt.setNumber(String.valueOf(receiptNumber));
		return receiptRepository.save(receipt);
	}

	public CouponType getCouponFromCache(BigInteger id) {
		if (!GeneralUtils.isBigIntegerGreaterThanZero(id))
			return new CouponType();
		List<CouponType> list = couponRepository.getCoupons();
		for (CouponType couponTypeLoop : list) {
			if (couponTypeLoop.getId() == id) {
				return couponTypeLoop;
			}
		}
		CouponType couponType = couponRepository.findOne(id);
		if (couponType == null)
			couponType = new CouponType();
		return couponType;
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

	public NewProjectType getNewProjectTypeFromCache(String projectTypeIdStr) {
		if (StringUtils.isBlank(projectTypeIdStr))
			return new NewProjectType();
		BigInteger projectTypeId = new BigInteger(projectTypeIdStr);
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

	public CharityBoxCategory getCharityBoxCategoryFromCache(String charityBoxCategoryId) {
		if (StringUtils.isBlank(charityBoxCategoryId))
			return new CharityBoxCategory();
		List<CharityBoxCategory> list = charityBoxCategoryRepository.getCharityBoxCategoryList();
		for (CharityBoxCategory charityBoxCategoryLoop : list) {
			if (charityBoxCategoryLoop.getId().equals(charityBoxCategoryId)) {
				return charityBoxCategoryLoop;
			}
		}
		CharityBoxCategory charityBoxCategory = charityBoxCategoryRepository.findOne(charityBoxCategoryId);
		if (charityBoxCategory == null)
			charityBoxCategory = new CharityBoxCategory();
		return charityBoxCategory;
	}

	public CharityBoxStatus getCharityBoxStatusFromCache(String charityBoxStatusId) {
		if (StringUtils.isBlank(charityBoxStatusId))
			return new CharityBoxStatus();
		List<CharityBoxStatus> list = charityBoxStatusRepository.getCharityBoxStatusList();
		for (CharityBoxStatus charityBoxStatusLoop : list) {
			if (charityBoxStatusLoop.getId().equals(charityBoxStatusId)) {
				return charityBoxStatusLoop;
			}
		}
		CharityBoxStatus charityBoxStatus = charityBoxStatusRepository.findOne(charityBoxStatusId);
		if (charityBoxStatus == null)
			charityBoxStatus = new CharityBoxStatus();
		return charityBoxStatus;
	}

	public CharityBoxType getCharityBoxTypeFromCache(BigInteger charityBoxTypeId) {
		if (charityBoxTypeId == null)
			return new CharityBoxType();
		List<CharityBoxType> list = charityBoxTypeRepository.findAll();
		for (CharityBoxType charityBoxTypeLoop : list) {
			if (charityBoxTypeLoop.getId().equals(charityBoxTypeId)) {
				return charityBoxTypeLoop;
			}
		}
		CharityBoxType charityBoxType = charityBoxTypeRepository.findOne(charityBoxTypeId);
		if (charityBoxType == null)
			charityBoxType = new CharityBoxType();
		return charityBoxType;
	}

	public Region getRegionFromCache(BigInteger regionId) {
		if (regionId == null)
			return new Region();
		List<Region> list = regionRepository.findAll();
		for (Region regionLoop : list) {
			if (regionLoop.getId().equals(regionId)) {
				return regionLoop;
			}
		}
		Region region = regionRepository.findOne(regionId);
		if (region == null)
			region = new Region();
		return region;
	}

	public Location getLocationFromCache(BigInteger locationId) {
		if (locationId == null)
			return new Location();
		List<Location> list = locationRepository.findAll();
		for (Location locationLoop : list) {
			if (locationLoop.getId().equals(locationId)) {
				return locationLoop;
			}
		}
		Location location = locationRepository.findOne(locationId);
		if (location == null)
			location = new Location();
		return location;
	}

	public SubLocation getSubLocationFromCache(BigInteger subLocationId) {
		if (subLocationId == null)
			return new SubLocation();
		List<SubLocation> list = subLocationRepository.findAll();
		for (SubLocation subLocationLoop : list) {
			if (subLocationLoop.getId().equals(subLocationId)) {
				return subLocationLoop;
			}
		}
		SubLocation subLocation = subLocationRepository.findOne(subLocationId);
		if (subLocation == null)
			subLocation = new SubLocation();
		return subLocation;
	}

	public CharityBoxActionType getCharityBoxActionTypeFromCache(String charityBoxActionTypeId) {
		if (StringUtils.isBlank(charityBoxActionTypeId))
			return new CharityBoxActionType();
		List<CharityBoxActionType> list = charityBoxActionTypeRepository.getCharityBoxActionTypeList();
		for (CharityBoxActionType charityBoxActionTypeLoop : list) {
			if (charityBoxActionTypeLoop.getId().equals(charityBoxActionTypeId)) {
				return charityBoxActionTypeLoop;
			}
		}
		CharityBoxActionType charityBoxActionType = charityBoxActionTypeRepository.findOne(charityBoxActionTypeId);
		if (charityBoxActionType == null)
			charityBoxActionType = new CharityBoxActionType();
		return charityBoxActionType;
	}

	public Country getCountryFromCache(String countryId) {
		if (countryId == null)
			return new Country();
		List<Country> list = countryRepository.findAll();
		for (Country countryLoop : list) {
			if (countryLoop.getId().equals(countryId)) {
				return countryLoop;
			}
		}
		Country country = countryRepository.findOne(countryId);
		if (country == null)
			country = new Country();
		return country;
	}

	public Nationality getNationalityFromCache(String nationalityId) {
		if (StringUtils.isBlank(nationalityId))
			return new Nationality();
		List<Nationality> list = nationalityRepository.findAll();
		for (Nationality loopObj : list) {
			if (loopObj.getId().equals(nationalityId)) {
				return loopObj;
			}
		}
		Nationality obj = nationalityRepository.findOne(nationalityId);
		if (StringUtils.isBlank(nationalityId))
			obj = new Nationality();
		return obj;
	}

	public Gender getGenderFromCache(String genderId) {
		if (StringUtils.isBlank(genderId))
			return new Gender();
		if (genderId.trim().equals("0"))
			genderId = "3";
		List<Gender> list = genderRepository.findAll();
		for (Gender genderLoop : list) {
			if (genderLoop.getId().equals(genderId)) {
				return genderLoop;
			}
		}
		Gender gender = genderRepository.findOne(genderId);
		if (gender == null)
			gender = new Gender();
		return gender;
	}

	public FirstTitle getFirstTitleFromCache(String firstTitleId) {
		if (StringUtils.isBlank(firstTitleId))
			return new FirstTitle();
		List<FirstTitle> list = firstTitleRepository.getFirstTitles();
		for (FirstTitle firstTitleLoop : list) {
			if (firstTitleLoop.getId().equals(firstTitleId)) {
				return firstTitleLoop;
			}
		}
		FirstTitle firstTitle = firstTitleRepository.findOne(firstTitleId);
		if (firstTitle == null)
			firstTitle = new FirstTitle();
		return firstTitle;
	}

	public GiftType getGiftTypeFromCache(String giftTypeId) {
		if (StringUtils.isBlank(giftTypeId))
			return new GiftType();
		List<GiftType> list = giftTypeRepository.getGiftTypes();
		for (GiftType giftTypeLoop : list) {
			if (giftTypeLoop.getId().equals(giftTypeId)) {
				return giftTypeLoop;
			}
		}
		GiftType giftType = giftTypeRepository.findOne(giftTypeId);
		if (giftType == null)
			giftType = new GiftType();
		return giftType;
	}

	public OrphanDTO getOrphanDetails(BigInteger id) {
		Orphan orphan = orphanRepository.findOne(id);
		String genderId = null;
		if (orphan.getGenderId() != null)
			genderId = orphan.getGenderId().toString();
		Gender gender = getGenderFromCache(genderId);
		OrphanDTO orphanDTO = new OrphanDTO(orphan.getId().toString(), orphan.getName(), orphan.getBirthDateStr(),
				orphan.getCaseNumber(), gender.getName());

		if (orphan.getNationalityId() != null) {
			Nationality nationality = getNationalityFromCache(orphan.getNationalityId().toString());
			orphanDTO.setNationality(nationality.getName());
		}
		if (orphan.getFamilyId() != null) {
			Family family = familyRepository.findOne(orphan.getFamilyId());
			if (family != null) {
				orphanDTO.setFatherDeathReason(family.getFatherDeathReason());
				orphanDTO.setFamilyNumber(family.getNumber());
			}
		}
		orphanDTO.setMotherName(orphan.getMotherName());
		if (orphanDTO.getFamilyNumber() == null) {
			orphanDTO.setFamilyNumber("");
		}
		if (orphanDTO.getFatherDeathReason() == null) {
			orphanDTO.setFatherDeathReason("");
		}
		if (orphanDTO.getMotherName() == null) {
			orphanDTO.setMotherName("");
		}
		if (orphanDTO.getNationality() == null) {
			orphanDTO.setNationality("");
		}
		return orphanDTO;
	}

	public void checkReceiptDetailsSequence() {
		log.info("######## checkReceiptDetailsSequence ###########");
		Long maxReceiptId = receiptDetailsRepository.getMaxReceiptDetailId();
		Long currentSequenceValue = receiptDetailsRepository.getReceiptDetailsSequenceCurrentValue();
		Long difference = maxReceiptId - currentSequenceValue;
		log.info("######## maxReceiptId: " + maxReceiptId);
		log.info("######## currentSequenceValue: " + currentSequenceValue);
		log.info("######## difference: " + difference);
		if (currentSequenceValue < maxReceiptId) {
			difference = difference + 1;
			for (int i = 0; i < difference; i++) {
				Long nextSequenceValue = receiptDetailsRepository.getReceiptDetailsSequenceNextValue();
				log.info("######## nextSequenceValue: " + nextSequenceValue);
			}
		} else {
			log.info("######## SEQUENCE MATCHES MAX ID >>> NO ACTION REQUIRED ###########");
		}
	}

}
