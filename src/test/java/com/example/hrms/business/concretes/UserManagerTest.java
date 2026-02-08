package com.example.hrms.business.concretes;

import com.example.hrms.core.utilities.results.Result;
import com.example.hrms.dataAccess.abstracts.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserManagerTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserManager userManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteAll() {
        doNothing().when(userDao).deleteAll();

        Result result = userManager.deleteAll();

        assertTrue(result.isSuccess());
        assertEquals("Deleted all users.", result.getMessage());
        verify(userDao, times(1)).deleteAll();
    }
}
