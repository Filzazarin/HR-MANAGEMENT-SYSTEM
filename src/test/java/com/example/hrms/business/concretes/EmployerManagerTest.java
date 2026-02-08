package com.example.hrms.business.concretes;

import com.example.hrms.core.utilities.results.DataResult;
import com.example.hrms.core.utilities.results.Result;
import com.example.hrms.dataAccess.abstracts.EmployerDao;
import com.example.hrms.entities.concretes.Employer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployerManagerTest {

    @Mock
    private EmployerDao employerDao;

    @InjectMocks
    private EmployerManager employerManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        Employer emp1 = new Employer();
        emp1.setId(1);
        Employer emp2 = new Employer();
        emp2.setId(2);

        when(employerDao.findAll()).thenReturn(Arrays.asList(emp1, emp2));

        DataResult<List<Employer>> result = employerManager.getAll();

        assertTrue(result.isSuccess());
        assertEquals(2, result.getData().size());
        assertEquals("Employers are listed.", result.getMessage());
        verify(employerDao, times(1)).findAll();
    }

    @Test
    void testGetByEmail_Success() {
        String email = "test@company.com";
        Employer emp = new Employer();
        emp.setId(1);
        emp.setEmail(email);

        when(employerDao.existsEmployerByEmail(email)).thenReturn(true);
        when(employerDao.getByEmail(email)).thenReturn(emp);

        DataResult<Employer> result = employerManager.getByEmail(email);

        assertTrue(result.isSuccess());
        assertEquals(email, result.getData().getEmail());
        assertEquals("Employer is found.", result.getMessage());
    }

    @Test
    void testGetByEmail_NotFound() {
        String email = "notfound@company.com";

        when(employerDao.existsEmployerByEmail(email)).thenReturn(false);

        DataResult<Employer> result = employerManager.getByEmail(email);

        assertFalse(result.isSuccess());
        assertNull(result.getData());
        assertEquals("Employer not found!", result.getMessage());
    }

    @Test
    void testAdd_Success() {
        Employer emp = new Employer();
        emp.setEmail("new@company.com");

        // First call to getByEmail for duplicate check returns null (not found)
        when(employerDao.existsEmployerByEmail(emp.getEmail())).thenReturn(false);
        when(employerDao.save(emp)).thenReturn(emp);

        Result result = employerManager.add(emp);

        assertTrue(result.isSuccess());
        assertEquals("Employer is added.", result.getMessage());
        verify(employerDao, times(1)).save(emp);
    }

    @Test
    void testAdd_Failure_DuplicateEmail() {
        Employer emp = new Employer();
        emp.setEmail("existing@company.com");
        emp.setId(1);

        // First call to getByEmail for duplicate check needs to return an existing
        // employer
        when(employerDao.existsEmployerByEmail(emp.getEmail())).thenReturn(true);
        when(employerDao.getByEmail(emp.getEmail())).thenReturn(emp);

        Result result = employerManager.add(emp);

        assertFalse(result.isSuccess());
        assertEquals("User e-mail has already exist!", result.getMessage());
        verify(employerDao, never()).save(emp);
    }
}
