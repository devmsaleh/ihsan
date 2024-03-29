package com.ihsan.service;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihsan.dao.BankChequeRepository;
import com.ihsan.dao.BankDepositRepository;
import com.ihsan.dao.BankTransferRepository;
import com.ihsan.dao.CouponTypeRepository;
import com.ihsan.dao.ErrorCodeRepository;
import com.ihsan.dao.FirstTitleRepository;
import com.ihsan.dao.GenderRepository;
import com.ihsan.dao.GiftTypeRepository;
import com.ihsan.dao.NewProjectCountryRepository;
import com.ihsan.dao.NewProjectTypeRepository;
import com.ihsan.dao.SponsorshipCountryRepository;
import com.ihsan.dao.SponsorshipTypeRepository;
import com.ihsan.dao.charityBoxes.CharityBoxActionTypeRepository;
import com.ihsan.dao.charityBoxes.CharityBoxCategoryRepository;
import com.ihsan.dao.charityBoxes.CharityBoxStatusRepository;
import com.ihsan.dao.charityBoxes.CharityBoxTypeRepository;
import com.ihsan.dao.charityBoxes.EmarahRepository;
import com.ihsan.dao.charityBoxes.LocationRepository;
import com.ihsan.dao.charityBoxes.RegionRepository;
import com.ihsan.dao.charityBoxes.SubLocationRepository;
import com.ihsan.entities.BankCheque;
import com.ihsan.entities.BankDeposit;
import com.ihsan.entities.BankTransfer;
import com.ihsan.entities.CouponType;
import com.ihsan.entities.FirstTitle;
import com.ihsan.entities.Gender;
import com.ihsan.entities.GiftType;
import com.ihsan.entities.NewProjectCountry;
import com.ihsan.entities.NewProjectType;
import com.ihsan.entities.SponsorshipCountry;
import com.ihsan.entities.SponsorshipType;
import com.ihsan.entities.charityBoxes.CharityBoxActionType;
import com.ihsan.entities.charityBoxes.CharityBoxCategory;
import com.ihsan.entities.charityBoxes.CharityBoxStatus;
import com.ihsan.entities.charityBoxes.CharityBoxType;
import com.ihsan.entities.charityBoxes.Emarah;
import com.ihsan.entities.charityBoxes.Location;
import com.ihsan.entities.charityBoxes.Region;
import com.ihsan.entities.charityBoxes.SubLocation;

@Service
public class CacheService {

	private static final Logger logger = LoggerFactory.getLogger(CacheService.class);

	@Autowired
	private NewProjectTypeRepository newProjectTypeRepository;

	@Autowired
	private CouponTypeRepository couponTypeRepository;

	@Autowired
	private BankChequeRepository bankChequeRepository;

	@Autowired
	private BankTransferRepository bankTransferRepository;

	@Autowired
	private ErrorCodeRepository errorCodeRepository;

	@Autowired
	private CharityBoxCategoryRepository charityBoxCategoryRepository;

	@Autowired
	private CharityBoxStatusRepository charityBoxStatusRepository;

	@Autowired
	private CharityBoxTypeRepository charityBoxTypeRepository;

	@Autowired
	private EmarahRepository emarahRepository;

	@Autowired
	private RegionRepository regionRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private SubLocationRepository subLocationRepository;

	@Autowired
	private CharityBoxActionTypeRepository charityBoxActionTypeRepository;

	@Autowired
	private GiftTypeRepository giftTypeRepository;

	@Autowired
	private FirstTitleRepository firstTitleRepository;

	@Autowired
	private SponsorshipCountryRepository sponsorshipCountryRepository;

	@Autowired
	private SponsorshipTypeRepository sponsorshipTypeRepository;

	@Autowired
	private NewProjectCountryRepository newProjectCountryRepository;

	@Autowired
	private GenderRepository genderRepository;

	@Autowired
	private BankDepositRepository bankDepositRepository;

