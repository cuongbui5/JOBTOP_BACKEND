package com.example.jobs_top.service;

import com.example.jobs_top.model.Resume;
import com.example.jobs_top.model.User;
import com.example.jobs_top.repository.ResumeRepository;
import com.example.jobs_top.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeService {
    private final ResumeRepository resumeRepository;

    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    public List<Resume> getAllResume() {
        return resumeRepository.findAll();
    }

    public List<Resume> getAllResumeByUser() {
        User user= Utils.getUserFromContext();
        return resumeRepository.findByUserId(user.getId());
    }

    public Resume getResumeById(Long resumeId) {
        return resumeRepository.findById(resumeId).get();
    }

    public Resume createResume(Resume resume) {
        resume.setUser(Utils.getUserFromContext());
        return resumeRepository.save(resume);
    }

    public Resume updateResume(Long id,Resume resume) {
        Resume cv=resumeRepository.findById(id).orElseThrow(()->new RuntimeException("resume not found"));
        cv.setName(resume.getName());
        cv.setLink(resume.getLink());
        return resumeRepository.save(cv);
    }
    public void deleteResume(Long id) {
        resumeRepository.deleteById(id);
    }
}
