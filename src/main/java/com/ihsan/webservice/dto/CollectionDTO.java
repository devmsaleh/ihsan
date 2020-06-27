package com.ihsan.webservice.dto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CollectionDTO {

	private BigInteger delegateId;
	private BigInteger supervisorId;
	private List<BigInteger> ignoredReceiptsList = new ArrayList<BigInteger>();

	@Override
	public String toString() {
		return "CollectionDTO [delegateId=" + delegateId + ", supervisorId=" + supervisorId + ", ignoredReceiptsList="
				+ ignoredReceiptsList + "]";
	}

	public BigInteger getDelegateId() {
		return delegateId;
	}

	public void setDelegateId(BigInteger delegateId) {
		this.delegateId = delegateId;
	}

	public BigInteger getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(BigInteger supervisorId) {
		this.supervisorId = supervisorId;
	}

	public List<BigInteger> getIgnoredReceiptsList() {
		return ignoredReceiptsList;
	}

	public void setIgnoredReceiptsList(List<BigInteger> ignoredReceiptsList) {
		this.ignoredReceiptsList = ignoredReceiptsList;
	}

}
