CREATE TABLE job_applications (
    id BIGSERIAL PRIMARY KEY,
    company VARCHAR(160) NOT NULL,
    role_title VARCHAR(200) NOT NULL,
    job_url VARCHAR(2048),
    status VARCHAR(24) NOT NULL,
    notes TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_job_applications_status_created_at
    ON job_applications (status, created_at DESC);
