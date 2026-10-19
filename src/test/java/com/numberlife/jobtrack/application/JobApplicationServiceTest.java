package com.numberlife.jobtrack.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.numberlife.jobtrack.domain.JobApplication;
import com.numberlife.jobtrack.domain.JobStatus;
import com.numberlife.jobtrack.infrastructure.JobApplicationRepository;
import com.numberlife.jobtrack.web.JobApplicationRequest;
import com.numberlife.jobtrack.web.JobApplicationResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JobApplicationServiceTest {
    @Mock
    private JobApplicationRepository repository;

    @InjectMocks
    private JobApplicationService service;

    @Test
    void createTrimsTextAndDefaultsStatusToSaved() {
        when(repository.save(any(JobApplication.class))).thenAnswer(invocation -> invocation.getArgument(0));
        JobApplicationRequest request = new JobApplicationRequest(" Example Co. ", " AI Engineer ", null, null, "note");

        JobApplicationResponse response = service.create(request);

        ArgumentCaptor<JobApplication> captor = ArgumentCaptor.forClass(JobApplication.class);
        verify(repository).save(captor.capture());
        assertEquals("Example Co.", captor.getValue().getCompany());
        assertEquals("AI Engineer", captor.getValue().getRoleTitle());
        assertEquals(JobStatus.SAVED, captor.getValue().getStatus());
        assertEquals("Example Co.", response.company());
    }
}
