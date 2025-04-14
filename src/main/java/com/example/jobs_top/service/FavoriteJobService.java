package com.example.jobs_top.service;

import com.example.jobs_top.model.FavoriteJob;
import com.example.jobs_top.model.Job;
import com.example.jobs_top.model.Account;
import com.example.jobs_top.repository.FavoriteJobRepository;
import com.example.jobs_top.repository.JobRepository;
import com.example.jobs_top.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteJobService {
    private final FavoriteJobRepository favoriteJobRepository;
    private final JobRepository jobRepository;


    public FavoriteJobService(FavoriteJobRepository favoriteJobRepository, JobRepository jobRepository) {
        this.favoriteJobRepository = favoriteJobRepository;
        this.jobRepository = jobRepository;
    }

    public List<?> getAllFavoriteJobsByAccount() {
        Account account= Utils.getAccount();
        return favoriteJobRepository.findFavoriteJobByAccountId(account.getId());

    }



    public void saveFavoriteJob( Long jobId) {
        Account account= Utils.getAccount();
        Job job=jobRepository.findById(jobId).orElseThrow(()->new RuntimeException("Not found job"));
        if(favoriteJobRepository.existsByAccountIdAndJobId(account.getId(), jobId)){
            throw new RuntimeException("Công việc này đã được thêm vào yêu thích rồi");
        }
        FavoriteJob favoriteJob = new FavoriteJob();
        favoriteJob.setAccount(account);
        favoriteJob.setJob(job);
        favoriteJobRepository.save(favoriteJob);
    }


    public void deleteFavoriteJob(Long id) {
        favoriteJobRepository.deleteById(id);
    }



}
