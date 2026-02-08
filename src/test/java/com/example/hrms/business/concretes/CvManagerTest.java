package com.example.hrms.business.concretes;

import com.example.hrms.core.utilities.results.DataResult;
import com.example.hrms.core.utilities.results.Result;
import com.example.hrms.dataAccess.abstracts.CvDao;
import com.example.hrms.entities.concretes.Cv;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CvManagerTest {

    @Mock
    private CvDao cvDao;

    @InjectMocks
    private CvManager cvManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        Cv cv1 = new Cv();
        cv1.setId(1);
        Cv cv2 = new Cv();
        cv2.setId(2);

        when(cvDao.findAll()).thenReturn(Arrays.asList(cv1, cv2));

        DataResult<List<Cv>> result = cvManager.getAll();

        assertTrue(result.isSuccess());
        assertEquals(2, result.getData().size());
        assertEquals("Listed all cvs.", result.getMessage());
        verify(cvDao, times(1)).findAll();
    }

    @Test
    void testAdd() {
        Cv cv = new Cv();
        cv.setId(1);

        when(cvDao.save(cv)).thenReturn(cv);

        Result result = cvManager.add(cv);

        assertTrue(result.isSuccess());
        assertEquals("Cv is added.", result.getMessage());
        verify(cvDao, times(1)).save(cv);
    }

    @Test
    void testGetCvByCandidate_Id() {
        int candidateId = 1;
        Cv cv = new Cv();
        cv.setId(1);

        when(cvDao.getCvByCandidate_Id(candidateId)).thenReturn(cv);

        DataResult<Cv> result = cvManager.getCvByCandidate_Id(candidateId);

        assertTrue(result.isSuccess());
        assertEquals(cv, result.getData());
        assertEquals("Cv is found.", result.getMessage());
        verify(cvDao, times(1)).getCvByCandidate_Id(candidateId);
    }
}
