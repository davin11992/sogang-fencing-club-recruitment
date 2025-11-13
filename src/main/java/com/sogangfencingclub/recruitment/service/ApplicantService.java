package com.sogangfencingclub.recruitment.service;

import com.sogangfencingclub.recruitment.dto.ApplicantRequest;
import com.sogangfencingclub.recruitment.entity.Applicant;
import com.sogangfencingclub.recruitment.entity.Applicant.Status;
import com.sogangfencingclub.recruitment.entity.Recruitment;
import com.sogangfencingclub.recruitment.exception.CustomException;
import com.sogangfencingclub.recruitment.exception.ErrorCode;
import com.sogangfencingclub.recruitment.repository.ApplicantRepository;
import com.sogangfencingclub.recruitment.repository.RecruitmentRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ApplicantService {
    private final ApplicantRepository applicantRepository;
    private final RecruitmentRepository recruitmentRepository;

    public ApplicantService(ApplicantRepository applicantRepository, RecruitmentRepository recruitmentRepository) {
        this.applicantRepository = applicantRepository;
        this.recruitmentRepository = recruitmentRepository;
    }

    // 지원서 등록
    @Transactional
    public Applicant createApplicant(Long recruitmentId, ApplicantRequest request) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new CustomException(ErrorCode.RECRUITMENT_NOT_FOUND));

        Applicant applicant = new Applicant();
        applicant.setName(request.getName());
        applicant.setMajor(request.getMajor());
        applicant.setPhone(request.getPhone());
        applicant.setMotivation(request.getMotivation());
        applicant.setInterviewAvailableTime(request.getInterviewAvailableTime());
        applicant.setRecruitment(recruitment);
        applicant.setStatus(Applicant.Status.APPLIED);

        return applicantRepository.save(applicant);
    }

    // 전체 지원자 조회
    public List<Applicant> getAllApplicants() {
        return applicantRepository.findAll();
    }

    // 특정 모집 공고에 지원한 사람들 조회
    public List<Applicant> getApplicantsByRecruitment(Long recruitmentId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new CustomException(ErrorCode.RECRUITMENT_NOT_FOUND));
        return applicantRepository.findByRecruitment(recruitment);
    }

    // 단일 지원자 조회
    public Applicant getApplicant(Long id) {
        return applicantRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.APPLICANT_NOT_FOUND));
    }

    // 지원자 상태 변경 (합격/불합격)
    @Transactional
    public Applicant updateApplicantStatus(Long applicantId, Status status) {
        Applicant applicant = applicantRepository.findById(applicantId)
                .orElseThrow(() -> new CustomException(ErrorCode.APPLICANT_NOT_FOUND));
        applicant.setStatus(status);
        // JPA의 Dirty Checking으로 자동 업데이트됨 (save 호출 필요 X)
        return applicant;
    }

    // 특정 공고의 합격자 목록 조회
    public List<Applicant> getPassedApplicants(Long recruitmentId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new CustomException(ErrorCode.RECRUITMENT_NOT_FOUND));
        return applicantRepository.findByRecruitmentAndStatus(recruitment, Status.PASSED);
    }
}
