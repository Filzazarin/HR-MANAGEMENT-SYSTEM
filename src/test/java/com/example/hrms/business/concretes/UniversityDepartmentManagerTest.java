package com.example.hrms.business.concretes;

import com.example.hrms.core.utilities.results.DataResult;
import com.example.hrms.core.utilities.results.Result;
import com.example.hrms.dataAccess.abstracts.UniversityDepartmentDao;
import com.example.hrms.entities.concretes.UniversityDepartment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UniversityDepartmentManagerTest {

    @Mock
    private UniversityDepartmentDao universityDepartmentDao;

    @InjectMocks
    private UniversityDepartmentManager universityDepartmentManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        UniversityDepartment ud1 = new UniversityDepartment();
        ud1.setId(1);
        UniversityDepartment ud2 = new UniversityDepartment();
        ud2.setId(2);

        when(universityDepartmentDao.findAll()).thenReturn(Arrays.asList(ud1, ud2));

        DataResult<List<UniversityDepartment>> result = universityDepartmentManager.getAll();

        assertTrue(result.isSuccess());
        assertEquals(2, result.getData().size());
        assertEquals("All university departments are listed.", result.getMessage());
        verify(universityDepartmentDao, times(1)).findAll();
    }

    @Test
    void testAdd() {
        UniversityDepartment ud = new UniversityDepartment();
        ud.setId(1);

        when(universityDepartmentDao.save(ud)).thenReturn(ud);

        Result result = universityDepartmentManager.add(ud);

        assertTrue(result.isSuccess());
        assertEquals("University department is added.", result.getMessage());
        verify(universityDepartmentDao, times(1)).save(ud);
    }
}
