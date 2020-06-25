package com.ihsan.webservice.dto.charityBox;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.ihsan.enums.CharityBoxActionTypeEnum;

public class CharityBoxTransferDTO {

	private BigInteger charityBoxId;
	private BigInteger newCharityBoxId;
	private BigInteger regionId;
	private BigInteger locationId;
	private BigInteger subLocationId;
	private Float updateSublocationLatitude;
	private Float updateSublocationLongitude;
	private RegionDTO newRegionDTO = new RegionDTO();
	private LocationDTO newLocationDTO = new LocationDTO();
	private SubLocationDTO newSubLocationDTO = new SubLocationDTO();
	private BigInteger delegateId;
	private CharityBoxActionTypeEnum actionType;
	private String notes;
	private List<BigInteger> attachments = new ArrayList<BigInteger>();
	private BigInteger safetyCaseId;
	private boolean subLocationTemporaryClosed;
	private int subLocationRating;

	public BigInteger getCharityBoxId() {
		return charityBoxId;
	}

	public void setCharityBoxId(BigInteger charityBoxId) {
		this.charityBoxId = charityBoxId;
	}

	public BigInteger getNewCharityBoxId() {
		return newCharityBoxId;
	}

	public void setNewCharityBoxId(BigInteger newCharityBoxId) {
		this.newCharityBoxId = newCharityBoxId;
	}

	public BigInteger getSubLocationId() {
		return subLocationId;
	}

	public void setSubLocationId(BigInteger subLocationId) {
		this.subLocationId = subLocationId;
	}

	public RegionDTO getNewRegionDTO() {
		return newRegionDTO;
	}

	public void setNewRegionDTO(RegionDTO newRegionDTO) {
		this.newRegionDTO = newRegionDTO;
	}

	public LocationDTO getNewLocationDTO() {
		return newLocationDTO;
	}

	public void setNewLocationDTO(LocationDTO newLocationDTO) {
		this.newLocationDTO = newLocationDTO;
	}

	public SubLocationDTO getNewSubLocationDTO() {
		return newSubLocationDTO;
	}

	public void setNewSubLocationDTO(SubLocationDTO newSubLocationDTO) {
		this.newSubLocationDTO = newSubLocationDTO;
	}

	public BigInteger getDelegateId() {
		return delegateId;
	}

	public void setDelegateId(BigInteger delegateId) {
		this.delegateId = delegateId;
	}

	public CharityBoxActionTypeEnum getActionType() {
		return actionType;
	}

	public void setActionType(CharityBoxActionTypeEnum actionType) {
		this.actionType = actionType;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public BigInteger getRegionId() {
		return regionId;
	}

	public void setRegionId(BigInteger regionId) {
		this.regionId = regionId;
	}

	public BigInteger getLocationId() {
		return locationId;
	}

	public void setLocationId(BigInteger locationId) {
		this.locationId = locationId;
	}

	public List<BigInteger> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<BigInteger> attachments) {
		this.attachments = attachments;
	}

	public Float getUpdateSublocationLatitude() {
		return updateSublocationLatitude;
	}

	public void setUpdateSublocationLatitude(Float updateSublocationLatitude) {
		this.updateSublocationLatitude = updateSublocationLatitude;
	}

	public Float getUpdateSublocationLongitude() {
		return updateSublocationLongitude;
	}

	public void setUpdateSublocationLongitude(Float updateSublocationLongitude) {
		this.updateSublocationLongitude = updateSublocationLongitude;
	}

	public BigInteger getSafetyCaseId() {
		return safetyCaseId;
	}

	public void setSafetyCaseId(BigInteger safetyCaseId) {
		this.safetyCaseId = safetyCaseId;
	}

	public boolean isSubLocationTemporaryClosed() {
		return subLocationTemporaryClosed;
	}

	public void setSubLocationTemporaryClosed(boolean subLocationTemporaryClosed) {
		this.subLocationTemporaryClosed = subLocationTemporaryClosed;
	}

	public int getSubLocationRating() {
		return subLocationRating;
	}

	public void setSubLocationRating(int subLocationRating) {
		this.subLocationRating = subLocationRating;
	}

}
