package com.sogangfencingclub.recruitment.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    RECRUITMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 모집 공고가 존재하지 않습니다."),
    APPLICANT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 지원자가 존재하지 않습니다."),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 요청 파라미터입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다."),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "유효하지 않은 입력값입니다.");


    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
