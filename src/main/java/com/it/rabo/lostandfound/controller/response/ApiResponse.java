package com.it.rabo.lostandfound.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    @JsonInclude(JsonInclude. Include. NON_NULL)
    private T data;
    @JsonInclude(JsonInclude. Include. NON_NULL)
    private String message;
    private int status;

    public ApiResponse(String message, int httpStatus) {
        this.message = message;
        this.status = httpStatus;
    }

    public ApiResponse(T data, int httpStatus) {
        this.data = data;
        this.status = httpStatus;
    }

}