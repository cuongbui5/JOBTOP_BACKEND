package com.example.jobs_top.controller;

import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.service.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follows")
public class FollowController {
    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }
    @GetMapping("/{companyId}")
    public ResponseEntity<?> getFollowByUser(@PathVariable Long companyId) {

        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"Success",followService.getFollowByAccountAndCompany(companyId))
        );
    }

    @GetMapping("/followed-companies")
    public ResponseEntity<?> getAllCompanies() {

        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"Success",followService.getFollowedCompaniesByAccount())
        );
    }

    // 2. Thực hiện theo dõi công ty
    @PostMapping("/{companyId}")
    public ResponseEntity<?> followRecruiter(@PathVariable Long companyId) {

        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"Success",followService.followCompany(companyId))
        );
    }

    // 3. Bỏ theo dõi công ty
    @DeleteMapping("/{followId}")
    public ResponseEntity<?> unFollowRecruiter(@PathVariable Long followId) {
        followService.unFollowRecruiter(followId);
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"Success",null)
        );
    }

}
