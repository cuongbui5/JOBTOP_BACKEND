package com.example.jobs_top.controller;

import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.model.FavoriteJob;
import com.example.jobs_top.service.FavoriteJobService;
import com.example.jobs_top.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorite-jobs")
public class FavoriteJobController {
    private final FavoriteJobService favoriteJobService;

    public FavoriteJobController(FavoriteJobService favoriteJobService) {
        this.favoriteJobService = favoriteJobService;
    }

    @GetMapping
    public ResponseEntity<?> getAllFavoriteJobs() {
        return ResponseEntity.ok().body(new ApiResponse<>(
                200,
                Constants.SUCCESS_MESSAGE,
                favoriteJobService.getAllFavoriteJobsByAccount()));
    }



    @PostMapping("/save")
    public ResponseEntity<?> saveFavoriteJob(@RequestParam("jobId") Long jobId) {
        favoriteJobService.saveFavoriteJob(jobId);
        return ResponseEntity.ok().body(new ApiResponse<>(200,"success",null));
    }


    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> deleteFavoriteJob(@PathVariable Long id) {
        favoriteJobService.deleteFavoriteJob(id);
        return ResponseEntity.ok().body(new ApiResponse<>(200,  Constants.SUCCESS_MESSAGE, null));
    }
}
