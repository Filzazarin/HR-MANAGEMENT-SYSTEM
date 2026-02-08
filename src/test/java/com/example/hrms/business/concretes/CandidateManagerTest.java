package com.example.hrms.business.concretes;

import com.example.hrms.core.abstracts.MernisCheckService;
import com.example.hrms.core.utilities.results.DataResult;
import com.example.hrms.core.utilities.results.ErrorDataResult;
import com.example.hrms.core.utilities.results.Result;
import com.example.hrms.core.utilities.results.SuccessDataResult;
import com.example.hrms.dataAccess.abstracts.CandidateDao;
import com.example.hrms.entities.concretes.Candidate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CandidateManagerTest {

    @Mock
    private CandidateDao candidateDao;

    @Mock
    private MernisCheckService mernisCheckService;

    @InjectMocks
    private CandidateManager candidateManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        Candidate candidate1 = new Candidate();
        candidate1.setId(1);
        candidate1.setEmail("test1@example.com");

        Candidate candidate2 = new Candidate();
        candidate2.setId(2);
        candidate2.setEmail("test2@example.com");

        when(candidateDao.findAll()).thenReturn(Arrays.asList(candidate1, candidate2));

        DataResult<List<Candidate>> result = candidateManager.getAll();

        assertTrue(result.isSuccess());
        assertEquals(2, result.getData().size());
        assertEquals("All candidates are listed.", result.getMessage());
        verify(candidateDao, times(1)).findAll();
    }

    @Test
    void testGetByEmail_Success() {
        String email = "test@example.com";
        Candidate candidate = new Candidate();
        candidate.setId(1);
        candidate.setEmail(email);

        when(candidateDao.existsCandidateByEmail(email)).thenReturn(true);
        when(candidateDao.getByEmail(email)).thenReturn(candidate);

        DataResult<Candidate> result = candidateManager.getByEmail(email);

        assertTrue(result.isSuccess());
        assertEquals(email, result.getData().getEmail());
        assertEquals("Candidate is found.", result.getMessage());
    }

    @Test
    void testGetByEmail_NotFound() {
        String email = "notfound@example.com";

        when(candidateDao.existsCandidateByEmail(email)).thenReturn(false);

        DataResult<Candidate> result = candidateManager.getByEmail(email);

        assertFalse(result.isSuccess());
        assertNull(result.getData());
        assertEquals("Candidate not found!", result.getMessage());
    }

    @Test
    void testGetByIdentityNumber_Success() {
        String identityNumber = "12345678901";
        Candidate candidate = new Candidate();
        candidate.setId(1);
        candidate.setIdentityNumber(identityNumber);

        when(candidateDao.existsCandidateByIdentityNumber(identityNumber)).thenReturn(true);
        when(candidateDao.getByIdentityNumber(identityNumber)).thenReturn(candidate);

        DataResult<Candidate> result = candidateManager.getByIdentityNumber(identityNumber);

        assertTrue(result.isSuccess());
        assertEquals(identityNumber, result.getData().getIdentityNumber());
        assertEquals("Candidate is listed.", result.getMessage());
    }

    @Test
    void testGetByIdentityNumber_NotFound() {
        String identityNumber = "00000000000";

        when(candidateDao.existsCandidateByIdentityNumber(identityNumber)).thenReturn(false);

        DataResult<Candidate> result = candidateManager.getByIdentityNumber(identityNumber);

        assertFalse(result.isSuccess());
        assertNull(result.getData());
        assertEquals("Candidate not found!", result.getMessage());
    }

    @Test
    void testAdd_Success() {
        Candidate candidate = new Candidate();
        candidate.setEmail("new@example.com");
        candidate.setIdentityNumber("12345678901");

        when(candidateDao.existsCandidateByEmail(candidate.getEmail())).thenReturn(false);
        when(candidateDao.existsCandidateByIdentityNumber(candidate.getIdentityNumber())).thenReturn(false);
        when(candidateDao.save(candidate)).thenReturn(candidate);

        Result result = candidateManager.add(candidate);

        assertTrue(result.isSuccess());
        assertEquals("Candidate is added.", result.getMessage());
        verify(candidateDao, times(1)).save(candidate);
    }

    @Test
    void testAdd_Failure_DuplicateEmail() {
        Candidate candidate = new Candidate();
        candidate.setEmail("existing@example.com");
        candidate.setIdentityNumber("12345678901");

        when(candidateDao.existsCandidateByEmail(candidate.getEmail())).thenReturn(true);

        Result result = candidateManager.add(candidate);

        assertFalse(result.isSuccess());
        assertEquals("User has already exist!", result.getMessage());
        verify(candidateDao, never()).save(candidate);
    }

    @Test
    void testAdd_Failure_DuplicateIdentityNumber() {
        Candidate candidate = new Candidate();
        candidate.setEmail("new@example.com");
        candidate.setIdentityNumber("12345678901");

        when(candidateDao.existsCandidateByEmail(candidate.getEmail())).thenReturn(false);
        when(candidateDao.existsCandidateByIdentityNumber(candidate.getIdentityNumber())).thenReturn(true);

        Result result = candidateManager.add(candidate);

        assertFalse(result.isSuccess());
        assertEquals("User has already exist!", result.getMessage());
        verify(candidateDao, never()).save(candidate);
    }
}
