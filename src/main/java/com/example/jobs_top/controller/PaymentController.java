package com.example.jobs_top.controller;

import com.example.jobs_top.dto.req.CreateAccountPlan;
import com.example.jobs_top.service.AccountPlanService;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final AccountPlanService accountPlanService;

    private final String signingSecret="whsec_245f214792a91f727c3eb844d4c3f2e59379f1e60b7f087a8e9aeb004f620d80";

    public PaymentController(AccountPlanService accountPlanService) {
        this.accountPlanService = accountPlanService;
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handlePaymentWebhook(@RequestBody String payload,
                                                       @RequestHeader("Stripe-Signature") String signature) {
        try {
            System.out.println("get event");
            // Xác thực và xử lý sự kiện webhook từ Stripe
            Event event = Webhook.constructEvent(payload, signature, signingSecret);


            // Kiểm tra nếu sự kiện thanh toán thành công
            if ("payment_intent.succeeded".equals(event.getType())) {
                System.out.println("Payment successful");
                PaymentIntent paymentIntent = (PaymentIntent) event.getData().getObject();
                String accountId = paymentIntent.getMetadata().get("accountId");
                String planId = paymentIntent.getMetadata().get("planId");
                System.out.println(accountId+" "+planId);
                accountPlanService.createAccountPlan(new CreateAccountPlan(Long.parseLong(planId),Long.parseLong(accountId)));

                // Lưu thông tin thanh toán vào cơ sở dữ liệu
                //paymentService.savePaymentData(paymentIntent);
            }

            // Trả lời với Stripe rằng Webhook đã được nhận
            return ResponseEntity.ok("Webhook received");

        } catch (Exception e) {
            // Xử lý lỗi khi không thể xác thực webhook hoặc khi có lỗi xảy ra
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Webhook error: " + e.getMessage());
        }
    }
}
