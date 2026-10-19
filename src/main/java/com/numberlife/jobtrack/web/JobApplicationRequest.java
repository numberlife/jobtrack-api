package com.numberlife.jobtrack.web;

import com.numberlife.jobtrack.domain.JobStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record JobApplicationRequest(
        @NotBlank @Size(max = 160) String company,
        @NotBlank @Size(max = 200) String roleTitle,
        @Size(max = 2048) @Pattern(regexp = "^$|https?://[^\\s]+", message = "must be an HTTP or HTTPS URL") String jobUrl,
        JobStatus status,
        @Size(max = 10000) String notes
) {}
