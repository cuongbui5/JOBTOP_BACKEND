package com.example.jobs_top.service;

import com.example.jobs_top.model.User;
import com.example.jobs_top.model.UserProfile;
import com.example.jobs_top.repository.UserProfileRepository;
import com.example.jobs_top.repository.UserRepository;
import com.example.jobs_top.security.UserPrincipal;
import com.example.jobs_top.utils.Utils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;



    public UserProfileService(UserProfileRepository userProfileRepository, UserRepository userRepository) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    public UserProfile saveUserProfile(UserProfile userProfile) {
        User user=Utils.getUserFromContext();
        if(userProfile.getId()==null){
            userProfile.setUser(user);
            return userProfileRepository.save(userProfile);
        }
        Optional<UserProfile> userProfileOptional=userProfileRepository.findById(userProfile.getId());
        UserProfile userProfileUpdate=userProfileOptional.get();
        userProfileUpdate.setPhone(userProfile.getPhone());
        userProfileUpdate.setFullName(userProfile.getFullName());
        userProfileUpdate.setAddress(userProfile.getAddress());
        userProfileUpdate.setSkills(userProfile.getSkills());
        userProfileUpdate.setDescription(userProfile.getDescription());
        userProfileUpdate.setImage(userProfile.getImage());
        userProfileUpdate.setEducation(userProfile.getEducation());
        userProfileUpdate.setDateOfBirth(userProfile.getDateOfBirth());
        userProfileUpdate.setGender(userProfile.getGender());
        userProfileUpdate.setPublicProfile(userProfile.getPublicProfile());
        return userProfileRepository.save(userProfileUpdate);

    }
    public UserProfile getUserProfileByUser() {
        User user=Utils.getUserFromContext();
        return userProfileRepository.findByUserId(user.getId()).orElseThrow(()->new RuntimeException("Bạn chưa có thông tin profile!"));
    }


    public UserProfile getUserProfileById(Long id) {
        return userProfileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("UserProfile not found with id: " + id));
    }







    public List<UserProfile> getAllUserProfiles() {
        return userProfileRepository.findAll();
    }


    public void deleteUserProfile(Long id) {
        UserProfile userProfile = getUserProfileById(id);
        userProfileRepository.delete(userProfile);
    }

    public UserProfile getUserProfileByUserId(Long userId) {
        User user=userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("User not found with id: " + userId));
        return userProfileRepository.findByUserId(user.getId()).orElseThrow(()->new RuntimeException("Không tìm thấy  thông tin!"));

    }
}
