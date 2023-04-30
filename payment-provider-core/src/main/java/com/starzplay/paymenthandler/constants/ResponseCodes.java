package com.starzplay.paymenthandler.constants;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseCodes {

    CREATED(HttpStatus.CREATED.value(), "Created Successfully"),
    SUCCESS(HttpStatus.OK.value(), "Success"),
    ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server Error"),
    NO_DATA_FOUND(2, "No Data Found"),
    NO_DATA_FOUND_BY_PAYMENT_METHOD_NAME(3, "No Data Found Against Payment Method Name"),
    NO_DATA_FOUND_BY_PAYMENT_METHOD_ID(4, "No Data Found Against Payment Method Id"),
    PAYMENT_METHOD_ALREADY_EXISTS(5, "Payment Method Already Exists"),
    PAYMENT_METHOD_DOES_NOT_EXISTS(6, "Payment Method Does Not Exists");


    private final int resultCode;
    private final String resultDescription;

    ResponseCodes(int resultCode, String resultDescription){

        this.resultCode = resultCode;
        this.resultDescription = resultDescription;
    }
}
