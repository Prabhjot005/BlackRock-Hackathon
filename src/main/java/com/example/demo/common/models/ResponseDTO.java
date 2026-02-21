package com.example.demo.common.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO implements Serializable {
    private static final long serialVersionUID = 3683617477370152234L;

    private ResponseMessage responseMessage = new ResponseMessage();
    private Object responseData;
    private ErrorData errorData = new ErrorData();
    private String type;
    private String title;
    private String state;
}