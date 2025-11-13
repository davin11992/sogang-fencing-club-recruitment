package com.sogangfencingclub.recruitment.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.sogangfencingclub.recruitment.dto.ApplicantRequest;
import com.sogangfencingclub.recruitment.entity.Applicant;
import com.sogangfencingclub.recruitment.entity.Recruitment;
import com.sogangfencingclub.recruitment.repository.ApplicantRepository;
import com.sogangfencingclub.recruitment.repository.RecruitmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ApplicantServiceTest {
    @Autowired
    private ApplicantService applicantService;

    @Autowired
    private RecruitmentRepository recruitmentRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    private Recruitment recruitment;

    @BeforeEach
    void setup() {
        recruitment = new Recruitment();
        recruitment.setTitle("펜싱 동아리 신입 모집");
        recruitment.setDescription("2025학년도 펜싱부 신입부원 모집");
        recruitment.setActive(true);
        recruitmentRepository.save(recruitment);
    }

    @Test
    void 지원서_정상등록() {
        // given
        ApplicantRequest request = new ApplicantRequest();
        request.setName("이다빈");
        request.setMajor("컴퓨터공학");
        request.setPhone("010-1234-5678");
        request.setMotivation("펜싱이 너무 좋아서 지원합니다.");
        request.setInterviewAvailableTime("주말 오후 가능");

        // when
        Applicant saved = applicantService.createApplicant(recruitment.getId(), request);

        // then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getRecruitment().getId()).isEqualTo(recruitment.getId());
        assertThat(saved.getStatus()).isEqualTo(Applicant.Status.APPLIED);
    }

    @Test
    void 지원자_상태_변경() {
        // given
        ApplicantRequest request = new ApplicantRequest();
        request.setName("홍길동");
        request.setMajor("체육학과");
        request.setPhone("010-9999-8888");
        request.setMotivation("운동을 좋아합니다.");
        request.setInterviewAvailableTime("평일 오후 가능");
        Applicant saved = applicantService.createApplicant(recruitment.getId(), request);

        // when
        applicantService.updateApplicantStatus(saved.getId(), Applicant.Status.PASSED);

        // then
        Applicant updated = applicantRepository.findById(saved.getId()).get();
        assertThat(updated.getStatus()).isEqualTo(Applicant.Status.PASSED);
    }
}