	public void refreshAllCaches() {
		DateTime startDate = DateTime.now();
		logger.info("######### refreshAllCaches started at: " + startDate);

		newProjectTypeRepository.clearNewProjectTypesCache();
		List<NewProjectType> newProjectTypesList = newProjectTypeRepository.getNewProjectTypes();
		logger.info("######### newProjectTypesList: " + newProjectTypesList.size());
		couponTypeRepository.clearCouponsTypesCache();
		List<CouponType> couponTypeList = couponTypeRepository.getCoupons();
		logger.info("######### couponTypeList: " + couponTypeList.size());

		bankChequeRepository.clearBankChequeCache();
		List<BankCheque> bankChequeList = bankChequeRepository.getBanks();
		logger.info("######### bankChequeList: " + bankChequeList.size());
		bankTransferRepository.clearBankTransferCache();
		List<BankTransfer> bankTransferList = bankTransferRepository.findAll();
		logger.info("######### bankTransferList: " + bankTransferList.size());

		DateTime endDate = DateTime.now();
		int seconds = Seconds.secondsBetween(startDate, endDate).getSeconds();
		logger.info("######### refreshAllCaches ended at: " + endDate + ", it took " + seconds + " seconds");

		charityBoxStatusRepository.clearCharityBoxStatusCache();
		List<CharityBoxStatus> charityBoxStatusList = charityBoxStatusRepository.getCharityBoxStatusList();
		logger.info("######### charityBoxStatusList: " + charityBoxStatusList.size());

		charityBoxCategoryRepository.clearCharityBoxCategoryCache();
		List<CharityBoxCategory> charityBoxCategoryList = charityBoxCategoryRepository.getCharityBoxCategoryList();
		logger.info("######### charityBoxCategoryList: " + charityBoxCategoryList.size());

		charityBoxTypeRepository.clearCharityBoxTypeCache();
		List<CharityBoxType> charityBoxTypeList = charityBoxTypeRepository.findAll();
		logger.info("######### charityBoxTypeList: " + charityBoxTypeList.size());

		regionRepository.clearCharityBoxRegionCache();
		List<Region> regionsList = regionRepository.findAll();
		logger.info("######### regionsList: " + regionsList.size());

		locationRepository.clearCharityBoxLocationCache();
		List<Location> locationsList = locationRepository.findAll();
		logger.info("######### locationsList: " + locationsList.size());

		subLocationRepository.clearCharityBoxSubLocationCache();
		List<SubLocation> subLocationsList = subLocationRepository.findAll();
		logger.info("######### subLocationsList: " + subLocationsList.size());

		charityBoxActionTypeRepository.clearCharityBoxActionTypeCache();
		List<CharityBoxActionType> charityBoxActionTypeList = charityBoxActionTypeRepository
				.getCharityBoxActionTypeList();
		logger.info("######### charityBoxActionTypeList: " + charityBoxActionTypeList.size());

		emarahRepository.clearEmarahCache();
		List<Emarah> emarahList = emarahRepository.getEmarahList();
		logger.info("######### emarahList: " + emarahList.size());

		giftTypeRepository.clearGiftTypeCache();
		List<GiftType> giftTypeList = giftTypeRepository.getGiftTypes();
		logger.info("######### giftTypeList: " + giftTypeList.size());
		firstTitleRepository.clearFirstTitleCache();
		List<FirstTitle> firstTitleList = firstTitleRepository.getFirstTitles();
		logger.info("######### firstTitleList: " + firstTitleList.size());

		sponsorshipTypeRepository.clearSponsorshipTypesCache();
		List<SponsorshipType> sponsorshipTypeList = sponsorshipTypeRepository.getSponsorshipTypes();
		logger.info("######### sponsorshipTypeList: " + sponsorshipTypeList.size());

		newProjectCountryRepository.clearAllCountriesCache();
		List<NewProjectCountry> countriesList = newProjectCountryRepository.findAll();
		logger.info("######### countriesList: " + countriesList.size());
		genderRepository.clearAllGendersCache();
		List<Gender> genderList = genderRepository.findAll();
		logger.info("######### genderList: " + genderList.size());

		sponsorshipCountryRepository.clearAllNationalitiesCache();
		List<SponsorshipCountry> nationalitiesList = sponsorshipCountryRepository.findAll();
		logger.info("######### nationalitiesList: " + nationalitiesList.size());

		bankDepositRepository.clearBankDepositCache();
		List<BankDeposit> bankDepositList = bankDepositRepository.findAll();
		logger.info("######### bankDepositList: " + bankDepositList.size());

		errorCodeRepository.clearErrorCodesCache();
		newProjectCountryRepository.clearNewProjectCountriesCache();
		couponTypeRepository.clearCouponImageCache();
		newProjectCountryRepository.clearCountryImageCache();
		sponsorshipCountryRepository.clearSponsorshipTypeCountriesCache();
		sponsorshipCountryRepository.clearCountrySponsorshipImageCache();
		newProjectTypeRepository.clearNewProjectTypeImageCache();

	}

}
