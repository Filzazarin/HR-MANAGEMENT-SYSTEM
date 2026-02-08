package com.example.hrms.business.concretes;

import com.example.hrms.core.utilities.results.DataResult;
import com.example.hrms.core.utilities.results.Result;
import com.example.hrms.dataAccess.abstracts.EducationDao;
import com.example.hrms.entities.concretes.Education;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EducationManagerTest {

    @Mock
    private EducationDao educationDao;

    @InjectMocks
    private EducationManager educationManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        Education edu1 = new Education();
        edu1.setId(1);
        Education edu2 = new Education();
        edu2.setId(2);

        when(educationDao.findAll()).thenReturn(Arrays.asList(edu1, edu2));

        DataResult<List<Education>> result = educationManager.getAll();

        assertTrue(result.isSuccess());
        assertEquals(2, result.getData().size());
        assertEquals("Listed all educations.", result.getMessage());
        verify(educationDao, times(1)).findAll();
    }

    @Test
    void testAdd() {
        Education edu = new Education();
        edu.setId(1);

        when(educationDao.save(edu)).thenReturn(edu);

        Result result = educationManager.add(edu);

        assertTrue(result.isSuccess());
        assertEquals("Education is added.", result.getMessage());
        verify(educationDao, times(1)).save(edu);
    }
}
