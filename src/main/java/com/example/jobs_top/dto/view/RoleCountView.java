package com.example.jobs_top.dto.view;

import com.example.jobs_top.model.enums.RoleType;
import org.springframework.beans.factory.annotation.Value;

public interface RoleCountView {
    @Value("#{target.role.label}")
    String getRole();
    Long getCount();
}
