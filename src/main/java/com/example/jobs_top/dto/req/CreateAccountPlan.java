package com.example.jobs_top.dto.req;

public class CreateAccountPlan {
    private Long planId;
    private Long accountId;

    public CreateAccountPlan(Long planId, Long accountId) {
        this.planId = planId;
        this.accountId = accountId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
