package com.example.hrms.business.concretes;

import com.example.hrms.core.utilities.results.DataResult;
import com.example.hrms.core.utilities.results.Result;
import com.example.hrms.dataAccess.abstracts.LinkDao;
import com.example.hrms.entities.concretes.Link;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LinkManagerTest {

    @Mock
    private LinkDao linkDao;

    @InjectMocks
    private LinkManager linkManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        Link link1 = new Link();
        link1.setId(1);
        Link link2 = new Link();
        link2.setId(2);

        when(linkDao.findAll()).thenReturn(Arrays.asList(link1, link2));

        DataResult<List<Link>> result = linkManager.getAll();

        assertTrue(result.isSuccess());
        assertEquals(2, result.getData().size());
        assertEquals("Listed all links.", result.getMessage());
        verify(linkDao, times(1)).findAll();
    }

    @Test
    void testAdd_Success() {
        Link link = new Link();
        link.setId(1);
        link.setUrl("http://example.com");

        when(linkDao.existsByUrl("http://example.com")).thenReturn(false);
        when(linkDao.save(link)).thenReturn(link);

        Result result = linkManager.add(link);

        assertTrue(result.isSuccess());
        assertEquals("Added link.", result.getMessage());
        verify(linkDao, times(1)).save(link);
    }

    @Test
    void testAdd_Failure_Duplicate() {
        Link link = new Link();
        link.setId(1);
        link.setUrl("http://example.com");

        when(linkDao.existsByUrl("http://example.com")).thenReturn(true);

        Result result = linkManager.add(link);

        assertFalse(result.isSuccess());
        assertEquals("Link has already exist.", result.getMessage());
        verify(linkDao, never()).save(link);
    }
}
