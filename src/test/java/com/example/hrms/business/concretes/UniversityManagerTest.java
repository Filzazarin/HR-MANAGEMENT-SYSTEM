package com.example.hrms.business.concretes;

import com.example.hrms.core.utilities.results.DataResult;
import com.example.hrms.core.utilities.results.Result;
import com.example.hrms.dataAccess.abstracts.UniversityDao;
import com.example.hrms.entities.concretes.University;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UniversityManagerTest {

    @Mock
    private UniversityDao universityDao;

    @InjectMocks
    private UniversityManager universityManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        University uni1 = new University();
        uni1.setId(1);
        University uni2 = new University();
        uni2.setId(2);

        when(universityDao.findAll()).thenReturn(Arrays.asList(uni1, uni2));

        DataResult<List<University>> result = universityManager.getAll();

        assertTrue(result.isSuccess());
        assertEquals(2, result.getData().size());
        assertEquals("All universities are listed.", result.getMessage());
        verify(universityDao, times(1)).findAll();
    }

    @Test
    void testAdd_Success() {
        University uni = new University();
        uni.setId(1);
        uni.setUniversityName("Test University");

        when(universityDao.existsByUniversityName("Test University")).thenReturn(false);
        when(universityDao.save(uni)).thenReturn(uni);

        Result result = universityManager.add(uni);

        assertTrue(result.isSuccess());
        assertEquals("University is added.", result.getMessage());
        verify(universityDao, times(1)).save(uni);
    }

    @Test
    void testAdd_Failure_Duplicate() {
        University uni = new University();
        uni.setId(1);
        uni.setUniversityName("Test University");

        when(universityDao.existsByUniversityName("Test University")).thenReturn(true);

        Result result = universityManager.add(uni);

        assertFalse(result.isSuccess());
        assertEquals("University has already exist.", result.getMessage());
        verify(universityDao, never()).save(uni);
    }
}
