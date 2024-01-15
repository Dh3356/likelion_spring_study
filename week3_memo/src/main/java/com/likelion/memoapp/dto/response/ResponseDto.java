package com.likelion.memoapp.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseDto<T> {
    private final int statusCode;
    private final String message;
    private final T data;

    public ResponseDto(final HttpStatus statusCode, final String resultMsg) {
        this.statusCode = statusCode.value();
        this.message = resultMsg;
        this.data = null;
    }

    @Builder
    public ResponseDto(final int statusCode, final String message, final T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseDto<T> res(final HttpStatus statusCode, final String resultMsg) {
        return res(statusCode, resultMsg, null);
    }

    public static <T> ResponseDto<T> res(final HttpStatus statusCode, final String resultMsg, final T t) {
        return ResponseDto.<T>builder()
                .data(t)
                .statusCode(statusCode.value())
                .message(resultMsg)
                .build();
    }
}
