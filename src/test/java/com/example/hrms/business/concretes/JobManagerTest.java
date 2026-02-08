package com.example.hrms.business.concretes;

import com.example.hrms.core.utilities.results.DataResult;
import com.example.hrms.core.utilities.results.Result;
import com.example.hrms.dataAccess.abstracts.JobDao;
import com.example.hrms.entities.concretes.Job;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JobManagerTest {

    @Mock
    private JobDao jobDao;

    @InjectMocks
    private JobManager jobManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        Job job1 = new Job();
        job1.setId(1);
        Job job2 = new Job();
        job2.setId(2);

        when(jobDao.findAll()).thenReturn(Arrays.asList(job1, job2));

        DataResult<List<Job>> result = jobManager.getAll();

        assertTrue(result.isSuccess());
        assertEquals(2, result.getData().size());
        assertEquals("Jobs are listed.", result.getMessage());
        verify(jobDao, times(1)).findAll();
    }

    @Test
    void testAdd() {
        Job job = new Job();
        job.setId(1);

        when(jobDao.save(job)).thenReturn(job);

        Result result = jobManager.add(job);

        assertTrue(result.isSuccess());
        assertEquals("Job position is added.", result.getMessage());
        verify(jobDao, times(1)).save(job);
    }
}
