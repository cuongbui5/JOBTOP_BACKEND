package com.example.jobs_top.utils;


import com.example.jobs_top.model.Account;
import com.example.jobs_top.security.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Utils {
    public static Account getAccount() {
        UserPrincipal userPrincipal  = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userPrincipal.getUser();
    }


    public static Date zonedDateTimeToDate(String zonedDateTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(zonedDateTimeStr, formatter);
        Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }
}
