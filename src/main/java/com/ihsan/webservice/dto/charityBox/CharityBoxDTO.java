package com.ihsan.webservice.dto.charityBox;

public class CharityBoxDTO {

	private String id;
	private String name;
	private String barcode;
	private String source;
	private String sourceId;
	private String type;
	private String status;
	private String region;
	private String location;
	private String subLocation;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRegion() {
		if (region == null)
			region = "";
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getLocation() {
		if (location == null)
			location = "";
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSubLocation() {
		if (subLocation == null)
			subLocation = "";
		return subLocation;
	}

	public void setSubLocation(String subLocation) {
		this.subLocation = subLocation;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

}
