package com.sogangfencingclub.recruitment.repository;

import com.sogangfencingclub.recruitment.entity.Applicant;
import com.sogangfencingclub.recruitment.entity.Applicant.Status;
import com.sogangfencingclub.recruitment.entity.Recruitment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
    List<Applicant> findByRecruitment(Recruitment recruitment);

    List<Applicant> findByRecruitmentAndStatus(Recruitment recruitment, Status status);
}
