package com.example.jobs_top.utils;


import com.example.jobs_top.model.User;
import com.example.jobs_top.security.MyUserDetailsService;
import com.example.jobs_top.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Utils {
    public static User getUserFromContext() {
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
