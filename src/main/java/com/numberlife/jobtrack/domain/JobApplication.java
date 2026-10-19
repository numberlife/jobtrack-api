package com.numberlife.jobtrack.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "job_applications")
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 160)
    private String company;

    @Column(name = "role_title", nullable = false, length = 200)
    private String roleTitle;

    @Column(name = "job_url", length = 2048)
    private String jobUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 24)
    private JobStatus status;

    @Column(columnDefinition = "text")
    private String notes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected JobApplication() {}

    public JobApplication(String company, String roleTitle, String jobUrl, JobStatus status, String notes) {
        this.company = company;
        this.roleTitle = roleTitle;
        this.jobUrl = jobUrl;
        this.status = status;
        this.notes = notes;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }

    public void update(String company, String roleTitle, String jobUrl, JobStatus status, String notes) {
        this.company = company;
        this.roleTitle = roleTitle;
        this.jobUrl = jobUrl;
        this.status = status;
        this.notes = notes;
    }

    public Long getId() { return id; }
    public String getCompany() { return company; }
    public String getRoleTitle() { return roleTitle; }
    public String getJobUrl() { return jobUrl; }
    public JobStatus getStatus() { return status; }
    public String getNotes() { return notes; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
