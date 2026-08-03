package com.ktsr.drinkIt.helper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseWrapper {

    private String errorCode;
    private String errorMessage;
    private Object responseBody;
}
