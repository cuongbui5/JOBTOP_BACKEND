package com.example.jobs_top.service;

import com.example.jobs_top.dto.res.StripeResponse;
import com.example.jobs_top.model.Account;
import com.example.jobs_top.model.Plan;
import com.example.jobs_top.repository.PlanRepository;
import com.example.jobs_top.utils.Utils;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service

public class StripeService {
    private final PlanRepository planRepository;
    @Value("${stripe.api-key}")
    private String apiKey;

    @Value("${stripe.publishable-key}")
    private String publishableKey;

    public StripeService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }


    public StripeResponse checkoutPlan(Long planId) {
        Plan plan = planRepository.findById(planId).orElseThrow(()->new IllegalArgumentException("Plan not found"));
        Stripe.apiKey = apiKey;
        Account account= Utils.getAccount();


        SessionCreateParams.LineItem.PriceData.ProductData planData= SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(plan.getName())
                .setDescription(plan.getDescription()).build();

        SessionCreateParams.LineItem.PriceData priceData= SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("VND")
                .setProductData(planData)
                .setUnitAmount(plan.getPrice().longValue()).build();

        SessionCreateParams.LineItem lineItem= SessionCreateParams.LineItem.builder()
                .setQuantity(1L)
                .setPriceData(priceData)
                .build();
        Map<String, String> metadata = new HashMap<>();
        metadata.put("accountId", account.getId().toString());
        metadata.put("planId", plan.getId().toString());
        SessionCreateParams params= SessionCreateParams.builder().setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:5173/payment/success")
                .setCancelUrl("http://localhost:5173/payment/cancel")
                .addLineItem(lineItem)
                .setPaymentIntentData(
                        SessionCreateParams.PaymentIntentData.builder()
                                .putAllMetadata(metadata)
                                .build()

                )
                .build();

        Session session=null;

        try {
            session=Session.create(params);
        } catch (StripeException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return new StripeResponse(session.getId(),session.getUrl());


    }
}
