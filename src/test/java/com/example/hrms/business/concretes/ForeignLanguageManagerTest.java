package com.example.hrms.business.concretes;

import com.example.hrms.core.utilities.results.DataResult;
import com.example.hrms.core.utilities.results.Result;
import com.example.hrms.dataAccess.abstracts.ForeignLanguageDao;
import com.example.hrms.entities.concretes.ForeignLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ForeignLanguageManagerTest {

    @Mock
    private ForeignLanguageDao foreignLanguageDao;

    @InjectMocks
    private ForeignLanguageManager foreignLanguageManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        ForeignLanguage fl1 = new ForeignLanguage();
        fl1.setId(1);
        ForeignLanguage fl2 = new ForeignLanguage();
        fl2.setId(2);

        when(foreignLanguageDao.findAll()).thenReturn(Arrays.asList(fl1, fl2));

        DataResult<List<ForeignLanguage>> result = foreignLanguageManager.getAll();

        assertTrue(result.isSuccess());
        assertEquals(2, result.getData().size());
        assertEquals("Listed all foreign languages.", result.getMessage());
        verify(foreignLanguageDao, times(1)).findAll();
    }

    @Test
    void testAdd() {
        ForeignLanguage fl = new ForeignLanguage();
        fl.setId(1);

        when(foreignLanguageDao.save(fl)).thenReturn(fl);

        Result result = foreignLanguageManager.add(fl);

        assertTrue(result.isSuccess());
        assertEquals("Added foreign language.", result.getMessage());
        verify(foreignLanguageDao, times(1)).save(fl);
    }
}
