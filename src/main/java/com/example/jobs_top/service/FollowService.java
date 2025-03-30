package com.example.jobs_top.service;

import com.example.jobs_top.model.Follow;
import com.example.jobs_top.model.RecruiterProfile;
import com.example.jobs_top.model.User;
import com.example.jobs_top.repository.FollowRepository;
import com.example.jobs_top.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {
    private final FollowRepository followRepository;
    private final RecruiterProfileService recruiterProfileService;

    public FollowService(FollowRepository followRepository, RecruiterProfileService recruiterProfileService) {
        this.followRepository = followRepository;
        this.recruiterProfileService = recruiterProfileService;
    }

    public Follow getFollowByUserAndRecruiter(Long recruiterId) {
        User user=Utils.getUserFromContext();
        return followRepository.findByUserIdAndRecruiterId(user.getId(),recruiterId).orElse(null);
    }



    public Follow followRecruiter(Long recruiterId){
        RecruiterProfile recruiterProfile=recruiterProfileService.getRecruiterProfileById(recruiterId);
        User user=Utils.getUserFromContext();
        if(followRepository.existsByUserIdAndRecruiterId(user.getId(),recruiterId)){
            throw new RuntimeException("Bạn đã follow công ty này rồi");
        }
        Follow follow=new Follow();
        follow.setUser(Utils.getUserFromContext());
        follow.setRecruiter(recruiterProfile);
        return followRepository.save(follow);

    }
    public void unFollowRecruiter(Long followId){
        followRepository.deleteById(followId);

    }

    public List<RecruiterProfile> getFollowedCompaniesByUser() {
        User user=Utils.getUserFromContext();
        return followRepository.findFollowedCompaniesByUserId(user.getId());
    }
}
