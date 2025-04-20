package com.example.jobs_top.service;

import com.example.jobs_top.model.Plan;
import com.example.jobs_top.repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {
    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public List<Plan> getAllPlans() {
        return planRepository.findByIsActiveTrue();
    }

    public Plan createPlan(Plan plan) {
        plan.setActive(true);
        return planRepository.save(plan);
    }

    public Plan updatePlan(Long id, Plan updatedPlan) {
        return planRepository.findById(id)
                .map(existingPlan -> {
                    existingPlan.setName(updatedPlan.getName());
                    existingPlan.setPrice(updatedPlan.getPrice());
                    existingPlan.setDescription(updatedPlan.getDescription());
                    existingPlan.setMaxPosts(updatedPlan.getMaxPosts());
                    existingPlan.setDurationDays(updatedPlan.getDurationDays());
                    return planRepository.save(existingPlan);
                })
                .orElseThrow(() -> new IllegalArgumentException("Plan with id " + id + " not found"));

    }

    public void deletePlan(Long id) {
        Plan plan=planRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Plan with id " + id + " not found"));
        plan.setActive(false);
        planRepository.save(plan);
    }
}
