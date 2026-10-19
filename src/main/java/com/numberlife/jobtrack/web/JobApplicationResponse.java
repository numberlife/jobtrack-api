package com.numberlife.jobtrack.web;

import com.numberlife.jobtrack.domain.JobApplication;
import com.numberlife.jobtrack.domain.JobStatus;
import java.time.Instant;

public record JobApplicationResponse(
        Long id,
        String company,
        String roleTitle,
        String jobUrl,
        JobStatus status,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {
    public static JobApplicationResponse from(JobApplication application) {
        return new JobApplicationResponse(
                application.getId(), application.getCompany(), application.getRoleTitle(),
                application.getJobUrl(), application.getStatus(), application.getNotes(),
                application.getCreatedAt(), application.getUpdatedAt());
    }
}
