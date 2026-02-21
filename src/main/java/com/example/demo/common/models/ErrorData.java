package com.example.demo.common.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorData implements Serializable {
    private static final long serialVersionUID = 1L;
    //private ErrorMessage errorMessage = new ErrorMessage();
    private Object responseMessage;
    private String errorCode;
    private String errorMessage;
    //private String actualErrorMessage;
    private Timestamp landingTime;
    private Timestamp responseTime;
    private Integer status;

}
