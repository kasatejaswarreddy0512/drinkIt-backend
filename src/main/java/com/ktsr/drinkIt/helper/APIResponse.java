package com.ktsr.drinkIt.helper;


import com.ktsr.drinkIt.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class APIResponse {

    private APIResponse() {}

    public static ResponseEntity<ResponseWrapper> get(
            ErrorCode errorCode,
            Object responseBody,
            HttpStatus status) {

        ResponseWrapper wrapper = new ResponseWrapper();
        wrapper.setErrorCode(errorCode.getCode());
        wrapper.setErrorMessage(errorCode.getMessage());
        wrapper.setResponseBody(responseBody);

        return new ResponseEntity<>(wrapper, status);
    }
}
