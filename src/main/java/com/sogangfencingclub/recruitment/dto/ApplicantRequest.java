package com.sogangfencingclub.recruitment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicantRequest {
    @NotBlank(message = "이름은 필수 입력값입니다.")
    @Size(max = 30, message = "이름은 30자 이하로 입력해주세요.")
    private String name;

    @NotBlank(message = "전공은 필수 입력값입니다.")
    private String major;

    @NotBlank(message = "전화번호는 필수 입력값입니다.")
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호 형식은 010-XXXX-XXXX 이어야 합니다.")
    private String phone;

    @NotBlank(message = "지원 동기를 입력해주세요.")
    @Size(max = 500, message = "지원 동기는 500자 이하로 입력해주세요.")
    private String motivation;

    @NotBlank(message = "면접 가능 시간을 입력해주세요.")
    private String interviewAvailableTime;
}
