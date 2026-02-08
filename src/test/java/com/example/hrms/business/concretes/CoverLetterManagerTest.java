package com.example.hrms.business.concretes;

import com.example.hrms.core.utilities.results.DataResult;
import com.example.hrms.core.utilities.results.Result;
import com.example.hrms.dataAccess.abstracts.CoverLetterDao;
import com.example.hrms.entities.concretes.CoverLetter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CoverLetterManagerTest {

    @Mock
    private CoverLetterDao coverLetterDao;

    @InjectMocks
    private CoverLetterManager coverLetterManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        CoverLetter cl1 = new CoverLetter();
        cl1.setId(1);
        CoverLetter cl2 = new CoverLetter();
        cl2.setId(2);

        when(coverLetterDao.findAll()).thenReturn(Arrays.asList(cl1, cl2));

        DataResult<List<CoverLetter>> result = coverLetterManager.getAll();

        assertTrue(result.isSuccess());
        assertEquals(2, result.getData().size());
        assertEquals("Listed all cover letters.", result.getMessage());
        verify(coverLetterDao, times(1)).findAll();
    }

    @Test
    void testAdd() {
        CoverLetter cl = new CoverLetter();
        cl.setId(1);

        when(coverLetterDao.save(cl)).thenReturn(cl);

        Result result = coverLetterManager.add(cl);

        assertTrue(result.isSuccess());
        assertEquals("Added cover letter.", result.getMessage());
        verify(coverLetterDao, times(1)).save(cl);
    }
}
