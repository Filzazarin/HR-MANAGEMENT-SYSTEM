package com.example.hrms.business.concretes;

import com.example.hrms.core.utilities.results.DataResult;
import com.example.hrms.core.utilities.results.ErrorResult;
import com.example.hrms.core.utilities.results.Result;
import com.example.hrms.core.utilities.results.SuccessResult;
import com.example.hrms.dataAccess.abstracts.JobAdvertisementDao;
import com.example.hrms.entities.concretes.JobAdvertisement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JobAdvertisementManagerTest {

    @Mock
    private JobAdvertisementDao jobAdvertisementDao;

    @InjectMocks
    private JobAdvertisementManager jobAdvertisementManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        JobAdvertisement job1 = new JobAdvertisement();
        job1.setId(1);
        JobAdvertisement job2 = new JobAdvertisement();
        job2.setId(2);

        when(jobAdvertisementDao.findAll()).thenReturn(Arrays.asList(job1, job2));

        DataResult<List<JobAdvertisement>> result = jobAdvertisementManager.getAll();

        assertTrue(result.isSuccess());
        assertEquals(2, result.getData().size());
        assertEquals("All job advertisements are listed.", result.getMessage());
        verify(jobAdvertisementDao, times(1)).findAll();
    }

    @Test
    void testAdd() {
        JobAdvertisement job = new JobAdvertisement();
        job.setId(1);

        when(jobAdvertisementDao.save(job)).thenReturn(job);

        Result result = jobAdvertisementManager.add(job);

        assertTrue(result.isSuccess());
        assertEquals("Job advertisement is added.", result.getMessage());
        verify(jobAdvertisementDao, times(1)).save(job);
    }

    @Test
    void testGetAllActive() {
        JobAdvertisement job1 = new JobAdvertisement();
        job1.setId(1);
        job1.setIsActive(true);

        when(jobAdvertisementDao.findByIsActiveTrue()).thenReturn(Arrays.asList(job1));

        DataResult<List<JobAdvertisement>> result = jobAdvertisementManager.getAllActive();

        assertTrue(result.isSuccess());
        assertEquals(1, result.getData().size());
        assertEquals("Active jobs are listed.", result.getMessage());
        verify(jobAdvertisementDao, times(1)).findByIsActiveTrue();
    }

    @Test
    void testDelete_Success() {
        int id = 1;

        when(jobAdvertisementDao.existsById(id)).thenReturn(true);
        when(jobAdvertisementDao.deleteById(id)).thenReturn(new SuccessResult("Deleted"));

        Result result = jobAdvertisementManager.delete(id);

        assertTrue(result.isSuccess());
        assertEquals("Job advertisement is deleted.", result.getMessage());
        verify(jobAdvertisementDao, times(1)).deleteById(id);
    }

    @Test
    void testDelete_NotFound() {
        int id = 1;

        when(jobAdvertisementDao.existsById(id)).thenReturn(false);

        Result result = jobAdvertisementManager.delete(id);

        assertFalse(result.isSuccess());
        assertEquals("Job advertisement not found", result.getMessage());
        verify(jobAdvertisementDao, never()).deleteById(id);
    }
}
