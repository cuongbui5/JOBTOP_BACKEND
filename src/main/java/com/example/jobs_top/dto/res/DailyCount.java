package com.example.jobs_top.dto.res;

import java.time.LocalDate;

public class DailyCount {
    private LocalDate date;
    private long count;

    public DailyCount(java.util.Date date, long count) {
        this.date = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        this.count = count;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
