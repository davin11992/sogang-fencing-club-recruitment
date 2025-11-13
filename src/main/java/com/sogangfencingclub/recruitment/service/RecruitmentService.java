package com.sogangfencingclub.recruitment.service;

import com.sogangfencingclub.recruitment.entity.Recruitment;
import com.sogangfencingclub.recruitment.exception.CustomException;
import com.sogangfencingclub.recruitment.exception.ErrorCode;
import com.sogangfencingclub.recruitment.repository.RecruitmentRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RecruitmentService {
    private final RecruitmentRepository recruitmentRepository;

    public RecruitmentService(RecruitmentRepository recruitmentRepository) {
        this.recruitmentRepository = recruitmentRepository;
    }

    // CREATE
    @Transactional
    public Recruitment createRecruitment(Recruitment recruitment) {
        return recruitmentRepository.save(recruitment);
    }

    // READ (전체 조회)
    public List<Recruitment> getAllRecruitments() {
        return recruitmentRepository.findAll();
    }

    // READ (단일 조회)
    public Optional<Recruitment> getRecruitment(Long id) {
        return recruitmentRepository.findById(id);
    }

    // UPDATE
    @Transactional
    public Recruitment updateRecruitment(Long id, Recruitment updated) {
        Recruitment recruitment = recruitmentRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RECRUITMENT_NOT_FOUND));

        // 필요한 필드만 업데이트
        recruitment.setTitle(updated.getTitle());
        recruitment.setDescription(updated.getDescription());
        recruitment.setStartDate(updated.getStartDate());
        recruitment.setEndDate(updated.getEndDate());
        recruitment.setActive(updated.isActive());

        return recruitment;
    }

    // DELETE
    @Transactional
    public void deleteRecruitment(Long id) {
        recruitmentRepository.deleteById(id);
    }
}
