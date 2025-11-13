package com.sogangfencingclub.recruitment.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.sogangfencingclub.recruitment.entity.Recruitment;
import com.sogangfencingclub.recruitment.repository.RecruitmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class RecruitmentServiceTest {
    @Autowired
    private RecruitmentService recruitmentService;

    @Autowired
    private RecruitmentRepository recruitmentRepository;

    @Test
    void 모집공고_등록_및_조회() {
        // given
        Recruitment recruitment = new Recruitment();
        recruitment.setTitle("2025 펜싱 동아리 신입 모집");
        recruitment.setDescription("활발하고 열정적인 신입 모집");
        recruitment.setActive(true);

        // when
        Recruitment saved = recruitmentService.createRecruitment(recruitment);

        // then
        assertThat(saved.getId()).isNotNull();

        Recruitment found = recruitmentRepository.findById(saved.getId())
                .orElseThrow(() -> new RuntimeException("저장된 모집공고 없음"));
        assertThat(found.getTitle()).isEqualTo("2025 펜싱 동아리 신입 모집");
    }

    @Test
    void 모집공고_수정() {
        // given
        Recruitment recruitment = new Recruitment();
        recruitment.setTitle("임시 제목");
        recruitment.setDescription("수정 전");
        recruitment.setActive(true);
        recruitment = recruitmentRepository.save(recruitment);

        Recruitment update = new Recruitment();
        update.setTitle("수정된 제목");
        update.setDescription("내용 수정됨");
        update.setActive(false);

        // when
        recruitmentService.updateRecruitment(recruitment.getId(), update);

        // then
        Recruitment found = recruitmentRepository.findById(recruitment.getId()).get();
        assertThat(found.getTitle()).isEqualTo("수정된 제목");
        assertThat(found.isActive()).isFalse();
    }
}
