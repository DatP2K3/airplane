package com.airplane.schedule.dto.response;

import com.airplane.schedule.dto.ApiResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
public class PageApiResponse<T> extends ApiResponse<T> {
    private PageableResponse pageable;

    public static <T> PageApiResponse<T> fail(RuntimeException exception) {
        return PageApiResponse.<T>builder()
                .message(exception.getMessage())
                .success(false)
                .code(500)
                .exception(exception)
                .timestamp(System.currentTimeMillis())
                .build();
    }

    @Data
    @Builder
    public static class PageableResponse {
        private int pageIndex;
        private int pageSize;
    }
}