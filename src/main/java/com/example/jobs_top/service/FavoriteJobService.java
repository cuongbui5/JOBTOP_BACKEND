package com.example.jobs_top.service;

import com.example.jobs_top.dto.view.JobCardView;
import com.example.jobs_top.model.FavoriteJob;
import com.example.jobs_top.model.Job;
import com.example.jobs_top.model.User;
import com.example.jobs_top.model.pk.FavoriteJobId;
import com.example.jobs_top.repository.FavoriteJobRepository;
import com.example.jobs_top.repository.JobRepository;
import com.example.jobs_top.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class FavoriteJobService {
    private final FavoriteJobRepository favoriteJobRepository;
    private final JobRepository jobRepository;


    public FavoriteJobService(FavoriteJobRepository favoriteJobRepository, JobRepository jobRepository) {
        this.favoriteJobRepository = favoriteJobRepository;
        this.jobRepository = jobRepository;
    }

    public List<?> getAllFavoriteJobsByUser() {
        User user= Utils.getUserFromContext();
        return favoriteJobRepository.findFavoriteJobByUserId(user.getId());

    }



    public void saveFavoriteJob( Long jobId) {
        User user= Utils.getUserFromContext();
        Job job=jobRepository.findById(jobId).orElseThrow(()->new RuntimeException("Not found job"));
        if(favoriteJobRepository.existsByUserIdAndJobId(user.getId(), jobId)){
            throw new RuntimeException("Công việc này đã được thêm vào yêu thích rồi");
        }
        FavoriteJob favoriteJob = new FavoriteJob();
        favoriteJob.setUser(user);
        favoriteJob.setJob(job);
        favoriteJobRepository.save(favoriteJob);
    }


    public void deleteFavoriteJob(Long id) {
        favoriteJobRepository.deleteById(id);
    }



}
