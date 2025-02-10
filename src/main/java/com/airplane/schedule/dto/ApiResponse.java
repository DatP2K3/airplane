package com.airplane.schedule.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> implements Serializable {
    private T data;
    private boolean success;
    private int code;
    private String message;
    private long timestamp;
    private String status;

    @JsonIgnore
    private RuntimeException exception;

    public static <T> ApiResponse<T> fail(RuntimeException exception) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setMessage(exception.getMessage());
        response.setSuccess(false);
        response.setCode(500);
        response.setException(exception);
        response.setTimestamp(System.currentTimeMillis());
        return response;
    }
}