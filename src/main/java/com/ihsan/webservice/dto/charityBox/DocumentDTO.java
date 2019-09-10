package com.ihsan.webservice.dto.charityBox;

import java.math.BigInteger;

public class DocumentDTO {

	private String documentContent;
	private BigInteger delegateId;

	public String getDocumentContent() {
		return documentContent;
	}

	public void setDocumentContent(String documentContent) {
		this.documentContent = documentContent;
	}

	public BigInteger getDelegateId() {
		return delegateId;
	}

	public void setDelegateId(BigInteger delegateId) {
		this.delegateId = delegateId;
	}

}
