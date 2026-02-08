package com.example.hrms.business.concretes;

import com.example.hrms.core.utilities.results.DataResult;
import com.example.hrms.core.utilities.results.Result;
import com.example.hrms.dataAccess.abstracts.JobExperienceDao;
import com.example.hrms.entities.concretes.JobExperience;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JobExperienceManagerTest {

    @Mock
    private JobExperienceDao jobExperienceDao;

    @InjectMocks
    private JobExperienceManager jobExperienceManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        JobExperience je1 = new JobExperience();
        je1.setId(1);
        JobExperience je2 = new JobExperience();
        je2.setId(2);

        when(jobExperienceDao.findAll()).thenReturn(Arrays.asList(je1, je2));

        DataResult<List<JobExperience>> result = jobExperienceManager.getAll();

        assertTrue(result.isSuccess());
        assertEquals(2, result.getData().size());
        assertEquals("Listed all job experiences.", result.getMessage());
        verify(jobExperienceDao, times(1)).findAll();
    }

    @Test
    void testAdd() {
        JobExperience je = new JobExperience();
        je.setId(1);

        when(jobExperienceDao.save(je)).thenReturn(je);

        Result result = jobExperienceManager.add(je);

        assertTrue(result.isSuccess());
        assertEquals("Added job experience.", result.getMessage());
        verify(jobExperienceDao, times(1)).save(je);
    }
}
