package com.sogangfencingclub.recruitment.controller;

import com.sogangfencingclub.recruitment.entity.Recruitment;
import com.sogangfencingclub.recruitment.exception.CustomException;
import com.sogangfencingclub.recruitment.exception.ErrorCode;
import com.sogangfencingclub.recruitment.service.RecruitmentService;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recruitments")
public class RecruitmentController {
    private final RecruitmentService recruitmentService;

    public RecruitmentController(RecruitmentService recruitmentService) {
        this.recruitmentService = recruitmentService;
    }

    // CREATE
    @PostMapping
    public Recruitment create(@RequestBody Recruitment recruitment) {
        return recruitmentService.createRecruitment(recruitment);
    }

    // READ (전체 조회)
    @GetMapping
    public List<Recruitment> getAll() {
        return recruitmentService.getAllRecruitments();
    }

    // READ (단일 조회)
    @GetMapping("/{id}")
    public Recruitment getOne(@PathVariable Long id) {
        return recruitmentService.getRecruitment(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RECRUITMENT_NOT_FOUND));
    }

    // UPDATE
    @PutMapping("/{id}")
    public Recruitment update(@PathVariable Long id, @RequestBody Recruitment updated) {
        return recruitmentService.updateRecruitment(id, updated);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        recruitmentService.deleteRecruitment(id);
    }
}
