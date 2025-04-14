package com.example.jobs_top.listener;


import com.example.jobs_top.model.BaseEntity;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


public class CustomAuditingEntityListener extends AuditingEntityListener {
    public CustomAuditingEntityListener(ObjectFactory<AuditingHandler> handler) {
        super.setAuditingHandler(handler);
    }

    @Override
    public void touchForCreate(Object target) {
        if (target instanceof BaseEntity entity) {
            if (entity.getCreatedAt() == null) {
                super.touchForCreate(entity);
            }
        } else {
            throw new IllegalArgumentException("Unsupported target type: " + target.getClass());
        }
    }

    @Override
    public void touchForUpdate(Object target) {
        if (target instanceof BaseEntity entity) {
            super.touchForUpdate(entity);
        } else {
            throw new IllegalArgumentException("Unsupported target type: " + target.getClass());
        }
    }
}
