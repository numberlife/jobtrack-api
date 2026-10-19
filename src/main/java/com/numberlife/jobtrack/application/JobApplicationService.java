package com.numberlife.jobtrack.application;

import com.numberlife.jobtrack.domain.JobApplication;
import com.numberlife.jobtrack.domain.JobStatus;
import com.numberlife.jobtrack.infrastructure.JobApplicationRepository;
import com.numberlife.jobtrack.web.JobApplicationRequest;
import com.numberlife.jobtrack.web.JobApplicationResponse;
import com.numberlife.jobtrack.web.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class JobApplicationService {
    private final JobApplicationRepository repository;

    public JobApplicationService(JobApplicationRepository repository) {
        this.repository = repository;
    }

    public JobApplicationResponse create(JobApplicationRequest request) {
        JobApplication application = new JobApplication(
                request.company().trim(), request.roleTitle().trim(), normalizeUrl(request.jobUrl()),
                request.status() == null ? JobStatus.SAVED : request.status(), request.notes());
        return JobApplicationResponse.from(repository.save(application));
    }

    @Transactional(readOnly = true)
    public PageResponse<JobApplicationResponse> list(JobStatus status, Pageable pageable) {
        Page<JobApplication> page = status == null
                ? repository.findAll(pageable)
                : repository.findAllByStatus(status, pageable);
        return PageResponse.from(page.map(JobApplicationResponse::from));
    }

    @Transactional(readOnly = true)
    public JobApplicationResponse get(long id) {
        return JobApplicationResponse.from(require(id));
    }

    public JobApplicationResponse update(long id, JobApplicationRequest request) {
        JobApplication application = require(id);
        application.update(
                request.company().trim(), request.roleTitle().trim(), normalizeUrl(request.jobUrl()),
                request.status() == null ? application.getStatus() : request.status(), request.notes());
        return JobApplicationResponse.from(repository.save(application));
    }

    public void delete(long id) {
        repository.delete(require(id));
    }

    private JobApplication require(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job application not found."));
    }

    private String normalizeUrl(String value) {
        if (value == null || value.isBlank()) return null;
        return value.trim();
    }
}
