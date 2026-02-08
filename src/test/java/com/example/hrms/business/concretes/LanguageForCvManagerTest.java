package com.example.hrms.business.concretes;

import com.example.hrms.core.utilities.results.DataResult;
import com.example.hrms.core.utilities.results.Result;
import com.example.hrms.dataAccess.abstracts.LanguageForCvDao;
import com.example.hrms.entities.concretes.LanguageForCv;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LanguageForCvManagerTest {

    @Mock
    private LanguageForCvDao languageForCvDao;

    @InjectMocks
    private LanguageForCvManager languageForCvManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        LanguageForCv lang1 = new LanguageForCv();
        lang1.setId(1);
        LanguageForCv lang2 = new LanguageForCv();
        lang2.setId(2);

        when(languageForCvDao.findAll()).thenReturn(Arrays.asList(lang1, lang2));

        DataResult<List<LanguageForCv>> result = languageForCvManager.getAll();

        assertTrue(result.isSuccess());
        assertEquals(2, result.getData().size());
        assertEquals("Listed all.", result.getMessage());
        verify(languageForCvDao, times(1)).findAll();
    }

    @Test
    void testAdd() {
        LanguageForCv lang = new LanguageForCv();
        lang.setId(1);

        when(languageForCvDao.save(lang)).thenReturn(lang);

        Result result = languageForCvManager.add(lang);

        assertTrue(result.isSuccess());
        assertEquals("Added.", result.getMessage());
        verify(languageForCvDao, times(1)).save(lang);
    }
}
