package com.it.rabo.lostandfound.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    @JsonInclude(JsonInclude. Include. NON_NULL)
    private Object data;
    @JsonInclude(JsonInclude. Include. NON_NULL)
    private String message;
    private int status;

    public ApiResponse(String message, int httpStatus) {
        this.message = message;
        this.status = httpStatus;
    }

    public ApiResponse(Object data, int httpStatus) {
        this.data = data;
        this.status = httpStatus;
    }

    public static ApiResponse of(String message) {
        return new ApiResponse(message, HttpStatus.CREATED.value());
    }

    public static ApiResponse of(Object data) {
        return new ApiResponse(data, HttpStatus.OK.value());
    }


}