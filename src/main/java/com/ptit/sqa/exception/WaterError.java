package com.ptit.sqa.exception;

import lombok.Getter;

@Getter
public enum WaterError {

    CUSTOMER_INVOICE_NOT_FOUND(404001, "Customer invoice not found."),
    CUSTOMER_NOT_FOUND(404002, "Customer not found"),

    UNEXPECTED_EXCEPTION(500001, "unknown error");

    WaterError(int errCode, String message) {
        this.errCode = errCode;
        this.message = message;
    }

    private final int errCode;
    private final String message;
}
