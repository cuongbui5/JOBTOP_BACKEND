package com.example.jobs_top.controller;

import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.service.StripeService;
import com.example.jobs_top.utils.Constants;
import com.stripe.exception.StripeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {
    private final StripeService stripeService;


    public CheckoutController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/{planId}")
    public ResponseEntity<?> checkout(@PathVariable Long planId){
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        stripeService.checkoutPlan(planId)
                )
        );

    }

}
