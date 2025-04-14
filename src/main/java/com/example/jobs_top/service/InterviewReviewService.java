package com.example.jobs_top.service;

import com.example.jobs_top.dto.req.CreateReview;
import com.example.jobs_top.dto.res.PaginatedResponse;
import com.example.jobs_top.dto.view.ReviewView;
import com.example.jobs_top.model.Account;
import com.example.jobs_top.model.InterviewReview;
import com.example.jobs_top.model.InterviewSchedule;
import com.example.jobs_top.repository.InterviewReviewRepository;
import com.example.jobs_top.repository.InterviewScheduleRepository;
import com.example.jobs_top.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class InterviewReviewService {
    private final InterviewReviewRepository interviewReviewRepository;
    private final InterviewScheduleRepository interviewScheduleRepository;

    public InterviewReviewService(InterviewReviewRepository interviewReviewRepository, InterviewScheduleRepository interviewScheduleRepository) {
        this.interviewReviewRepository = interviewReviewRepository;
        this.interviewScheduleRepository = interviewScheduleRepository;
    }


    @Transactional
    public InterviewReview save(CreateReview createReview) {
        InterviewSchedule interviewSchedule=interviewScheduleRepository
                .findById(createReview.getInterviewScheduleId())
                .orElseThrow(()->new RuntimeException("Not found schedule"));

        InterviewReview review=new InterviewReview();
        review.setReviewer(Utils.getAccount());
        review.setRating(createReview.getRating());
        review.setComment(createReview.getComment());
        review.setInterviewSchedule(interviewSchedule);
        review.setJobId(createReview.getJobId());

        return interviewReviewRepository.save(review);

    }



    public Map<Integer, Integer> getRatingStatistics(Long jobId) {
        List<Object[]> results = interviewReviewRepository.getRatingStatisticsByJobId(jobId);
        Map<Integer, Integer> ratingStats = new LinkedHashMap<>();

        for (Object[] row : results) {
            Integer rating = ((Number) row[0]).intValue();
            Integer count = ((Number) row[1]).intValue();
            ratingStats.put(rating, count);
        }

        return ratingStats;
    }

    public InterviewReview updateReview(Long id, CreateReview createReview) {
        InterviewReview interviewReview=interviewReviewRepository.findById(id).orElseThrow(()->new RuntimeException("No review found"));
        interviewReview.setRating(createReview.getRating());
        interviewReview.setComment(createReview.getComment());
        return interviewReviewRepository.save(interviewReview);
    }

    public PaginatedResponse<?> getAllReview(Long jobId, int page, int size) {
        Pageable pageable= PageRequest.of(page-1,size);
        Page<ReviewView> interviewReviewPage=interviewReviewRepository.findByJobId(jobId,pageable);
        return new PaginatedResponse<>(interviewReviewPage.getContent(),interviewReviewPage.getTotalPages(),page,interviewReviewPage.getTotalElements());

    }

    public InterviewReview getReviewByScheduleId(Long scheduleId) {
        Account account=Utils.getAccount();
        return interviewReviewRepository.findByInterviewScheduleIdAndReviewerId(scheduleId,account.getId());
    }
}
