package com.example.demo.common.utils;

import com.example.demo.common.models.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ResponseUtil {
    public static ResponseEntity<ResponseDTO> sendResponse(Object object, Timestamp landingTime, HttpStatus status,
                                                           Integer actualStatus, String type, String title) {
        ResponseDTO response = new ResponseDTO();
        // set response message
        response.getResponseMessage().setLandingTime(landingTime);
        response.getResponseMessage().setResponseTime(Timestamp.valueOf(LocalDateTime.now()));
        response.getResponseMessage().setMessage("SUCCESS");
        response.getResponseMessage().setStatus(0);
        response.getResponseMessage().setHttpStatus(actualStatus);
        response.setTitle(title);
        response.setType(type);
        response.setResponseData(object);
        return new ResponseEntity<ResponseDTO>(response, status);
    }


    public static ResponseEntity<ResponseDTO> sendErrorResponse(String errorCode, String errorMessage,
                                                                Timestamp landingTime, HttpStatus status, Integer actualStatus) {
        ResponseDTO errorResponse = new ResponseDTO();
        // set response message
        errorResponse.getResponseMessage().setLandingTime(landingTime);
        errorResponse.getResponseMessage().setResponseTime(Timestamp.valueOf(LocalDateTime.now()));
        errorResponse.getResponseMessage().setMessage("FAILURE");
        errorResponse.getErrorData().setErrorCode(errorCode);
        errorResponse.getErrorData().setErrorMessage(errorMessage);
        errorResponse.getResponseMessage().setStatus(1);
        errorResponse.getResponseMessage().setHttpStatus(actualStatus);
        return new ResponseEntity<ResponseDTO>(errorResponse, status);
    }
}
