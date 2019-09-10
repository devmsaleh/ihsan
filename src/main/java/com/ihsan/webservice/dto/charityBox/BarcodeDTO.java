package com.ihsan.webservice.dto.charityBox;

import java.math.BigInteger;

import com.ihsan.enums.CharityBoxActionTypeEnum;

public class BarcodeDTO {

	private String barcode;
	private CharityBoxActionTypeEnum actionType;
	private boolean route;
	private BigInteger subLocationId;
	private String number;

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public CharityBoxActionTypeEnum getActionType() {
		return actionType;
	}

	public void setActionType(CharityBoxActionTypeEnum actionType) {
		this.actionType = actionType;
	}

	public boolean isRoute() {
		return route;
	}

	public void setRoute(boolean route) {
		this.route = route;
	}

	public BigInteger getSubLocationId() {
		return subLocationId;
	}

	public void setSubLocationId(BigInteger subLocationId) {
		this.subLocationId = subLocationId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

}
