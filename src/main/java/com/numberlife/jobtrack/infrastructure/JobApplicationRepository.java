package com.numberlife.jobtrack.infrastructure;

import com.numberlife.jobtrack.domain.JobApplication;
import com.numberlife.jobtrack.domain.JobStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    Page<JobApplication> findAllByStatus(JobStatus status, Pageable pageable);
}
