package com.example.jobs_top.service;

import com.example.jobs_top.dto.res.RecruiterJobCountDto;
import com.example.jobs_top.dto.view.RecruiterProfileView;
import com.example.jobs_top.model.RecruiterProfile;
import com.example.jobs_top.model.User;
import com.example.jobs_top.repository.RecruiterProfileRepository;
import com.example.jobs_top.security.UserPrincipal;
import com.example.jobs_top.utils.Utils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RecruiterProfileService {
    private final RecruiterProfileRepository recruiterProfileRepository;

    public RecruiterProfileService(RecruiterProfileRepository recruiterProfileRepository) {
        this.recruiterProfileRepository = recruiterProfileRepository;
    }
    @Transactional
    public RecruiterProfile saveRecruiterProfile(RecruiterProfile recruiterProfile) {
        User user=Utils.getUserFromContext();
        if(recruiterProfile.getId()==null) {
            recruiterProfile.setUser(user);
            return recruiterProfileRepository.save(recruiterProfile);
        }
        Optional<RecruiterProfile> recruiterProfileOptional=recruiterProfileRepository.findById(recruiterProfile.getId());
        RecruiterProfile recruiterProfileUpdated=recruiterProfileOptional.get();
        recruiterProfileUpdated.setCompanyLogo(recruiterProfile.getCompanyLogo());
        recruiterProfileUpdated.setCompanyName(recruiterProfile.getCompanyName());
        recruiterProfileUpdated.setDescription(recruiterProfile.getDescription());
        recruiterProfileUpdated.setCompanyAddress(recruiterProfile.getCompanyAddress());
        recruiterProfileUpdated.setNation(recruiterProfile.getNation());
        recruiterProfileUpdated.setCompanySize(recruiterProfile.getCompanySize());
        recruiterProfileUpdated.setCompanyWebsite(recruiterProfile.getCompanyWebsite());
        recruiterProfileUpdated.setCategory(recruiterProfile.getCategory());
        return recruiterProfileRepository.save(recruiterProfileUpdated);
    }


    @Transactional
    public RecruiterProfile getRecruiterProfileById(Long id) {
        return recruiterProfileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("RecruiterProfile not found with id: " + id));
    }


    @Transactional
    public List<RecruiterProfile> getAllRecruiterProfiles() {
        return recruiterProfileRepository.findAll();
    }


    public void deleteRecruiterProfile(Long id) {
        RecruiterProfile recruiterProfile = getRecruiterProfileById(id);
        recruiterProfileRepository.delete(recruiterProfile);
    }



    public <T> T getRecruiterProfileByUser(Class<T> type) {
        User user=Utils.getUserFromContext();
        return recruiterProfileRepository.findByUserId(user.getId(),type).orElseThrow(()->new RuntimeException("Chưa có thông tin nhà tuyển dụng"));
    }
}
