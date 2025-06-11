package com.example.jobs_top.dto.res;

import java.math.BigDecimal;

public class MonthlyRevenueDto {
    private int month;
    private BigDecimal revenue;

    public MonthlyRevenueDto(int month, BigDecimal revenue) {
        this.month = month;
        this.revenue = revenue;
    }

    public int getMonth() {
        return month;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }
}
