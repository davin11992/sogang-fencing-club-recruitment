package com.sogangfencingclub.recruitment.controller;

import com.sogangfencingclub.recruitment.dto.ApiResponse;
import com.sogangfencingclub.recruitment.dto.ApplicantRequest;
import com.sogangfencingclub.recruitment.entity.Applicant;
import com.sogangfencingclub.recruitment.entity.Applicant.Status;
import com.sogangfencingclub.recruitment.service.ApplicantService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/applicants")
public class ApplicantController {
    private final ApplicantService applicantService;

    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    // 지원서 등록 (특정 모집 공고에 지원)
    @PostMapping("/{recruitmentId}")
    public ResponseEntity<ApiResponse<Applicant>> createApplicant(
            @PathVariable Long recruitmentId,
            @Valid @RequestBody ApplicantRequest request) {

        Applicant saved = applicantService.createApplicant(recruitmentId, request);
        return ResponseEntity.ok(ApiResponse.success("지원 완료", saved));
    }

    // 전체 지원자 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<Applicant>>> getAllApplicants() {
        List<Applicant> applicants = applicantService.getAllApplicants();
        return ResponseEntity.ok(ApiResponse.success("전체 지원자 조회 성공", applicants));
    }

    // 모집 공고별 지원자 조회
    @GetMapping("/recruitment/{recruitmentId}")
    public ResponseEntity<ApiResponse<List<Applicant>>> getApplicantsByRecruitment(@PathVariable Long recruitmentId) {
        List<Applicant> applicants = applicantService.getApplicantsByRecruitment(recruitmentId);
        return ResponseEntity.ok(ApiResponse.success("모집 공고별 지원자 조회 성공", applicants));
    }

    // 단일 지원자 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Applicant>> getApplicant(@PathVariable Long id) {
        Applicant applicant = applicantService.getApplicant(id);
        return ResponseEntity.ok(ApiResponse.success("지원자 조회 성공", applicant));
    }

    // 지원자 상태 변경
    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Applicant>> updateStatus(@PathVariable Long id,
                                                               @RequestParam("status") Status status) {
        Applicant updated = applicantService.updateApplicantStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("상태 변경 완료", updated));
    }

    // 합격자 목록 조회
    @GetMapping("/recruitment/{recruitmentId}/passed")
    public ResponseEntity<ApiResponse<List<Applicant>>> getPassedApplicants(@PathVariable Long recruitmentId) {
        List<Applicant> passed = applicantService.getPassedApplicants(recruitmentId);
        return ResponseEntity.ok(ApiResponse.success("합격자 목록 조회 성공", passed));
    }
}
