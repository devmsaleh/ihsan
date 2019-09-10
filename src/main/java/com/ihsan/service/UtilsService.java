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
import com.ihsan.dao.charityBoxes.CharityBoxActionTypeRepository;
import com.ihsan.dao.charityBoxes.CharityBoxCategoryRepository;
import com.ihsan.dao.charityBoxes.CharityBoxStatusRepository;
import com.ihsan.dao.charityBoxes.CharityBoxTypeRepository;
import com.ihsan.dao.charityBoxes.LocationRepository;
import com.ihsan.dao.charityBoxes.RegionRepository;
import com.ihsan.dao.charityBoxes.SubLocationRepository;
import com.ihsan.entities.CouponType;
import com.ihsan.entities.NewProjectType;
import com.ihsan.entities.Receipt;
import com.ihsan.entities.charityBoxes.CharityBoxActionType;
import com.ihsan.entities.charityBoxes.CharityBoxCategory;
import com.ihsan.entities.charityBoxes.CharityBoxStatus;
import com.ihsan.entities.charityBoxes.CharityBoxType;
import com.ihsan.entities.charityBoxes.Location;
import com.ihsan.entities.charityBoxes.Region;
import com.ihsan.entities.charityBoxes.SubLocation;

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

}
