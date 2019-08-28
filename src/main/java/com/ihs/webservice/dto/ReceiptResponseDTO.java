package com.ihs.webservice.dto;

import java.math.BigInteger;

public class ReceiptResponseDTO {

    private BigInteger receiptId;

    public ReceiptResponseDTO() {

    }

    public ReceiptResponseDTO(BigInteger receiptId) {
        this.receiptId = receiptId;
    }

    public BigInteger getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(BigInteger receiptId) {
        this.receiptId = receiptId;
    }

}
